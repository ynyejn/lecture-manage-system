package com.example.lecturemanagesystem.domain.service;

import com.example.lecturemanagesystem.domain.dto.LectureEnrollmentCommand;
import com.example.lecturemanagesystem.domain.dto.LectureEnrollmentInfo;
import com.example.lecturemanagesystem.domain.dto.LectureScheduleInfo;
import com.example.lecturemanagesystem.domain.dto.UserLectureSearchCommand;
import com.example.lecturemanagesystem.domain.entity.LectureSchedule;
import com.example.lecturemanagesystem.domain.entity.User;
import com.example.lecturemanagesystem.domain.repository.ILectureEnrollmentRepository;
import com.example.lecturemanagesystem.domain.repository.ILectureScheduleRepository;
import com.example.lecturemanagesystem.domain.repository.IUserRepository;
import com.example.lecturemanagesystem.support.exception.ApiErrorCode;
import com.example.lecturemanagesystem.support.exception.ApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class LectureEnrollmentServiceItTest {
    @Autowired
    private LectureEnrollmentService enrollmentService;
    @Autowired
    private ILectureScheduleRepository lectureScheduleRepository;
    @Autowired
    private IUserRepository userRepository;

    @Test
    void 유저가_특강_수강신청하면_수강신청정보를_반환한다() {
        // given
        User user = userRepository.save(new User("테스트 유저"));
        LectureSchedule lecture = lectureScheduleRepository.save(
                LectureSchedule.builder()
                        .title("테스트 강의")
                        .instructorName("강사1")
                        .lectureAt(LocalDateTime.now().plusDays(1))
                        .build()
        );

        // when
        LectureEnrollmentInfo result = enrollmentService.enroll(
                new LectureEnrollmentCommand(lecture.getId(), user.getId())
        );

        // then
        assertThat(result).isNotNull();
        assertThat(result.lectureId()).isEqualTo(lecture.getId());
        assertThat(result.userName()).isEqualTo(user.getName());
    }

    @Test
    void 수강신청한_유저의_특강목록을_조회하면_신청한_특강정보를_반환한다() {
        // given
        User user = userRepository.save(new User("테스트 유저"));
        LectureSchedule lecture = lectureScheduleRepository.save(
                LectureSchedule.builder()
                        .title("테스트 강의")
                        .instructorName("강사1")
                        .lectureAt(LocalDateTime.now().plusDays(1))
                        .build()
        );

        // 수강 신청
        enrollmentService.enroll(new LectureEnrollmentCommand(lecture.getId(), user.getId()));

        // when
        List<LectureScheduleInfo> results = enrollmentService.getUserLectures(
                new UserLectureSearchCommand(user.getId())
        );

        // then
        assertThat(results).hasSize(1);
        LectureScheduleInfo lectureInfo = results.get(0);
        assertThat(lectureInfo.title()).isEqualTo("테스트 강의");
        assertThat(lectureInfo.instructor()).isEqualTo("강사1");
    }

    @Test
    void 동시에_40명의_수강신청요청이_들어오면_30명은_신청성공하고_10명은_EXCEEDED_CAPACITY_예외가_발생한다() throws InterruptedException {
        // given
        LectureSchedule lecture = lectureScheduleRepository.save(
                LectureSchedule.builder()
                        .title("동시성 테스트 강의")
                        .instructorName("강사1")
                        .lectureAt(LocalDateTime.now().plusDays(1))
                        .build()
        );

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            users.add(userRepository.save(new User("User" + i)));
        }

        CountDownLatch latch = new CountDownLatch(40);
        List<CompletableFuture<Boolean>> futures = new ArrayList<>();

        // when
        for (User user : users) {
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
                try {
                    enrollmentService.enroll(new LectureEnrollmentCommand(lecture.getId(), user.getId()));
                    return true;
                } catch (ApiException e) {
                    assertThat(e.getApiErrorCode()).isEqualTo(ApiErrorCode.EXCEEDED_CAPACITY);
                    return false;
                } finally {
                    latch.countDown();
                }
            });
            futures.add(future);
        }

        latch.await(10, TimeUnit.SECONDS);

        // then
        long successCount = futures.stream()
                .map(CompletableFuture::join)
                .filter(success -> success)
                .count();

        long failureCount = futures.stream()
                .map(CompletableFuture::join)
                .filter(success -> !success)
                .count();

        LectureSchedule updatedLecture = lectureScheduleRepository.findById(lecture.getId()).get();

        assertThat(successCount).isEqualTo(30);
        assertThat(failureCount).isEqualTo(10);
        assertThat(updatedLecture.getEnrolledCount()).isEqualTo(30);
    }

    @Test
    void 같은강의를_5번_신청하면_첫번째만_성공하고_이후는_ALREADY_ENROLLED_LECTURE_예외가_발생한다() {
        // given
        LectureSchedule lecture = lectureScheduleRepository.save(
                LectureSchedule.builder()
                        .title("중복 신청 테스트 강의")
                        .instructorName("강사1")
                        .lectureAt(LocalDateTime.now().plusDays(1))
                        .build()
        );

        User user = userRepository.save(new User("중복 신청 테스트 유저"));

        // when & then
        // 첫 번째 신청은 성공
        assertDoesNotThrow(() ->
                enrollmentService.enroll(new LectureEnrollmentCommand(lecture.getId(), user.getId()))
        );

        // 이후 4번의 신청은 모두 실패
        for (int i = 0; i < 4; i++) {
            assertThatThrownBy(() ->
                    enrollmentService.enroll(new LectureEnrollmentCommand(lecture.getId(), user.getId()))
            )
                    .isInstanceOf(ApiException.class)
                    .hasMessage("이미 신청한 강의입니다.")
                    .extracting("apiErrorCode")
                    .isEqualTo(ApiErrorCode.ALREADY_ENROLLED_LECTURE);
        }

        // 최종적으로 한 번만 신청되었는지 확인
        LectureSchedule updatedLecture = lectureScheduleRepository.findById(lecture.getId()).get();
        assertThat(updatedLecture.getEnrolledCount()).isEqualTo(1);
    }

}