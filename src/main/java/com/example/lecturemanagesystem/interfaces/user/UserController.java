package com.example.lecturemanagesystem.interfaces.user;

import com.example.lecturemanagesystem.domain.LectureSchedule;
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
    /**
     * TODO - 특강 신청 완료 목록 조회 API를 작성해주세요.
     */
    @GetMapping("/{userId}/lectures")
    public List<LectureSchedule> getUserLectures(
            @PathVariable Long userId
    ) {
        return null;
    }
}
