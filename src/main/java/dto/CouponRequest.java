package dto;

import lombok.Data;
import model.Coupon;
import java.time.LocalDateTime;

@Data
public class CouponRequest {
    private String userEmail;
    private String code;
    private String description;
    private double discount;
    private Coupon.CouponType type;
    private LocalDateTime expiresAt;
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public Coupon.CouponType getType() {
		return type;
	}
	public void setType(Coupon.CouponType type) {
		this.type = type;
	}
	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}
    
}