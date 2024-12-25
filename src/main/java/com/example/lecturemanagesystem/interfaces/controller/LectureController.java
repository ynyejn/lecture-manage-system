package com.example.lecturemanagesystem.interfaces.controller;

import com.example.lecturemanagesystem.domain.dto.LectureScheduleInfo;
import com.example.lecturemanagesystem.domain.dto.LectureAvailableSearchCommand;
import com.example.lecturemanagesystem.domain.service.LectureEnrollmentService;
import com.example.lecturemanagesystem.domain.service.LectureScheduleService;
import com.example.lecturemanagesystem.interfaces.dto.LectureEnrollmentRequest;
import com.example.lecturemanagesystem.interfaces.dto.LectureEnrollmentResponse;
import com.example.lecturemanagesystem.interfaces.dto.LectureScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lectures")
@RequiredArgsConstructor
public class LectureController {
    private final LectureEnrollmentService lectureEnrollmentService;
    private final LectureScheduleService lectureScheduleService;

    /**
     * 특강 신청 API
     */
    @PostMapping("/{lectureId}")
    public LectureEnrollmentResponse enroll(
            @PathVariable Long lectureId,
            @RequestBody LectureEnrollmentRequest request
    ) {
        return LectureEnrollmentResponse.from(lectureEnrollmentService.enroll(request.toCommand(lectureId)));
    }

    /**
     * 특강 신청 가능 목록 API
     */
    @GetMapping("/available")
    public List<LectureScheduleResponse> getAvailableLectures(
            @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date
    ) {
        List<LectureScheduleInfo> infos = lectureScheduleService.getAvailableLectures(new LectureAvailableSearchCommand(date));
        return infos.stream()
                .map(LectureScheduleResponse::from)
                .toList();
    }

}
