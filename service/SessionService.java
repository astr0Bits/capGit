package com.capstone.demo.service;

import com.capstone.demo.model.*;
import com.capstone.demo.repository.CompletedSessionRepository;
import com.capstone.demo.repository.SessionRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    
    @Autowired
    private SessionRequestRepository sessionRequestRepository;
    
    @Autowired
    private CompletedSessionRepository completedSessionRepository;
    
    @Autowired
    private UserService userService;
    
    public SessionRequest createSessionRequest(SkillOffer skillOffer, User learner, int hours) {
        SessionRequest request = new SessionRequest();
        request.setSkillOffer(skillOffer);
        request.setLearner(learner);
        request.setRequestedHours(hours);
        request.setCreatedAt(LocalDateTime.now());
        request.setStatus(SessionRequest.SessionStatus.PENDING);
        
        return sessionRequestRepository.save(request);
    }
    
    public SessionRequest acceptSession(SessionRequest sessionRequest) {
        sessionRequest.setStatus(SessionRequest.SessionStatus.ACCEPTED);
        return sessionRequestRepository.save(sessionRequest);
    }
    
    public SessionRequest rejectSession(SessionRequest sessionRequest) {
        sessionRequest.setStatus(SessionRequest.SessionStatus.REJECTED);
        return sessionRequestRepository.save(sessionRequest);
    }
    
    public CompletedSession completeSession(SessionRequest sessionRequest, int actualHours) {
        // Update session status
        sessionRequest.setStatus(SessionRequest.SessionStatus.COMPLETED);
        sessionRequestRepository.save(sessionRequest);
        
        // Create completed session record
        CompletedSession completedSession = new CompletedSession();
        completedSession.setSessionRequest(sessionRequest);
        completedSession.setActualHours(actualHours);
        completedSession.setCreditsTransferred(actualHours); // 1 credit per hour
        completedSession.setCompletedAt(LocalDateTime.now());
        
        // Transfer credits from learner to teacher
        User learner = sessionRequest.getLearner();
        User teacher = sessionRequest.getSkillOffer().getTeacher();
        
        userService.deductCredits(learner, actualHours);
        userService.addCredits(teacher, actualHours);
        
        return completedSessionRepository.save(completedSession);
    }
    
    public List<SessionRequest> getLearnerSessions(User learner) {
        return sessionRequestRepository.findByLearner(learner);
    }
    
    public List<SessionRequest> getTeacherSessions(User teacher) {
        return sessionRequestRepository.findBySkillOfferTeacher(teacher);
    }

	public Optional<SessionRequest> getSessionRequestById(Long sessionId) {
        return sessionRequestRepository.findById(sessionId);
	}
}