package com.example.lecturemanagesystem.domain.dto;

import com.example.lecturemanagesystem.support.exception.ApiErrorCode;
import com.example.lecturemanagesystem.support.exception.ApiException;

public record UserLectureSearchCommand(Long userId) {
    public UserLectureSearchCommand {
        validate(userId);
    }

    public static UserLectureSearchCommand of(Long userId) {
        return new UserLectureSearchCommand(userId);
    }

    private void validate(Long userId) {
        if (userId == null || userId < 0) {
            throw new ApiException(ApiErrorCode.INVALID_PARAMETER);
        }
    }
}
