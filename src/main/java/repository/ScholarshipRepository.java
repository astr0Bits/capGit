package repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import model.Scholarship;
import model.User;

public interface ScholarshipRepository extends JpaRepository<Scholarship, Long> {
    List<Scholarship> findByUser(User user);
    List<Scholarship> findBySponsor(User sponsor);
}