package com.example.lecturemanagesystem.infrastructure.query;

import com.example.lecturemanagesystem.domain.entity.User;

import java.time.LocalDateTime;

public interface LectureEnrollmentQueryRepository {
    boolean existsByUserAndLectureAtBetween(User user, LocalDateTime start, LocalDateTime end);
}