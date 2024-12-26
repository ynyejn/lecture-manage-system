package com.example.lecturemanagesystem.infrastructure.repository;

import com.example.lecturemanagesystem.domain.entity.LectureEnrollment;
import com.example.lecturemanagesystem.domain.entity.User;
import com.example.lecturemanagesystem.domain.repository.ILectureEnrollmentRepository;
import com.example.lecturemanagesystem.infrastructure.jpa.LectureEnrollmentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureEnrollmentRepositoryImpl implements ILectureEnrollmentRepository {

    private final LectureEnrollmentJpaRepository lectureEnrollmentJpaRepository;

    @Override
    public LectureEnrollment save(LectureEnrollment lectureEnrollment) {
        return lectureEnrollmentJpaRepository.save(lectureEnrollment);
    }

    @Override
    public List<LectureEnrollment> findAllByUser(User user) {
        return lectureEnrollmentJpaRepository.findAllByUser(user);
    }

    @Override
    public List<LectureEnrollment> findAll() {
        return lectureEnrollmentJpaRepository.findAll();
    }

}
