package com.example.lecturemanagesystem.domain.repository;

import com.example.lecturemanagesystem.domain.entity.LectureSchedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ILectureScheduleRepository {
    Optional<LectureSchedule> findById(Long aLong);
    LectureSchedule save(LectureSchedule lectureSchedule);
    List<LectureSchedule> findAllByLectureAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
    Optional<LectureSchedule> findByIdWithPessimisticLock(Long lectureId);
}
