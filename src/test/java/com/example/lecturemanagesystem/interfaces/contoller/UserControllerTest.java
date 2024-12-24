package com.example.lecturemanagesystem.interfaces.contoller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 유저의_특강신청목록조회API_요청시_200응답을_반환한다() throws Exception {
        // given
        Long userId = 1L;

        // when & then
        mockMvc.perform(get("/api/v1/users/{userId}/lectures", userId))
                .andDo(print())
                .andExpect(status().isOk());
    }
}