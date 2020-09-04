package swmaestro.revivers.cashface.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swmaestro.revivers.cashface.application.UserService;
import swmaestro.revivers.cashface.domain.User;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/account/signup")
    public ResponseEntity<?> create(
            @RequestBody User resource
    ) throws URISyntaxException {
        resource.setTotalPoints(0);
        resource.setCreatedDate( Date.valueOf(LocalDate.now()) );

        User user = userService.registerUser(resource);

        String url = "/users/account/" + user.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }

    @GetMapping("/users/{userId}/totalPoints")
    public Map<String, String> getTotalPoints(
            @PathVariable("userId") Integer userId
    ) {
        HashMap<String, String> jsonResult = new HashMap<>();
        jsonResult.put("points", Integer.toString(userService.getPointsById(userId)));

        return jsonResult;
    }

}
