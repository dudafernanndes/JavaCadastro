package com.exemplo.demo.controller;

import com.exemplo.demo.dto.LoginRequest;
import com.exemplo.demo.model.User;
import com.exemplo.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = service.login(loginRequest.getEmail(), loginRequest.getSenha());
            String token = UUID.randomUUID().toString();
            return ResponseEntity.ok(Map.of("token", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/password")
    public User changePassword(@RequestParam String email, @RequestParam String newPassword) {
        return service.changePassword(email, newPassword);
    }
}
