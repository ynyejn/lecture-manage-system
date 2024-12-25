package com.example.lecturemanagesystem.interfaces.contoller;

import com.example.lecturemanagesystem.domain.entity.LectureSchedule;
import com.example.lecturemanagesystem.domain.entity.User;
import com.example.lecturemanagesystem.domain.repository.ILectureScheduleRepository;
import com.example.lecturemanagesystem.domain.repository.IUserRepository;
import com.example.lecturemanagesystem.interfaces.dto.LectureEnrollmentRequest;
import com.example.lecturemanagesystem.testContainers.AbstractTestContainersTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext
class LectureControllerTest extends AbstractTestContainersTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ILectureScheduleRepository lectureScheduleRepository;
    @Autowired
    private IUserRepository userRepository;
    private Long lectureId;
    private Long userId;

    @BeforeEach
    void setUp() {
        // 테스트용 강의 생성
        LectureSchedule lecture = createTestLecture();
        LectureSchedule savedLecture = lectureScheduleRepository.save(lecture);
        this.lectureId = savedLecture.getId();

        // 테스트용 사용자 생성
        User user = createTestUser();
        User savedUser = userRepository.save(user);
        this.userId = savedUser.getId();
    }
    // 테스트 데이터 생성을 위한 헬퍼 메서드
    private LectureSchedule createTestLecture() {
        return LectureSchedule.builder()
                .title("테스트 강의")
                .instructorName("알렉스 코치님")
                .lectureAt(LocalDateTime.of(2025, 3, 1, 14, 0))
                .build();
    }

    private User createTestUser() {
        return new User("테스트 사용자");
    }

    @Test
    void 특강신청API_요청시_200응답을_반환한다() throws Exception {
        // given
        LectureEnrollmentRequest request = new LectureEnrollmentRequest(userId);

        // when & then
        mockMvc.perform(post("/api/v1/lectures/{lectureId}", lectureId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 특강신청가능목록조회API_날짜파라미터전송시_200응답을_반환한다() throws Exception {
        // when & then
        mockMvc.perform(get("/api/v1/lectures/available")
                        .param("date", "20260101"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}