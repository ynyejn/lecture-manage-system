package com.example.lecturemanagesystem.infrastructure.jpa;

import com.example.lecturemanagesystem.domain.entity.LectureEnrollment;
import com.example.lecturemanagesystem.domain.entity.LectureSchedule;
import com.example.lecturemanagesystem.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LectureEnrollmentJpaRepository extends JpaRepository<LectureEnrollment, Long> {

    boolean existsByUserAndLectureSchedule(User user, LectureSchedule lectureSchedule);

    List<LectureEnrollment> findAllByUser(User user);
}
