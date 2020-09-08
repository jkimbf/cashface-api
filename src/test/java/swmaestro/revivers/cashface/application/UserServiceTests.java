package swmaestro.revivers.cashface.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import swmaestro.revivers.cashface.domain.User;
import swmaestro.revivers.cashface.domain.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void registerUser() {
        User mockUser = User.builder()
                .id(1004)
                .userType("customer")
                .name("Revivers Kim")
                .email("test@revivers.com")
                .password("testpw")
                .gender('M')
                .age(20)
                .nationality("Korean")
                .build();

        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService.registerUser(User.builder().name("Revivers Kim").build());

        assertEquals("Revivers Kim", user.getName());
    }

    @Test
    public void authenticateWithValidAttributes() {
        String email = "test@revivers.com";
        String password = "testpw";

        User mockUser = User.builder().email(email).build();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(any(), any())).willReturn(true);

        User user = userService.authenticate(email, password);

        assertEquals(email, user.getEmail());
    }

    @Test
    public void authenticateWithNotExistedEmail() {
        String email = "x@revivers.com";
        String password = "testpw";

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());
        given(passwordEncoder.matches(any(), any())).willReturn(true);

        Exception exception = assertThrows(EmailNotExistedException.class, () -> {
            userService.authenticate(email, password);
        });

        String expectedMessage = "Email is not registered: x@revivers.com";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void authenticateWithWrongPassword() {
        String email = "test@revivers.com";
        String password = "x";

        User mockUser = User.builder().email(email).build();
        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(any(), any())).willReturn(false);

        Exception exception = assertThrows(PasswordWrongException.class, () -> {
            userService.authenticate(email, password);
        });

        String expectedMessage = "Password is wrong";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}