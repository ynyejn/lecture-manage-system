package com.example.lecturemanagesystem.infrastructure.jpa;

import com.example.lecturemanagesystem.domain.entity.LectureSchedule;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureScheduleJpaRepository extends JpaRepository<LectureSchedule, Long> {
    List<LectureSchedule> findAllByLectureAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select l from LectureSchedule l where l.id=:lectureId")
    Optional<LectureSchedule> findByIdWithPessimisticLock(@Param("lectureId") Long lectureId);
}
