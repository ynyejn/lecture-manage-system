package com.example.lecturemanagesystem.interfaces.dto;

import com.example.lecturemanagesystem.domain.dto.LectureEnrollmentInfo;

public record LectureEnrollmentResponse(
        String userName,
        long lectureId,
        String lectureTitle,
        String instructor,
        String lectureAt
) {
    public static LectureEnrollmentResponse from(LectureEnrollmentInfo lectureEnrollmentInfo) {
        return new LectureEnrollmentResponse(
                lectureEnrollmentInfo.userName(),
                lectureEnrollmentInfo.lectureId(),
                lectureEnrollmentInfo.lectureTitle(),
                lectureEnrollmentInfo.instructor(),
                lectureEnrollmentInfo.lectureAt()
        );
    }
}
