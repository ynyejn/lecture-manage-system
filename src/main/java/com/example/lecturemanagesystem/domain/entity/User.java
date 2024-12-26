package com.example.lecturemanagesystem.domain.entity;

import com.example.lecturemanagesystem.support.exception.ApiErrorCode;
import com.example.lecturemanagesystem.support.exception.ApiException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Table(name = "user")
@RequiredArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30)
    private String name;
    @OneToMany(mappedBy = "user")
    private List<LectureEnrollment> lectureEnrollments = new ArrayList<>();



    public User(String name) {
        this.name = name;
    }



    public void validateEnrollment(LectureSchedule lecture) {
        // 이미 수강 중인 강의인지 확인
        if (lectureEnrollments.stream().anyMatch(enrollment -> enrollment.getLectureSchedule().equals(lecture))) {
            throw new ApiException(ApiErrorCode.ALREADY_ENROLLED_LECTURE);
        }
        // 강의 시간 중복 확인
        if (lectureEnrollments.stream()
                .anyMatch(enrollment -> {
                    LocalDateTime existingLectureTime = enrollment.getLectureSchedule().getLectureAt();
                    LocalDateTime newLectureTime = lecture.getLectureAt();
                    return existingLectureTime.isEqual(newLectureTime);
                })) {
            throw new ApiException(ApiErrorCode.DUPLICATE_TIME_SLOT);
        }
    }
}
