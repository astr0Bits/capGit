package com.capstone.demo.repository;

import com.capstone.demo.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findByName(String name);
    List<Skill> findByCategory(Skill.SkillCategory category);
    List<Skill> findByNameContainingIgnoreCase(String name);
}