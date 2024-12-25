package com.example.lecturemanagesystem.domain.dto;

import com.example.lecturemanagesystem.support.exception.ApiErrorCode;
import com.example.lecturemanagesystem.support.exception.ApiException;

import java.time.LocalDate;

public record LectureAvailableSearchCommand (LocalDate date) {
    public LectureAvailableSearchCommand {
        validate(date);
    }

    private void validate(LocalDate date) {
        if (date == null) {
            throw new ApiException(ApiErrorCode.INVALID_PARAMETER);
        }

        if (date.isBefore(LocalDate.now())) {
            throw new ApiException(ApiErrorCode.INVALID_PARAMETER);
        }
    }
}
