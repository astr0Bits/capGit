package service;

import repository.CouponRepository;
import repository.InternshipRepository;
import repository.ScholarshipRepository;
import repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import model.Coupon;
import model.Internship;
import model.Scholarship;
import model.User;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class SponsorService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ScholarshipRepository scholarshipRepository;
    
    @Autowired
    private CouponRepository couponRepository;
    
    @Autowired
    private InternshipRepository internshipRepository;
    
    public Scholarship createScholarship(User sponsor, String userEmail, 
                                       String title, String description, 
                                       double amount, LocalDateTime expiresAt) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
            
        Scholarship scholarship = new Scholarship();
        scholarship.setSponsor(sponsor);
        scholarship.setUser(user);
        scholarship.setTitle(title);
        scholarship.setDescription(description);
        scholarship.setAmount(amount);
        scholarship.setCreatedAt(LocalDateTime.now());
        scholarship.setExpiresAt(expiresAt);
        
        return scholarshipRepository.save(scholarship);
    }
    
    public Coupon createCoupon(User sponsor, String userEmail, String code,
                             String description, double discount, 
                             Coupon.CouponType type, LocalDateTime expiresAt) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
            
        Coupon coupon = new Coupon();
        coupon.setSponsor(sponsor);
        coupon.setUser(user);
        coupon.setCode(code);
        coupon.setDescription(description);
        coupon.setDiscount(discount);
        coupon.setType(type);
        coupon.setCreatedAt(LocalDateTime.now());
        coupon.setExpiresAt(expiresAt);
        
        return couponRepository.save(coupon);
    }
    
    public Internship createInternship(User sponsor, String title, String description,
                                     String company, Internship.InternshipType type,
                                     String location, boolean remote, 
                                     LocalDateTime applicationDeadline) {
        Internship internship = new Internship();
        internship.setSponsor(sponsor);
        internship.setTitle(title);
        internship.setDescription(description);
        internship.setCompany(company);
        internship.setType(type);
        internship.setLocation(location);
        internship.setRemote(remote);
        internship.setCreatedAt(LocalDateTime.now());
        internship.setApplicationDeadline(applicationDeadline);
        
        return internshipRepository.save(internship);
    }
    
    public List<Scholarship> getUserScholarships(User user) {
        return scholarshipRepository.findByUser(user);
    }
    
    public List<Coupon> getUserCoupons(User user) {
        return couponRepository.findByUser(user);
    }
    
    public List<Internship> getActiveInternships() {
        return internshipRepository.findByStatus(Internship.InternshipStatus.ACTIVE);
    }
}