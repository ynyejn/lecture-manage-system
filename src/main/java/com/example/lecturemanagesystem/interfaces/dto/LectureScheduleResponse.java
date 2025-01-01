package com.example.lecturemanagesystem.interfaces.dto;

import com.example.lecturemanagesystem.domain.dto.LectureScheduleInfo;

import java.time.LocalDateTime;

public record LectureScheduleResponse(String title,
                                      String instructor,
                                      long availableCapacity,
                                      LocalDateTime lectureAt) {
    public static LectureScheduleResponse from(LectureScheduleInfo lectureScheduleInfo) {
        return new LectureScheduleResponse(
                lectureScheduleInfo.title(),
                lectureScheduleInfo.instructor(),
                lectureScheduleInfo.availableCapacity(),
                lectureScheduleInfo.lectureAt()
        );
    }
}
