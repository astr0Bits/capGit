package dto;


import lombok.Data;
import model.Internship;

import java.time.LocalDateTime;

@Data
public class InternshipRequest {
    private String title;
    private String description;
    private String company;
    private Internship.InternshipType type;
    private String location;
    private boolean remote;
    private LocalDateTime applicationDeadline;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Internship.InternshipType getType() {
		return type;
	}
	public void setType(Internship.InternshipType type) {
		this.type = type;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public boolean isRemote() {
		return remote;
	}
	public void setRemote(boolean remote) {
		this.remote = remote;
	}
	public LocalDateTime getApplicationDeadline() {
		return applicationDeadline;
	}
	public void setApplicationDeadline(LocalDateTime applicationDeadline) {
		this.applicationDeadline = applicationDeadline;
	}
    
    
}