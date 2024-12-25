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
@Transactional(readOnly = true)
public class LectureEnrollmentService {

    private final ILectureScheduleRepository lectureScheduleRepository;
    private final ILectureEnrollmentRepository lectureEnrollmentRepository;
    private final IUserRepository userRepository;

    //신청
    @Transactional
    public LectureEnrollmentInfo enroll(LectureEnrollmentCommand command) {
        User user = findUser(command.userId());
        LectureSchedule lecture = findLecture(command.lectureId());

        // 신청 가능 여부 검증
        validateEnrollment(user, lecture);

        // 수강 신청 처리
        LectureEnrollment lectureEnrollment = enrollUserToLecture(user, lecture);
        return LectureEnrollmentInfo.from(lectureEnrollment);
    }

    public LectureEnrollment enrollUserToLecture(User user, LectureSchedule lecture) {
        lecture.increaseEnrolledCount();
        return lectureEnrollmentRepository.save(LectureEnrollment.create(user, lecture));
    }


    // 사용자 조회
    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ApiErrorCode.USER_NOT_FOUND));
    }

    // 특강 조회
    public LectureSchedule findLecture(Long lectureId) {
        return lectureScheduleRepository.findById(lectureId)
                .orElseThrow(() -> new ApiException(ApiErrorCode.LECTURE_NOT_FOUND));
    }

    // 수강 신청 가능 여부 검증
    public void validateEnrollment(User user, LectureSchedule lecture) {
        // 이미 신청한 강의인지 확인
        if (lectureEnrollmentRepository.existsByUserAndLectureSchedule(user, lecture)) {
            throw new ApiException(ApiErrorCode.ALREADY_ENROLLED_LECTURE);
        }

        // 강의 시간 중복 확인
        if (lectureEnrollmentRepository.existsByUserAndLectureAtBetween(user,
                lecture.getLectureAt().minusHours(1),
                lecture.getLectureAt().plusHours(1))) {
            throw new ApiException(ApiErrorCode.DUPLICATE_TIME_SLOT);
        }
    }


    public List<LectureScheduleInfo> getUserLectures(UserLectureSearchCommand command) {
        User user = findUser(command.userId());
        return lectureEnrollmentRepository.findAllByUser(user)
                .stream()
                .map(enrollment -> LectureScheduleInfo.from(enrollment.getLectureSchedule()))
                .toList();
    }
}
