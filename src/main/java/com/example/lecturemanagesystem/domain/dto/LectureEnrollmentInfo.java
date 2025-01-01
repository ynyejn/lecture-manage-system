package com.example.lecturemanagesystem.domain.dto;

import com.example.lecturemanagesystem.domain.entity.LectureEnrollment;
import com.example.lecturemanagesystem.domain.entity.LectureSchedule;
import com.example.lecturemanagesystem.domain.entity.User;

public record LectureEnrollmentInfo(
        String userName,
        long lectureId,
        String lectureTitle,
        String instructor,
        String lectureAt
) {
    public static LectureEnrollmentInfo from(LectureEnrollment lectureEnrollment) {
        LectureSchedule lectureSchedule = lectureEnrollment.getLectureSchedule();
        User user = lectureEnrollment.getUser();
        return new LectureEnrollmentInfo(
                user.getName(),
                lectureSchedule.getId(),
                lectureSchedule.getTitle(),
                lectureSchedule.getInstructorName(),
                lectureSchedule.getLectureAt().toString()
        );
    }
}
