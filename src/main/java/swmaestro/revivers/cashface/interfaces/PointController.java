package swmaestro.revivers.cashface.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swmaestro.revivers.cashface.application.PointService;
import swmaestro.revivers.cashface.application.UserService;
import swmaestro.revivers.cashface.domain.Point;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
public class PointController {

    @Autowired
    private PointService pointService;

    @Autowired
    private UserService userService;

    @GetMapping("/users/{userId}/pointsHistory")
    public List<Point> getPointsHistory(
            @PathVariable("userId") Integer userId
    ) {
        List<Point> points = pointService.getPointsByUserId(userId);
        return points;
    }

    @PostMapping("/users/{userId}/point")
    public ResponseEntity<?> createPoint(
            @Valid @RequestBody Point resource,
            @PathVariable("userId") Integer userId
    ) throws URISyntaxException {
        resource.setUserId(userId);
        resource.setDate(Date.valueOf(LocalDate.now()));

        Point point = pointService.registerPoint(resource);

        userService.updateTotalPoints(userId, resource.getAmount());

        String url = "/users/" + userId + "/point/" + point.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }

}
