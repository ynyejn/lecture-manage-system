package com.example.lecturemanagesystem.interfaces.lecture;

import com.example.lecturemanagesystem.domain.LectureSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lectures")
@RequiredArgsConstructor
public class LectureController {

    /**
     * TODO - 특강 신청 API를 작성해주세요.
     */
    @PostMapping("/{lectureId}")
    public LectureSchedule registerLecture(
            @PathVariable Long lectureId,
            @RequestBody LectureApplyRequest request
    ) {
        return null;
    }

    /**
     * TODO - 특강 신청 가능 목록 API를 작성해주세요.
     */
    @GetMapping("/available")
    public List<LectureSchedule> getAvailableLectures(
            @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date
    ) {
        return null;
    }

}
