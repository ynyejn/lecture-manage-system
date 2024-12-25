package com.example.lecturemanagesystem.infrastructure.repository;

import com.example.lecturemanagesystem.domain.entity.LectureEnrollment;
import com.example.lecturemanagesystem.domain.entity.LectureSchedule;
import com.example.lecturemanagesystem.domain.entity.User;
import com.example.lecturemanagesystem.domain.repository.ILectureEnrollmentRepository;
import com.example.lecturemanagesystem.infrastructure.jpa.LectureEnrollmentJpaRepository;
import com.example.lecturemanagesystem.infrastructure.query.LectureEnrollmentQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureEnrollmentRepositoryImpl implements ILectureEnrollmentRepository {

    private final LectureEnrollmentJpaRepository lectureEnrollmentJpaRepository;
    private final LectureEnrollmentQueryRepository lectureEnrollmentQueryRepository;

    @Override
    public boolean existsByUserAndLectureSchedule(User user, LectureSchedule lectureSchedule) {
        return lectureEnrollmentJpaRepository.existsByUserAndLectureSchedule(user, lectureSchedule);
    }

    @Override
    public boolean existsByUserAndLectureAtBetween(User user, LocalDateTime start,
                                                   LocalDateTime end) {
        return lectureEnrollmentQueryRepository.existsByUserAndLectureAtBetween(user, start, end);
    }

    @Override
    public LectureEnrollment save(LectureEnrollment lectureEnrollment) {
        return lectureEnrollmentJpaRepository.save(lectureEnrollment);
    }

    @Override
    public List<LectureEnrollment> findAllByUser(User user) {
        return lectureEnrollmentJpaRepository.findAllByUser(user);
    }

}
