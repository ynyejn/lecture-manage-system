package com.example.lecturemanagesystem.domain.repository;

import com.example.lecturemanagesystem.domain.entity.LectureEnrollment;
import com.example.lecturemanagesystem.domain.entity.LectureSchedule;
import com.example.lecturemanagesystem.domain.entity.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface ILectureEnrollmentRepository {

    boolean existsByUserAndLectureSchedule(User user, LectureSchedule lectureSchedule);

    boolean existsByUserAndLectureAtBetween(User user, LocalDateTime start, LocalDateTime end);

    LectureEnrollment save(LectureEnrollment lectureEnrollment);

    List<LectureEnrollment> findAllByUser(User user);
}
