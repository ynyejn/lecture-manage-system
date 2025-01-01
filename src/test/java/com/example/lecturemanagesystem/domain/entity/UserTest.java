package com.example.lecturemanagesystem.domain.entity;

import com.example.lecturemanagesystem.support.exception.ApiErrorCode;
import com.example.lecturemanagesystem.support.exception.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class UserTest {

    private User user;
    private LectureSchedule newLecture;

    @BeforeEach
    void setUp() {
        user = new User("테스트 사용자");
        newLecture = LectureSchedule.builder()
                .title("신청할 강의")
                .instructorName("강사1")
                .lectureAt(LocalDateTime.of(2025, 1, 1, 14, 0))
                .build();
    }

    @Test
    void 이미신청한_강의이면_ALREADY_ENROLLED_LECTURE_예외가_발생한다() {
        // given
        LectureEnrollment enrollment = new LectureEnrollment(user, newLecture);
        user.getLectureEnrollments().add(enrollment);

        // when & then
        assertThatThrownBy(() -> user.validateEnrollment(newLecture))
                .isInstanceOf(ApiException.class)
                .hasMessage("이미 신청한 강의입니다.")
                .extracting("apiErrorCode")
                .isEqualTo(ApiErrorCode.ALREADY_ENROLLED_LECTURE);
    }

    @Test
    void 동일시간대에_이미수강중인_강의가_있으면_DUPLICATE_TIME_SLOT_예외가_발생한다() {
        // given
        LectureSchedule existingLecture = LectureSchedule.builder()
                .title("기존 강의")
                .instructorName("강사2")
                .lectureAt(LocalDateTime.of(2025, 1, 1, 15, 0))  // 1시간 차이
                .build();

        LectureEnrollment enrollment = new LectureEnrollment(user, existingLecture);
        user.getLectureEnrollments().add(enrollment);

        // when & then
        assertThatThrownBy(() -> user.validateEnrollment(newLecture))
                .isInstanceOf(ApiException.class)
                .hasMessage("동일 시간대에 이미 수강 중인 강의가 있습니다.")
                .extracting("apiErrorCode")
                .isEqualTo(ApiErrorCode.DUPLICATE_TIME_SLOT);

    }
}