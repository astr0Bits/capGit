package com.capstone.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "skills")
@Data
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private SkillCategory category;

    private String description;

    public enum SkillCategory {
        LANGUAGE, COOKING, CODING, MUSIC, SPORTS, ARTS, BUSINESS, ACADEMIC, OTHER
    }
}