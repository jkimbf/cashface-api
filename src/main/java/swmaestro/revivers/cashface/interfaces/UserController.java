package swmaestro.revivers.cashface.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swmaestro.revivers.cashface.application.UserService;
import swmaestro.revivers.cashface.domain.User;
import swmaestro.revivers.cashface.utils.JwtUtil;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/user/account/signup")
    public ResponseEntity<?> create(
            @RequestBody User resource
    ) throws URISyntaxException {
        User user = userService.registerUser(resource);

        String url = "/users/account/" + user.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }

    @PostMapping("/user/account/auth")
    public ResponseEntity<SessionResponseDto> create(
            @RequestBody SessionRequestDto resource
    ) {

        String email = resource.getEmail();
        String password = resource.getPassword();

        User user = userService.authenticate(email, password);

    }

}
