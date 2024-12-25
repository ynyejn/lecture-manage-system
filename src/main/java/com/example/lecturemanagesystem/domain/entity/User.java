package com.example.lecturemanagesystem.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Table(name = "user")
@RequiredArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30)
    private String name;

    public User(String name) {
        this.name = name;
    }
}
