package com.example.lecturemanagesystem.interfaces.contoller;

import com.example.lecturemanagesystem.domain.entity.User;
import com.example.lecturemanagesystem.domain.repository.IUserRepository;
import com.example.lecturemanagesystem.testContainers.AbstractTestContainersTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext
class UserControllerTest extends AbstractTestContainersTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IUserRepository userRepository;

    @Test
    void 유저의_특강신청목록조회API_요청시_200응답을_반환한다() throws Exception {
        // given
        User user = userRepository.save( new User("테스트 사용자"));

        // when & then
        mockMvc.perform(get("/api/v1/users/{userId}/lectures", user.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}