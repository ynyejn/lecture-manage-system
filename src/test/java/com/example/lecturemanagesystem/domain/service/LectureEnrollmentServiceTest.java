package com.example.lecturemanagesystem.domain.service;

import com.example.lecturemanagesystem.domain.dto.LectureAvailableSearchCommand;
import com.example.lecturemanagesystem.domain.dto.LectureEnrollmentCommand;
import com.example.lecturemanagesystem.domain.dto.LectureScheduleInfo;
import com.example.lecturemanagesystem.domain.dto.UserLectureSearchCommand;
import com.example.lecturemanagesystem.domain.entity.LectureSchedule;
import com.example.lecturemanagesystem.domain.entity.User;
import com.example.lecturemanagesystem.domain.repository.ILectureEnrollmentRepository;
import com.example.lecturemanagesystem.domain.repository.ILectureScheduleRepository;
import com.example.lecturemanagesystem.domain.repository.IUserRepository;
import com.example.lecturemanagesystem.support.exception.ApiErrorCode;
import com.example.lecturemanagesystem.support.exception.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("LectureEnrollmentService 테스트")
class LectureEnrollmentServiceTest {
    @InjectMocks
    private LectureEnrollmentService lectureEnrollmentService;
    @Mock
    private ILectureEnrollmentRepository lectureEnrollmentRepository;
    @Mock
    private ILectureScheduleRepository lectureScheduleRepository;
    @Mock
    private IUserRepository userRepository;

    private User user;
    private LectureSchedule lecture;
    private LectureEnrollmentCommand command;
    private static final Long USER_ID = 1L;
    private static final Long LECTURE_ID = 1L;

    @BeforeEach
    void setUp() {
        user = new User("유저1");
        lecture = LectureSchedule.builder()
                .title("강의")
                .instructorName("알렉스 코치님")
                .lectureAt(LocalDateTime.of(2025, 1, 2, 14, 0))
                .build();
        command = new LectureEnrollmentCommand(LECTURE_ID, USER_ID);
    }


    @Nested
    @DisplayName("특강 신청 테스트")
    class LectureEnrollmentTest {
        @Test
        void 특강신청시_존재하지않는_유저아이디이면_USER_NOT_FOUND_예외가_발생한다() {
            // given
            when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> lectureEnrollmentService.findUser(USER_ID))
                    .isInstanceOf(ApiException.class)
                    .hasMessage("사용자를 찾을 수 없습니다.")
                    .extracting("apiErrorCode")
                    .isEqualTo(ApiErrorCode.USER_NOT_FOUND);
        }

        @Test
        void 특강신청시_존재하지않는_강의이면_LECTURE_NOT_FOUND_예외가_발생한다() {
            // given
            when(lectureScheduleRepository.findByIdWithPessimisticLock(LECTURE_ID))
                    .thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> lectureEnrollmentService.findLecture(LECTURE_ID))
                    .isInstanceOf(ApiException.class)
                    .hasMessage("강의를 찾을 수 없습니다.")
                    .extracting("apiErrorCode")
                    .isEqualTo(ApiErrorCode.LECTURE_NOT_FOUND);
        }
    }


    @Nested
    @DisplayName("특강 신청 완료 목록 조회 테스트")
    class getUserLecturesTest {
        @Test
        void 특강_신청_완료_목록_조회시_신청한_특강이_없는_사용자조회하면_빈_리스트를_반환한다() {
            // given
            when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
            when(lectureEnrollmentRepository.findAllByUser(user)).thenReturn(Collections.emptyList());

            // when
            List<LectureScheduleInfo> result = lectureEnrollmentService.getUserLectures(
                    UserLectureSearchCommand.of(USER_ID)
            );

            // then
            assertThat(result)
                    .as("수강 신청 내역이 없으므로 빈 리스트여야 합니다")
                    .isNotNull()
                    .isEmpty();
        }
    }
}