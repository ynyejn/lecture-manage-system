package com.example.lecturemanagesystem.domain.service;

import com.example.lecturemanagesystem.domain.dto.LectureAvailableSearchCommand;
import com.example.lecturemanagesystem.domain.dto.LectureScheduleInfo;
import com.example.lecturemanagesystem.domain.entity.LectureSchedule;
import com.example.lecturemanagesystem.domain.repository.ILectureScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectureScheduleServiceTest {
    @InjectMocks
    private LectureScheduleService lectureScheduleService;
    @Mock
    private ILectureScheduleRepository lectureScheduleRepository;
    @Test
    void 해당_날짜에_등록된_특강이_없으면_빈_리스트를_반환한다() {
        // given
        LocalDate dateWithNoLectures = LocalDate.of(2025, 12, 25);
        LectureAvailableSearchCommand command = new LectureAvailableSearchCommand(dateWithNoLectures);
        when(lectureScheduleRepository.findAllByLectureAtBetween(
                dateWithNoLectures.atStartOfDay(),
                dateWithNoLectures.atTime(LocalTime.MAX)))
                .thenReturn(Collections.emptyList());
        // when
        List<LectureScheduleInfo> result = lectureScheduleService.getAvailableLectures(command);

        // then
        assertThat(result)
                .as("조회 결과는 null이 아닌 빈 리스트여야 합니다")
                .isNotNull()
                .isEmpty();
    }

    @Test
    void 등록된_특강이_모두_수강_불가능한_상태면_빈_리스트를_반환한다() {
        // given
        LocalDate date = LocalDate.of(2025, 1, 2);
        LectureSchedule lecture =  LectureSchedule.builder()
                .title("강의")
                .instructorName("알렉스 코치님")
                .lectureAt(LocalDateTime.of(2025, 1, 2, 14, 0))
                .build();
        ReflectionTestUtils.setField(lecture, "enrolledCount", 30);  // 정원 초과 상태로 설정
        LectureAvailableSearchCommand command = new LectureAvailableSearchCommand(date);

        when(lectureScheduleRepository.findAllByLectureAtBetween(
                date.atStartOfDay(),
                date.atTime(LocalTime.MAX)))
                .thenReturn(List.of(lecture));

        // when
        List<LectureScheduleInfo> result = lectureScheduleService.getAvailableLectures(command);

        // then
        assertThat(result)
                .as("수강 가능한 특강이 없으므로 빈 리스트여야 합니다")
                .isEmpty();
    }
}