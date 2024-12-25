package com.example.lecturemanagesystem.domain.service;

import com.example.lecturemanagesystem.domain.dto.LectureAvailableSearchCommand;
import com.example.lecturemanagesystem.domain.dto.LectureScheduleInfo;
import com.example.lecturemanagesystem.domain.entity.LectureSchedule;
import com.example.lecturemanagesystem.domain.repository.ILectureScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureScheduleService {
    private final ILectureScheduleRepository lectureScheduleRepository;

    public List<LectureScheduleInfo> getAvailableLectures(LectureAvailableSearchCommand command) {
        LocalDateTime startDateTime = command.date().atStartOfDay();
        LocalDateTime endDateTime = command.date().atTime(LocalTime.MAX);

        return lectureScheduleRepository.findAllByLectureAtBetween(startDateTime, endDateTime)
                .stream()
                .filter(LectureSchedule::isAvailableToEnroll)   // 수강 가능한 강의만 필터링
                .map(LectureScheduleInfo::from)
                .toList();
    }
}
