package com.capstone.demo.repository;

import com.capstone.demo.model.CompletedSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedSessionRepository extends JpaRepository<CompletedSession, Long> {
    // You can add custom query methods here if needed
}