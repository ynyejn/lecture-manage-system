package com.example.lecturemanagesystem.domain.dto;

import com.example.lecturemanagesystem.support.exception.ApiErrorCode;
import com.example.lecturemanagesystem.support.exception.ApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class UserLectureSearchCommandTest {
    @Test
    void userId가_null이면_INVALID_PARAMETER_예외가_발생한다() {
        assertThatThrownBy(() -> UserLectureSearchCommand.of(null))
                .isInstanceOf(ApiException.class)
                .hasMessage("잘못된 요청입니다.")
                .extracting("apiErrorCode")
                .isEqualTo(ApiErrorCode.INVALID_PARAMETER);
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, -100L})
    void userId가_음수이면_INVALID_PARAMETER_예외가_발생한다(long negativeUserId) {
        assertThatThrownBy(() -> UserLectureSearchCommand.of(negativeUserId))
                .isInstanceOf(ApiException.class)
                .hasMessage("잘못된 요청입니다.")
                .extracting("apiErrorCode")
                .isEqualTo(ApiErrorCode.INVALID_PARAMETER);
    }
}