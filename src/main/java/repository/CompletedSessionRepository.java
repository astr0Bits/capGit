package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.CompletedSession;

@Repository
public interface CompletedSessionRepository extends JpaRepository<CompletedSession, Long> {
    // You can add custom query methods here if needed
}