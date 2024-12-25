package com.example.lecturemanagesystem.infrastructure.query;

import com.example.lecturemanagesystem.domain.entity.QLectureEnrollment;
import com.example.lecturemanagesystem.domain.entity.QLectureSchedule;
import com.example.lecturemanagesystem.domain.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static com.example.lecturemanagesystem.domain.entity.QLectureEnrollment.lectureEnrollment;
import static com.example.lecturemanagesystem.domain.entity.QLectureSchedule.lectureSchedule;

@Repository
@RequiredArgsConstructor
public class LectureEnrollmentQueryRepositoryImpl implements LectureEnrollmentQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByUserAndLectureAtBetween(User user, LocalDateTime start, LocalDateTime end) {
        Integer result = queryFactory
                .selectOne()
                .from(lectureEnrollment)
                .join(lectureEnrollment.lectureSchedule, lectureSchedule)
                .where(
                        lectureEnrollment.user.eq(user)
                                .and(lectureSchedule.lectureAt.between(start, end))
                )
                .fetchFirst();

        return result != null;
    }
}
