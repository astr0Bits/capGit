package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByVerificationToken(String token);
    
    // REMOVE this line: List<User> findUsersExcludingId(Long userId);
}