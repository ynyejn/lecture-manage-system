package com.example.lecturemanagesystem.infrastructure.repository;

import com.example.lecturemanagesystem.domain.entity.LectureSchedule;
import com.example.lecturemanagesystem.domain.repository.ILectureScheduleRepository;
import com.example.lecturemanagesystem.infrastructure.jpa.LectureScheduleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureScheduleRepositoryImpl implements ILectureScheduleRepository {

    private final LectureScheduleJpaRepository lectureScheduleJpaRepository;

    @Override
    public Optional<LectureSchedule> findById(Long lectureId) {
        return lectureScheduleJpaRepository.findById(lectureId);
    }
    @Override
    public LectureSchedule save(LectureSchedule lectureSchedule) {
        return lectureScheduleJpaRepository.save(lectureSchedule);
    }
    @Override
    public List<LectureSchedule> findAllByLectureAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return lectureScheduleJpaRepository.findAllByLectureAtBetween(startOfDay, endOfDay);
    }
    @Override
    public Optional<LectureSchedule> findByIdWithPessimisticLock(Long lectureId) {
        return lectureScheduleJpaRepository.findByIdWithPessimisticLock(lectureId);
    }


}