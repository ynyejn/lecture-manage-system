package com.example.lecturemanagesystem.interfaces.dto;


import com.example.lecturemanagesystem.domain.dto.LectureEnrollmentCommand;

public record LectureEnrollmentRequest(Long userId) {
    public LectureEnrollmentCommand toCommand(Long lectureId) {
        return new LectureEnrollmentCommand(lectureId, this.userId());
    }
}
