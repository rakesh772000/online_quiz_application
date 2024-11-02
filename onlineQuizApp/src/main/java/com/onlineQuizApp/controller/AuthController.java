package com.onlineQuizApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.onlineQuizApp.DTO.UserRegistrationDto;
import com.onlineQuizApp.model.User;
import com.onlineQuizApp.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    // Admin registration
    @PostMapping("/register/admin")
    public ResponseEntity<User> registerAdmin(@RequestBody UserRegistrationDto registrationDto) {
        User admin = new User();
        admin.setUsername(registrationDto.getUsername());
        admin.setPassword(registrationDto.getPassword());
        admin.setRole("ADMIN");
        return ResponseEntity.ok(userService.registerUser(admin));
    }

    // User registration
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegistrationDto registrationDto) {
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(registrationDto.getPassword());
        user.setRole("USER");
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
}
