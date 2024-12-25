package com.example.lecturemanagesystem.infrastructure.jpa;

import com.example.lecturemanagesystem.domain.entity.LectureSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LectureScheduleJpaRepository extends JpaRepository<LectureSchedule, Long> {
    List<LectureSchedule> findAllByLectureAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
