package repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import model.Internship;
import model.User;

public interface InternshipRepository extends JpaRepository<Internship, Long> {
    List<Internship> findBySponsor(User sponsor);
    List<Internship> findByStatus(Internship.InternshipStatus status);
    List<Internship> findByApplicationDeadlineAfter(LocalDateTime date);
}