package com.example.lecturemanagesystem.domain.dto;

import com.example.lecturemanagesystem.support.exception.ApiErrorCode;
import com.example.lecturemanagesystem.support.exception.ApiException;

public record LectureEnrollmentCommand(Long lectureId, Long userId) {
    public LectureEnrollmentCommand {
        validate(lectureId, userId);
    }

    private static void validate(Long lectureId, Long userId) {
        if (lectureId == null || userId == null) {
            throw new ApiException(ApiErrorCode.INVALID_PARAMETER);
        }
        if (lectureId < 0 || userId < 0) {
            throw new ApiException(ApiErrorCode.INVALID_NAGATIVE_PARAMETER);
        }
    }
}