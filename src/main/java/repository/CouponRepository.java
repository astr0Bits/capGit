package repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import model.Coupon;
import model.User;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findByUser(User user);
    List<Coupon> findBySponsor(User sponsor);
    Optional<Coupon> findByCode(String code);
}
