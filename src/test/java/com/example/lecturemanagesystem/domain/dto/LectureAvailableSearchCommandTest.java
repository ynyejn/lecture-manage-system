package com.example.lecturemanagesystem.domain.dto;

import com.example.lecturemanagesystem.support.exception.ApiErrorCode;
import com.example.lecturemanagesystem.support.exception.ApiException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class LectureAvailableSearchCommandTest {
    @Nested
    class validateTest {

        @Test
        void 날짜가_null이면_INVALID_PARAMETER_예외가_발생한다() {
            // given
            LocalDate nullDate = null;

            // when & then
            assertThatThrownBy(() -> new LectureAvailableSearchCommand(nullDate))
                    .isInstanceOf(ApiException.class)
                    .hasMessage("잘못된 요청입니다.")
                    .extracting("apiErrorCode")
                    .isEqualTo(ApiErrorCode.INVALID_PARAMETER);
        }

        @Test
        void 과거날짜로_생성하면_INVALID_PARAMETER_예외가_발생한다() {
            // given
            LocalDate pastDate = LocalDate.now().minusDays(1);

            // when & then
            assertThatThrownBy(() -> new LectureAvailableSearchCommand(pastDate))
                    .isInstanceOf(ApiException.class)
                    .hasMessage("잘못된 요청입니다.")
                    .extracting("apiErrorCode")
                    .isEqualTo(ApiErrorCode.INVALID_PARAMETER);
        }
    }
}