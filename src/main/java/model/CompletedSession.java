package model;

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
    private int learnerPointsAwarded;
    private int teacherPointsAwarded;
    
	public Long getId() {return id;}
	
	public void setId(Long id) {this.id = id;}
	
	public SessionRequest getSessionRequest() {return sessionRequest;}
	
	public void setSessionRequest(SessionRequest sessionRequest) { this.sessionRequest = sessionRequest;}
	
	public int getActualHours() {return actualHours;}
	
	public void setActualHours(int actualHours) {this.actualHours = actualHours;}
	
	public LocalDateTime getCompletedAt() {return completedAt;}
	
	public void setCompletedAt(LocalDateTime completedAt) {this.completedAt = completedAt;}
    
	public int getLearnerPointsAwarded() { return learnerPointsAwarded; }
    
    public void setLearnerPointsAwarded(int learnerPointsAwarded) { this.learnerPointsAwarded = learnerPointsAwarded; }
    
    public int getTeacherPointsAwarded() { return teacherPointsAwarded; }
    
    public void setTeacherPointsAwarded(int teacherPointsAwarded) { this.teacherPointsAwarded = teacherPointsAwarded; }
    
}