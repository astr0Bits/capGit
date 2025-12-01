package controller;

import model.SkillOffer;
import model.User;
import service.SkillOfferService;
import service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import dto.SkillOfferRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/skills")
public class SkillOfferController {
    
    @Autowired
    private SkillOfferService skillOfferService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping
    public ResponseEntity<?> createSkillOffer(@RequestBody SkillOfferRequest request, 
                                            Authentication authentication) {
        try {
            User teacher = userService.findByEmail(authentication.getName()).orElseThrow();
            
            if (teacher.getRole() != User.Role.TEACHER) {
                return ResponseEntity.badRequest().body(Map.of("error", "Only teachers can create skill offers"));
            }
            
            SkillOffer.SkillCategory category = SkillOffer.SkillCategory.valueOf(request.getCategory().toUpperCase());
            SkillOffer offer = skillOfferService.createSkillOffer(
                request.getTitle(), 
                request.getDescription(), 
                category, 
                teacher
            );
            
            return ResponseEntity.ok(offer);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping
    public ResponseEntity<List<SkillOffer>> getAllSkills() {
        List<SkillOffer> offers = skillOfferService.getAllAvailableOffers();
        return ResponseEntity.ok(offers);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<SkillOffer>> getSkillsByCategory(@PathVariable String category) {
        try {
            SkillOffer.SkillCategory skillCategory = SkillOffer.SkillCategory.valueOf(category.toUpperCase());
            List<SkillOffer> offers = skillOfferService.getOffersByCategory(skillCategory);
            return ResponseEntity.ok(offers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/my-offers")
    public ResponseEntity<List<SkillOffer>> getMyOffers(Authentication authentication) {
        User teacher = userService.findByEmail(authentication.getName()).orElseThrow();
        List<SkillOffer> offers = skillOfferService.getTeacherOffers(teacher);
        return ResponseEntity.ok(offers);
    }
}