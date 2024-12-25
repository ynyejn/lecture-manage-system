package com.example.lecturemanagesystem.domain.entity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.example.lecturemanagesystem.support.exception.ApiErrorCode;
import com.example.lecturemanagesystem.support.exception.ApiException;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;


@DisplayName("LectureSchedule 테스트")
class LectureScheduleTest {

//    @Test
//    void 수강인원이_최대정원에_도달하면_EXCEEDED_CAPACITY_예외가_발생한다() {
//        // given
//        LectureSchedule lecture = LectureSchedule.builder()
//                .title("테스트 강의")
//                .instructorName("알렉스 코치님")
//                .lectureAt(LocalDateTime.now().plusDays(1))
//                .build();
//        ReflectionTestUtils.setField(lecture, "enrolledCount", 30);
//
//        // when & then
//        assertThatThrownBy(() -> lecture.increaseEnrolledCount())
//                .isInstanceOf(ApiException.class)
//                .hasMessage("수강 인원이 초과되었습니다.")
//                .extracting("apiErrorCode")
//                .isEqualTo(ApiErrorCode.EXCEEDED_CAPACITY);
//
//        // 예외 전후 수강 인원 비교
//        assertThat(ReflectionTestUtils.getField(lecture, "enrolledCount"))
//                .as("예외 발생 후에도 수강 인원이 변경되지 않아야 합니다")
//                .isEqualTo(30);
//    }

    @Test
    void 현재_진행중인_강의는_LECTURE_UNAVAILABLE_예외가_발생한다() {
        // given
        LocalDateTime currentLectureTime = LocalDateTime.now().minusHours(1);
        LectureSchedule lecture = LectureSchedule.builder()
                .title("진행중인 강의")
                .instructorName("알렉스 코치님")
                .lectureAt(currentLectureTime)
                .build();
        // 테스트 전 수강 인원
        int initialEnrollmentCount = (int) ReflectionTestUtils.getField(lecture, "enrolledCount");


        // then
        assertThatThrownBy(() -> lecture.increaseEnrolledCount())
                .isInstanceOf(ApiException.class)
                .hasMessage("신청 불가능한 강의입니다.")
                .extracting("apiErrorCode")
                .isEqualTo(ApiErrorCode.LECTURE_UNAVAILABLE);

        // 예외 전후 수강 인원 비교
        assertThat(ReflectionTestUtils.getField(lecture, "enrolledCount"))
                .as("예외 발생 후에도 수강 인원이 변경되지 않아야 합니다")
                .isEqualTo(initialEnrollmentCount);
    }

}
