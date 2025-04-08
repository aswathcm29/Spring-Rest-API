package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public User getUserById(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @PostMapping("/add")
    public String addUser(@RequestBody User user) {
        if (userRepository.existsById(user.getId())) {
            return "User with ID " + user.getId() + " already exists!";
        }
        System.out.println(user);
        userRepository.save(user);
        return "User added!";
    }

    @PutMapping("/update/{id}")
    public String updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            userRepository.save(user);
            return "User updated!";
        }
        return "User not found!";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "User deleted!";
        }
        return "User not found!";
    }
}
