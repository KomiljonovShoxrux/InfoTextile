package org.example.info_textile.controller;

import org.example.info_textile.model.AuthResponse;
import org.example.info_textile.model.LoginRequest;
import org.example.info_textile.model.RegisterRequest;
import org.example.info_textile.model.Users;
import org.example.info_textile.model.entity.Role;
import org.example.info_textile.repository.UserRepo;
import org.example.info_textile.security.JwtUtil;
import org.example.info_textile.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepo userRepo;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil, UserRepo userRepo) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            String token = authService.add(request);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Users> optionalUser = userRepo.findByEmail(request.getEmail());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email noto‘g‘ri"));
        }

        Users user = optionalUser.get();
        if (user.isBlocked()) {
            return ResponseEntity.status(403).body(Map.of("error", "Bloklangan foydalanuvchi"));
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            String token = jwtUtil.createToken(user.getEmail(), user.getRole().name(), user.getName());
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "name", user.getName(),
                    "email", user.getEmail()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Login yoki parol noto‘g‘ri"));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("error", "Token yo‘q"));
        }

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
        Optional<Users> optionalUser = userRepo.findByEmail(email);

        return optionalUser.map(user -> ResponseEntity.ok(Map.of(
                "name", user.getName(),
                "email", user.getEmail()
        ))).orElseGet(() -> ResponseEntity.status(404).body(Map.of("error", "Foydalanuvchi topilmadi")));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Frontend tokenni o‘chirish orqali logout qiladi
        return ResponseEntity.ok(Map.of("message", "Chiqildi"));
    }
}
