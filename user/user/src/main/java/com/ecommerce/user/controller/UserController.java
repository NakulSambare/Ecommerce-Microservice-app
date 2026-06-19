package com.ecommerce.user.controller;



import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.model.User;
import com.ecommerce.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {


    @Autowired
    UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/api/users")
    public ResponseEntity<List<UserResponse>> createUsers(@RequestBody UserRequest user) {

        return ResponseEntity.ok(userService.createUsers(user));
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable  Long id) {
        // Implementation to get user by ID
        return ResponseEntity.ok(userService.getUserById(id)); // Placeholder
    }

    @PutMapping("/api/users")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        boolean isUpdated = userService.updateUser(user);
        if (isUpdated) {
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
