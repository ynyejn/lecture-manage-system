package com.example.lecturemanagesystem.domain.dto;


import com.example.lecturemanagesystem.support.exception.ApiErrorCode;
import com.example.lecturemanagesystem.support.exception.ApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LectureEnrollmentCommandTest {

    @Test
    void 특강_신청_Command_객체_생성시_lectureId가_null이면_INVALID_PARAMETER_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> new LectureEnrollmentCommand(null, 1L))
                .isInstanceOf(ApiException.class)
                .hasMessage("잘못된 요청입니다.");
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, -100})
    void 특강_신청_Command_객체_생성시_lectureId가_음수이면_INVALID_NAGATIVE_PARAMETER_예외가_발생한다(Long negativeLectureId) {
        // when & then
        assertThatThrownBy(() -> new LectureEnrollmentCommand(negativeLectureId, 1L))
                .isInstanceOf(ApiException.class)
                .hasMessage("음수 값은 허용되지 않습니다.")
                .extracting("apiErrorCode")
                .isEqualTo(ApiErrorCode.INVALID_NAGATIVE_PARAMETER);
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, -100})
    void 특강_신청_Command_객체_생성시_userId가_음수이면_INVALID_NAGATIVE_PARAMETER_예외가_발생한다(Long negativeUserId) {
        // when & then
        assertThatThrownBy(() -> new LectureEnrollmentCommand(1L, negativeUserId))
                .isInstanceOf(ApiException.class)
                .hasMessage("음수 값은 허용되지 않습니다.")
                .extracting("apiErrorCode")
                .isEqualTo(ApiErrorCode.INVALID_NAGATIVE_PARAMETER);

    }
}