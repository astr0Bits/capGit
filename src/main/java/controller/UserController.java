package controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import dto.UserStatsDto;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping("/stats")
    public ResponseEntity<?> getUserStats(Authentication authentication) {
        try {
            // For now, return mock stats - you can replace with real calculations
            UserStatsDto stats = new UserStatsDto();
            stats.setReputation(245);
            stats.setTotalSessions(42);
            stats.setLearnerSessions(25);
            stats.setMentorSessions(17);
            stats.setRating(4.5);
            stats.setSkillsLearned(5);
            stats.setHoursTaught(22.5);
            
            return ResponseEntity.ok(stats);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}