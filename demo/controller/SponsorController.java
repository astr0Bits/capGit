package com.capstone.demo.controller;

import com.capstone.demo.model.User;
import com.capstone.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sponsor")
public class SponsorController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/add-credits")
    public ResponseEntity<?> addCreditsToUser(@RequestParam String userEmail,
                                             @RequestParam double credits,
                                             Authentication authentication) {
        try {
            User sponsor = userService.findByEmail(authentication.getName()).orElseThrow();
            
            // Check if user is a sponsor
            if (sponsor.getRole() != User.Role.SPONSOR) {
                return ResponseEntity.badRequest().body(Map.of("error", "Only sponsors can add credits"));
            }
            
            Optional<User> targetUser = userService.findByEmail(userEmail);
            if (targetUser.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
            }
            
            userService.addCredits(targetUser.get(), credits);
            
            return ResponseEntity.ok(Map.of(
                "message", "Credits added successfully",
                "user", targetUser.get().getEmail(),
                "addedCredits", credits,
                "newBalance", targetUser.get().getCredits()
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}