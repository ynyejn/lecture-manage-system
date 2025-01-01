package com.example.lecturemanagesystem.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Table(name = "lecture_enrollment", uniqueConstraints = {
        // user_id, lecture_schedule_id 컬럼의 값이 중복되지 않도록 유니크 제약조건 설정
        @UniqueConstraint(columnNames = {"user_id", "lecture_schedule_id"})
})
@RequiredArgsConstructor
public class LectureEnrollment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_schedule_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private LectureSchedule lectureSchedule;

    public LectureEnrollment(User user, LectureSchedule lectureSchedule) {
        this.user = user;
        this.lectureSchedule = lectureSchedule;
        user.getLectureEnrollments().add(this);
    }

    public void setLecture(LectureSchedule lectureSchedule) {
        this.lectureSchedule = lectureSchedule;
    }
}
