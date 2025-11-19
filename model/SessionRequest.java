package com.capstone.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "session_requests")
@Data
public class SessionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "skill_offer_id")
    private SkillOffer skillOffer;

    @ManyToOne
    @JoinColumn(name = "learner_id")
    private User learner;

    private LocalDateTime requestedDate;
    private int requestedHours;
    
    @Enumerated(EnumType.STRING)
    private SessionStatus status = SessionStatus.PENDING;

    private LocalDateTime createdAt;

    public enum SessionStatus {
        PENDING, ACCEPTED, REJECTED, COMPLETED, CANCELLED
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SkillOffer getSkillOffer() {
		return skillOffer;
	}

	public void setSkillOffer(SkillOffer skillOffer) {
		this.skillOffer = skillOffer;
	}

	public User getLearner() {
		return learner;
	}

	public void setLearner(User learner) {
		this.learner = learner;
	}

	public LocalDateTime getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(LocalDateTime requestedDate) {
		this.requestedDate = requestedDate;
	}

	public int getRequestedHours() {
		return requestedHours;
	}

	public void setRequestedHours(int requestedHours) {
		this.requestedHours = requestedHours;
	}

	public SessionStatus getStatus() {
		return status;
	}

	public void setStatus(SessionStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
    
}