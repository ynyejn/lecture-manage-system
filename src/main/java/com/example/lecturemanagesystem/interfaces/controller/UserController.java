package com.example.lecturemanagesystem.interfaces.controller;

import com.example.lecturemanagesystem.domain.dto.LectureScheduleInfo;
import com.example.lecturemanagesystem.domain.dto.UserLectureSearchCommand;
import com.example.lecturemanagesystem.domain.service.LectureEnrollmentService;
import com.example.lecturemanagesystem.interfaces.dto.LectureScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final LectureEnrollmentService lectureEnrollmentService;
    /**
     * 특강 신청 완료 목록 조회 API
     */
    @GetMapping("/{userId}/lectures")
    public List<LectureScheduleResponse> getUserLectures(@PathVariable Long userId) {
        List<LectureScheduleInfo> lectures = lectureEnrollmentService.getUserLectures(UserLectureSearchCommand.of(userId));
        return lectures.stream()
                .map(LectureScheduleResponse::from)
                .toList();
    }
}
