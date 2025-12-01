package controller;


import model.SessionRequest;
import model.SkillOffer;
import model.User;
import service.SessionService;
import service.SkillOfferService;
import service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import dto.SessionRequestDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private SkillOfferService skillOfferService;

	@Autowired
	private UserService userService;

	@PostMapping("/request")
	public ResponseEntity<?> requestSession(@RequestBody SessionRequestDto requestDto,
			Authentication authentication) {
		try {
			User learner = userService.findByEmail(authentication.getName()).orElseThrow();

			if (learner.getRole() != User.Role.LEARNER) {
				return ResponseEntity.badRequest().body(Map.of("error", "Only learners can request sessions"));
			}

			Optional<SkillOffer> skillOfferOpt = skillOfferService.getSkillOfferById(requestDto.getSkillOfferId());
			if (skillOfferOpt.isEmpty()) {
				return ResponseEntity.badRequest().body(Map.of("error", "Skill offer not found"));
			}

			SkillOffer skillOffer = skillOfferOpt.get();

			SessionRequest sessionRequest = sessionService.createSessionRequest(
					skillOffer, learner, requestDto.getRequestedHours()
					);

			return ResponseEntity.ok(sessionRequest);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}

	@PostMapping("/{sessionId}/accept")
	public ResponseEntity<?> acceptSession(@PathVariable Long sessionId, Authentication authentication) {
		try {
			User teacher = userService.findByEmail(authentication.getName()).orElseThrow();
			Optional<SessionRequest> sessionRequestOpt = sessionService.getSessionRequestById(sessionId);

			if (sessionRequestOpt.isEmpty()) {
				return ResponseEntity.badRequest().body(Map.of("error", "Session request not found"));
			}

			SessionRequest sessionRequest = sessionRequestOpt.get();

			// Check if the teacher owns the skill offer
			if (!sessionRequest.getSkillOffer().getTeacher().getId().equals(teacher.getId())) {
				return ResponseEntity.badRequest().body(Map.of("error", "Not authorized to accept this session"));
			}

			SessionRequest updatedRequest = sessionService.acceptSession(sessionRequest);
			return ResponseEntity.ok(updatedRequest);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}

	@PostMapping("/{sessionId}/complete")
	public ResponseEntity<?> completeSession(@PathVariable Long sessionId, 
			@RequestParam int actualHours,
			Authentication authentication) {
		try {
			User teacher = userService.findByEmail(authentication.getName()).orElseThrow();
			Optional<SessionRequest> sessionRequestOpt = sessionService.getSessionRequestById(sessionId);

			if (sessionRequestOpt.isEmpty()) {
				return ResponseEntity.badRequest().body(Map.of("error", "Session request not found"));
			}

			SessionRequest sessionRequest = sessionRequestOpt.get();

			// Check if the teacher owns the skill offer and session is accepted
			if (!sessionRequest.getSkillOffer().getTeacher().getId().equals(teacher.getId())) {
				return ResponseEntity.badRequest().body(Map.of("error", "Not authorized to complete this session"));
			}

			if (sessionRequest.getStatus() != SessionRequest.SessionStatus.ACCEPTED) {
				return ResponseEntity.badRequest().body(Map.of("error", "Session must be accepted before completion"));
			}

			var completedSession = sessionService.completeSession(sessionRequest, actualHours);
			return ResponseEntity.ok(completedSession);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}

	@GetMapping("/my-sessions")
	public ResponseEntity<List<SessionRequest>> getMySessions(Authentication authentication) {
		User user = userService.findByEmail(authentication.getName()).orElseThrow();

		List<SessionRequest> sessions;
		if (user.getRole() == User.Role.LEARNER) {
			sessions = sessionService.getLearnerSessions(user);
		} else {
			sessions = sessionService.getTeacherSessions(user);
		}

		return ResponseEntity.ok(sessions);
	}
}
