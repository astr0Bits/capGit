package repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.SessionRequest;
import model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRequestRepository extends JpaRepository<SessionRequest, Long> {
    List<SessionRequest> findByLearner(User learner);
    List<SessionRequest> findBySkillOfferTeacher(User teacher);
    Optional<SessionRequest> findById(Long id);
}