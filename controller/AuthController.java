package com.capstone.demo.controller;

import com.capstone.demo.dto.LoginRequest;
import com.capstone.demo.dto.RegisterRequest;
import com.capstone.demo.model.User;
import com.capstone.demo.security.JwtUtils;
import com.capstone.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional; // ADD THIS IMPORT

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UserService userService;
    
    @Autowired
    JwtUtils jwtUtils;
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            System.out.println("=== DEBUG REGISTRATION ===");
            System.out.println("Name: " + registerRequest.getName());
            System.out.println("Email: " + registerRequest.getEmail());
            System.out.println("Password: " + registerRequest.getPassword());
            System.out.println("Role: " + registerRequest.getRole());
            
            User.Role role = User.Role.valueOf(registerRequest.getRole().toUpperCase());
            
            User user = userService.registerUser(
                registerRequest.getName(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                role
            );
            
            System.out.println("✅ User registered with ID: " + user.getId());
            System.out.println("Stored password hash: " + user.getPassword());
            System.out.println("Email verified: " + user.isEmailVerified());
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully. Please check your email for verification.");
            response.put("userId", user.getId());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.out.println("❌ Registration error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/debug-login")
    public ResponseEntity<?> debugLogin(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("=== DEBUG LOGIN ===");
            System.out.println("Email: " + loginRequest.getEmail());
            System.out.println("Password provided: " + loginRequest.getPassword());
            
            // 1. Check if user exists
            Optional<User> userOpt = userService.findByEmail(loginRequest.getEmail());
            if (userOpt.isEmpty()) {
                System.out.println("❌ User not found in database");
                return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
            }
            
            User user = userOpt.get();
            System.out.println("✅ User found: " + user.getEmail());
            System.out.println("Stored password hash: " + user.getPassword());
            System.out.println("Role: " + user.getRole());
            System.out.println("Email verified: " + user.isEmailVerified());
            
            // 2. Test password manually using the SAME PasswordEncoder
            boolean passwordMatches = userService.verifyPassword(loginRequest.getPassword(), user.getPassword());
            System.out.println("Manual password verification: " + passwordMatches);
            
            if (!passwordMatches) {
                System.out.println("❌ Password doesn't match - encoding issue detected!");
                return ResponseEntity.badRequest().body(Map.of("error", "Password encoding mismatch"));
            }
            
            System.out.println("✅ Password verified successfully manually");
            
            // 3. Try authentication
            try {
                Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
                );
                System.out.println("🎉 Spring Security authentication successful!");
                
                String jwt = jwtUtils.generateJwtToken(authentication);
                return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "token", jwt,
                    "role", user.getRole()
                ));
                
            } catch (Exception e) {
                System.out.println("❌ Spring Security authentication failed: " + e.getMessage());
                e.printStackTrace();
                return ResponseEntity.badRequest().body(Map.of("error", "Spring Security auth failed: " + e.getMessage()));
            }
            
        } catch (Exception e) {
            System.out.println("❌ General error: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            
            User user = userService.findByEmail(loginRequest.getEmail()).orElseThrow();
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("id", user.getId());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());
            response.put("credits", user.getCredits());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid email or password"));
        }
    }
    
    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        boolean verified = userService.verifyEmail(token);
        if (verified) {
            return ResponseEntity.ok(Map.of("message", "Email verified successfully"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid verification token"));
        }
    }
    
}