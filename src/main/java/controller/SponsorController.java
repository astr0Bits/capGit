package controller;

import service.SponsorService;
import service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import dto.CouponRequest;
import dto.InternshipRequest;
import dto.ScholarshipRequest;
import model.Coupon;
import model.Internship;
import model.Scholarship;
import model.User;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sponsor")
public class SponsorController {

	@Autowired
	private SponsorService sponsorService;

	@Autowired
	private UserService userService;

	@PostMapping("/scholarship")
	public ResponseEntity<?> createScholarship(@RequestBody ScholarshipRequest request,
			Authentication authentication) {
		try {
			User sponsor = userService.findByEmail(authentication.getName()).orElseThrow();

			if (sponsor.getRole() != User.Role.SPONSOR) {
				return ResponseEntity.badRequest().body(Map.of("error", "Only sponsors can create scholarships"));
			}

			Scholarship scholarship = sponsorService.createScholarship(
					sponsor, request.getUserEmail(), request.getTitle(),
					request.getDescription(), request.getAmount(), request.getExpiresAt()
					);

			return ResponseEntity.ok(scholarship);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}

	@PostMapping("/coupon")
	public ResponseEntity<?> createCoupon(@RequestBody CouponRequest request,
			Authentication authentication) {
		try {
			User sponsor = userService.findByEmail(authentication.getName()).orElseThrow();

			if (sponsor.getRole() != User.Role.SPONSOR) {
				return ResponseEntity.badRequest().body(Map.of("error", "Only sponsors can create coupons"));
			}

			Coupon coupon = sponsorService.createCoupon(
					sponsor, request.getUserEmail(), request.getCode(),
					request.getDescription(), request.getDiscount(), 
					request.getType(), request.getExpiresAt()
					);

			return ResponseEntity.ok(coupon);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}

	@PostMapping("/internship")
	public ResponseEntity<?> createInternship(@RequestBody InternshipRequest request,
			Authentication authentication) {
		try {
			User sponsor = userService.findByEmail(authentication.getName()).orElseThrow();

			if (sponsor.getRole() != User.Role.SPONSOR) {
				return ResponseEntity.badRequest().body(Map.of("error", "Only sponsors can create internships"));
			}

			Internship internship = sponsorService.createInternship(
					sponsor, request.getTitle(), request.getDescription(),
					request.getCompany(), request.getType(), request.getLocation(),
					request.isRemote(), request.getApplicationDeadline()
					);

			return ResponseEntity.ok(internship);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}

	@GetMapping("/internships")
	public ResponseEntity<List<Internship>> getActiveInternships() {
		List<Internship> internships = sponsorService.getActiveInternships();
		return ResponseEntity.ok(internships);
	}
}