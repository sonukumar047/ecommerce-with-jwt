package com.example.e_commerce.controller;

import com.example.e_commerce.dto.AuthRequest;
import com.example.e_commerce.dto.AuthResponse;
import com.example.e_commerce.entity.Role;
import com.example.e_commerce.entity.Users;
import com.example.e_commerce.service.UsersService;
import com.example.e_commerce.serviceImpl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsersService usersService;
    @Autowired
    private JwtService jwtService;


    @PostMapping("/signup")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> signup(@RequestBody Users users) {
        Users createdUser = usersService.createUsers(users);
        return new ResponseEntity<>("User created successfully with userID: "+
                createdUser.getUserId(), HttpStatus.CREATED);
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<String> signupAdmin(@RequestBody Users user) {
        user.setRole(Role.ADMIN);
        Users createdUser = usersService.createUsers(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Admin created with ID: " + createdUser.getUserId());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        Users user = usersService.getUsersByUsername(request.getUsername());
        String jwt = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
