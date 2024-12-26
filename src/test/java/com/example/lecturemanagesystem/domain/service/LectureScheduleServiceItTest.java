package com.example.lecturemanagesystem.domain.service;


import com.example.lecturemanagesystem.domain.dto.LectureAvailableSearchCommand;
import com.example.lecturemanagesystem.domain.dto.LectureScheduleInfo;
import com.example.lecturemanagesystem.domain.entity.LectureEnrollment;
import com.example.lecturemanagesystem.domain.entity.LectureSchedule;
import com.example.lecturemanagesystem.domain.entity.User;
import com.example.lecturemanagesystem.domain.repository.ILectureScheduleRepository;
import com.example.lecturemanagesystem.domain.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest
@ActiveProfiles("test")
class LectureScheduleServiceItTest {
    @Autowired
    private LectureScheduleService lectureScheduleService;
    @Autowired
    private ILectureScheduleRepository lectureScheduleRepository;
    @Autowired
    private IUserRepository userRepository;

    @Test
    void 특정날짜에_신청가능한_특강목록을_조회하면_수강가능한_특강정보목록을_반환한다() {
        // given
        LocalDateTime targetDate = LocalDateTime.of(2025, 1, 1, 14, 0);

        LectureSchedule availableLecture = lectureScheduleRepository.save(
                LectureSchedule.builder()
                        .title("수강가능 강의")
                        .instructorName("강사1")
                        .lectureAt(targetDate)
                        .build()
        );

        LectureSchedule fullLecture = lectureScheduleRepository.save(
                LectureSchedule.builder()
                        .title("수강불가 강의")
                        .instructorName("강사2")
                        .lectureAt(targetDate)
                        .build()
        );

        // 수강불가 강의 정원 채우기
        for (int i = 0; i < 30; i++) {
            fullLecture.addEnrollment();
        }
        lectureScheduleRepository.save(fullLecture);

        // when
        List<LectureScheduleInfo> results = lectureScheduleService.getAvailableLectures(
                new LectureAvailableSearchCommand(targetDate.toLocalDate())
        );

        // then
        assertThat(results).hasSize(1);
        assertThat(results.get(0).title()).isEqualTo(availableLecture.getTitle());
    }
}
