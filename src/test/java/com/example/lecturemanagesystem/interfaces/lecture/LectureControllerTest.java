package com.example.lecturemanagesystem.interfaces.lecture;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LectureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 특강신청API_요청시_200응답을_반환한다() throws Exception {
        // given
        Long lectureId = 1L;
        LectureApplyRequest request = new LectureApplyRequest(1L);

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
                        .param("date", "20240101"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}