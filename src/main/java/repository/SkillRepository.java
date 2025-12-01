package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Skill;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findByName(String name);
    List<Skill> findByCategory(Skill.SkillCategory category);
    List<Skill> findByNameContainingIgnoreCase(String name);
}