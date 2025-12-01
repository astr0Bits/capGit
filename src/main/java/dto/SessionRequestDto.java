package dto;

import lombok.Data;

@Data
public class SessionRequestDto {
    private Long skillOfferId;
    private int requestedHours;
	public Long getSkillOfferId() {
		return skillOfferId;
	}
	public void setSkillOfferId(Long skillOfferId) {
		this.skillOfferId = skillOfferId;
	}
	public int getRequestedHours() {
		return requestedHours;
	}
	public void setRequestedHours(int requestedHours) {
		this.requestedHours = requestedHours;
	}
    
}