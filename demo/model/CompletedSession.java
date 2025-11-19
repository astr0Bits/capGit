package com.capstone.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "completed_sessions")
@Data
public class CompletedSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_request_id")
    private SessionRequest sessionRequest;

    private int actualHours;
    private LocalDateTime completedAt;
    private double creditsTransferred;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SessionRequest getSessionRequest() {
		return sessionRequest;
	}
	public void setSessionRequest(SessionRequest sessionRequest) {
		this.sessionRequest = sessionRequest;
	}
	public int getActualHours() {
		return actualHours;
	}
	public void setActualHours(int actualHours) {
		this.actualHours = actualHours;
	}
	public LocalDateTime getCompletedAt() {
		return completedAt;
	}
	public void setCompletedAt(LocalDateTime completedAt) {
		this.completedAt = completedAt;
	}
	public double getCreditsTransferred() {
		return creditsTransferred;
	}
	public void setCreditsTransferred(double creditsTransferred) {
		this.creditsTransferred = creditsTransferred;
	}
    
    
}