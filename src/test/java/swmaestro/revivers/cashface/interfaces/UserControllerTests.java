package swmaestro.revivers.cashface.interfaces;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import swmaestro.revivers.cashface.application.UserIdNotExistedException;
import swmaestro.revivers.cashface.application.UserService;
import swmaestro.revivers.cashface.domain.User;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void create() throws Exception {
        User user = User.builder()
                .id(1004)
                .userType("customer")
                .name("Revivers Kim")
                .email("test@revivers.com")
                .password("testpw")
                .gender('M')
                .age(20)
                .nationality("Korean")
                .build();

        given(userService.registerUser(any())).willReturn(user);


        mvc.perform(post("/users/account/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userType\":\"customer\",\"name\":\"Revivers Kim\",\"email\":\"test@revivers.com\"," +
                        "\"password\":\"testpw\",\"gender\":\"M\",\"age\":20,\"nationality\":\"Korean\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/users/account/1004"));

        verify(userService).registerUser(any());
    }

    @Test
    public void getTotalPoints() throws Exception {
        given(userService.getPointsById(1004)).willReturn(10000);

        mvc.perform(get("/users/1004/totalPoints"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"points\":10000")));
    }

    @Test
    public void getTotalPointsOfNotExistingUser() throws Exception {
        Integer invalidUserId = 9999;

        given(userService.getPointsById(invalidUserId))
                .willThrow(UserIdNotExistedException.class);

        mvc.perform(get("/users/9999/totalPoints")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserIdNotExistedException));
    }

}