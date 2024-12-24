package com.example.lecturemanagesystem.support.exception;

public record ErrorResponse(
        String code,
        String message
) {
}
