package controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import dto.NotificationDto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    
    @GetMapping
    public ResponseEntity<?> getUserNotifications(Authentication authentication) {
        try {
            // Mock notifications - replace with real notifications from your services
            List<NotificationDto> notifications = Arrays.asList(
                createNotification(1L, "request", "New session request from Mohammed Ali", "calendar", "5 min ago", "text-blue-500"),
                createNotification(2L, "reminder", "Reminder: Session with Sarah starts in 2 hours", "clock", "1 hour ago", "text-orange-500"),
                createNotification(3L, "achievement", "You earned a new badge: Community Helper!", "trophy", "2 hours ago", "text-purple-500")
            );
            
            return ResponseEntity.ok(notifications);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    private NotificationDto createNotification(Long id, String type, String message, String icon, String time, String color) {
        NotificationDto notification = new NotificationDto();
        notification.setId(id);
        notification.setType(type);
        notification.setMessage(message);
        notification.setIcon(icon);
        notification.setTime(time);
        notification.setColor(color);
        notification.setCreatedAt(LocalDateTime.now());
        return notification;
    }
}