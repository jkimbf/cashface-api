package swmaestro.revivers.cashface.interfaces;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import swmaestro.revivers.cashface.application.EmailNotExistedException;
import swmaestro.revivers.cashface.application.PasswordWrongException;
import swmaestro.revivers.cashface.application.UserService;
import swmaestro.revivers.cashface.domain.User;
import swmaestro.revivers.cashface.utils.JwtUtil;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SessionController.class)
class SessionControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @Test
    public void createWithValidAttributes() throws Exception {
        Integer id = 1004;
        String name = "Revivers Kim";
        String email = "test@revivers.com";
        String password = "test";

        User mockUser = User.builder().id(id).name(name).email(email).password(password).build();

        given(userService.authenticate(email, password)).willReturn(mockUser);

        given(jwtUtil.createToken(id, name)).willReturn("header.payload.signature");

        mvc.perform(post("/users/account/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@revivers.com\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/users/account/auth"))
                .andExpect(content().string(containsString("{\"accessToken\":\"header.payload.signature\"}")));
    }

    @Test
    public void createdWithNotExistedEmail() throws Exception {
        given(userService.authenticate("x@revivers.com", "test"))
                .willThrow(EmailNotExistedException.class);

        mvc.perform(post("/users/account/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"x@revivers.com\",\"password\":\"test\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate("x@revivers.com", "test");
    }

    @Test
    public void createWithWrongPassword() throws Exception {
        given(userService.authenticate("test@revivers.com", "x"))
                .willThrow(PasswordWrongException.class);

        mvc.perform(post("/users/account/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@revivers.com\",\"password\":\"x\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate("test@revivers.com", "x");
    }

}