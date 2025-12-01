package dto;

import java.util.Set;

import lombok.Data;
import model.User;

@Data
public class UserStatsDto {
	private int reputation;
    private int totalSessions;
    private int learnerSessions;
    private int mentorSessions;
    private double rating;
    private int points; 
    private int skillsLearned;
    private int totalPointsEarned; 
    private double hoursTaught;
    private Set<User.Badge> badges; 
    
	public int getReputation() {
		return reputation;
	}
	public void setReputation(int reputation) {
		this.reputation = reputation;
	}
	public int getTotalSessions() {
		return totalSessions;
	}
	public void setTotalSessions(int totalSessions) {
		this.totalSessions = totalSessions;
	}
	public int getLearnerSessions() {
		return learnerSessions;
	}
	public void setLearnerSessions(int learnerSessions) {
		this.learnerSessions = learnerSessions;
	}
	public int getMentorSessions() {
		return mentorSessions;
	}
	public void setMentorSessions(int mentorSessions) {
		this.mentorSessions = mentorSessions;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public int getSkillsLearned() {
		return skillsLearned;
	}
	public void setSkillsLearned(int skillsLearned) {
		this.skillsLearned = skillsLearned;
	}

	public double getHoursTaught() {
		return hoursTaught;
	}
	public void setHoursTaught(double hoursTaught) {
		this.hoursTaught = hoursTaught;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getTotalPointsEarned() {
		return totalPointsEarned;
	}
	public void setTotalPointsEarned(int totalPointsEarned) {
		this.totalPointsEarned = totalPointsEarned;
	}
	public Set<User.Badge> getBadges() {
		return badges;
	}
	public void setBadges(Set<User.Badge> badges) {
		this.badges = badges;
	} 
}