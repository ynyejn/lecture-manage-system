package com.example.lecturemanagesystem.domain.entity;

import com.example.lecturemanagesystem.support.exception.ApiErrorCode;
import com.example.lecturemanagesystem.support.exception.ApiException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Table(name = "lecture_schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureSchedule extends BaseEntity {

    /**
     * 최대 수강 가능 인원
     */
    private static final int MAX_ENROLLMENT_COUNT = 30;

    /**
     * 수업 시간 (단위: 시간)
     */
    private static final int LECTURE_DURATION_HOURS = 2;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(name = "instructor_name", nullable = false, length = 30)
    private String instructorName;

    @Column(name = "enrolled_count", nullable = false)
    private int enrolledCount;

    @Column(name = "lecture_at", nullable = false)
    private LocalDateTime lectureAt;

    //잔여 수강 가능 인원
    public int getRemainingCapacity() {
        return MAX_ENROLLMENT_COUNT - enrolledCount;
    }

    // 수강 신청 가능한 시간인지 확인 (수업 시작 2시간 전까지만 수강 신청 가능)
    private boolean isEnrollableTime() {
        return this.lectureAt.isAfter(LocalDateTime.now().plusHours(LECTURE_DURATION_HOURS));
    }

    //     수강 가능한 인원인지 확인
    private boolean hasAvailableCapacity() {
        return enrolledCount < MAX_ENROLLMENT_COUNT;
    }

    public boolean isAvailableToEnroll() {
        return hasAvailableCapacity() && isEnrollableTime();
    }

    public void addEnrollment() {
        validateEnrollment();
        enrolledCount++;
    }

    private void validateEnrollment() {
        if (!hasAvailableCapacity()) {
            throw new ApiException(ApiErrorCode.EXCEEDED_CAPACITY);
        }
        if (!isEnrollableTime()) {
            throw new ApiException(ApiErrorCode.LECTURE_UNAVAILABLE);
        }
    }

    @Builder
    private LectureSchedule(String title, String instructorName, LocalDateTime lectureAt) {
        this.title = title;
        this.instructorName = instructorName;
        this.lectureAt = lectureAt;
        this.enrolledCount = 0;
    }


}
