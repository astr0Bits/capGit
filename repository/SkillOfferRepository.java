package com.capstone.demo.repository;


import com.capstone.demo.model.SkillOffer;
import com.capstone.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SkillOfferRepository extends JpaRepository<SkillOffer, Long> {
    List<SkillOffer> findByTeacherAndAvailable(User teacher, boolean available);
    List<SkillOffer> findByAvailable(boolean available);
    List<SkillOffer> findByCategory(SkillOffer.SkillCategory category);
    Optional<SkillOffer> findById(Long id);
}