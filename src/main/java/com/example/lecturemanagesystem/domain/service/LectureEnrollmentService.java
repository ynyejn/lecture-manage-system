package com.example.lecturemanagesystem.domain.service;

import com.example.lecturemanagesystem.domain.dto.*;
import com.example.lecturemanagesystem.domain.entity.LectureEnrollment;
import com.example.lecturemanagesystem.domain.entity.LectureSchedule;
import com.example.lecturemanagesystem.domain.entity.User;
import com.example.lecturemanagesystem.domain.repository.ILectureEnrollmentRepository;
import com.example.lecturemanagesystem.domain.repository.ILectureScheduleRepository;
import com.example.lecturemanagesystem.domain.repository.IUserRepository;
import com.example.lecturemanagesystem.support.exception.ApiErrorCode;
import com.example.lecturemanagesystem.support.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureEnrollmentService {

    private final ILectureScheduleRepository lectureScheduleRepository;
    private final ILectureEnrollmentRepository lectureEnrollmentRepository;
    private final IUserRepository userRepository;

    //신청
    @Transactional
    public LectureEnrollmentInfo enroll(LectureEnrollmentCommand command) {
        User user = findUser(command.userId());
        LectureSchedule lecture = findLecture(command.lectureId());

        user.validateEnrollment(lecture);

        // 수강 신청 처리
        return enrollAndSave(user, lecture);
    }

    public LectureEnrollmentInfo enrollAndSave(User user, LectureSchedule lecture) {
        lecture.addEnrollment();
        LectureEnrollment enrollment = lectureEnrollmentRepository.save(new LectureEnrollment(user, lecture));
        return LectureEnrollmentInfo.from(enrollment);
    }

    // 사용자 조회
    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ApiErrorCode.USER_NOT_FOUND));
    }

    // 특강 조회
    public LectureSchedule findLecture(Long lectureId) {
        return lectureScheduleRepository.findByIdWithPessimisticLock(lectureId)
                .orElseThrow(() -> new ApiException(ApiErrorCode.LECTURE_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<LectureScheduleInfo> getUserLectures(UserLectureSearchCommand command) {
        User user = findUser(command.userId());
        return lectureEnrollmentRepository.findAllByUser(user)
                .stream()
                .map(enrollment -> LectureScheduleInfo.from(enrollment.getLectureSchedule()))
                .toList();
    }
}
