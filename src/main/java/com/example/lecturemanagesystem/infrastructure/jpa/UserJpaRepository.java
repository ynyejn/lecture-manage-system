package com.example.lecturemanagesystem.infrastructure.jpa;

import com.example.lecturemanagesystem.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

}
