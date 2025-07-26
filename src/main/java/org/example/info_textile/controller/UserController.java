package org.example.info_textile.controller;

import org.example.info_textile.model.Users;
import org.example.info_textile.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")

public class UserController {

    @Autowired
    private UserRepo userRepo;
    @GetMapping("/me")
    public String getUserInfo() {
        return "Siz token orqali autentifikatsiyadan o'tgansiz!";
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication auth) {
        Users user = (Users) auth.getPrincipal();
        return ResponseEntity.ok(Map.of(
                "name", user.getName(),
                "email", user.getEmail(),
                "role", user.getRole()
        ));
    }

}
