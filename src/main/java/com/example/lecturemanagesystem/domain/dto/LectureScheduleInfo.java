package com.example.lecturemanagesystem.domain.dto;

import com.example.lecturemanagesystem.domain.entity.LectureSchedule;

import java.time.LocalDateTime;

public record LectureScheduleInfo(
        String title,
        String instructor,
        long availableCapacity,
        LocalDateTime lectureAt
) {
    public static LectureScheduleInfo from(LectureSchedule lectureSchedule) {
        return new LectureScheduleInfo(
                lectureSchedule.getTitle(),
                lectureSchedule.getInstructorName(),
                lectureSchedule.getRemainingCapacity(),
                lectureSchedule.getLectureAt()
        );
    }
}
