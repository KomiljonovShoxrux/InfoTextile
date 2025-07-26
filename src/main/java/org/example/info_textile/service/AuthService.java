package org.example.info_textile.service;

import org.example.info_textile.model.AuthResponse;
import org.example.info_textile.model.RegisterRequest;
import org.example.info_textile.model.Users;
import org.example.info_textile.model.entity.Role;
import org.example.info_textile.repository.UserRepo;
import org.example.info_textile.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {


    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String add(RegisterRequest request) {

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Bu email allaqachon ro'yxatdan o'tgan");
        }

        Users user = new Users();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // parol hashlanadi
        user.setRole(Role.USER);
        user.setBlocked(false);
        userRepo.save(user);

        return jwtUtil.createToken(
                user.getEmail(),
                user.getRole().name(),
                user.getName()
        );
    }
}
