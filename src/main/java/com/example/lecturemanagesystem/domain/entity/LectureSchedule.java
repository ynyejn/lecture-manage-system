package com.example.lecturemanagesystem.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "lecture_schedule")
public class LectureSchedule extends BaseEntity{
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(name = "instructor_name", nullable = false, length = 30)
    private String instructorName;

    @Column(name = "enrolled_count", nullable = false)
    private int enrolledCount;

    @Column(name = "lecture_at", nullable = false)
    private LocalDateTime lectureAt;

}
