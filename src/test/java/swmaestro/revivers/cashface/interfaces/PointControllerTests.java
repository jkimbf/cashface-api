package swmaestro.revivers.cashface.interfaces;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import swmaestro.revivers.cashface.application.PointService;
import swmaestro.revivers.cashface.application.UserService;
import swmaestro.revivers.cashface.domain.Point;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PointController.class)
class PointControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PointService pointService;

    @MockBean
    private UserService userService;

    @Test
    public void list() throws Exception {
        List<Point> points = new ArrayList<>();
        points.add(Point.builder()
                .userId(1004)
                .adsType("Google Ads")
                .transactionType("Reward")
                .amount(10)
                .build()
        );
        points.add(Point.builder()
                .userId(1004)
                .adsType("Kakao Ads")
                .transactionType("Reward")
                .amount(12)
                .build()
        );

        given(pointService.getPointsByUserId(1004)).willReturn(points);

        mvc.perform(get("/users/1004/pointsHistory"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\"userId\":1004,\"adsType\":\"Google Ads\",\"transactionType\":\"Reward\",\"amount\":10"
                )))
                .andExpect(content().string(containsString(
                        "\"userId\":1004,\"adsType\":\"Kakao Ads\",\"transactionType\":\"Reward\",\"amount\":12"
                )));
    }

    @Test
    public void create() throws Exception {
        Point point = Point.builder()
                .userId(1004)
                .adsType("Kakao Ads")
                .transactionType("적립")
                .amount(10)
                .build();

        given(pointService.registerPoint(any())).willReturn(point);

        mvc.perform(post("/users/1004/point")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":1004,\"adsType\":\"Kakao Ads\",\"transactionType\":0,\"amount\":10}"))
                .andExpect(status().isCreated());

        verify(pointService).registerPoint(any());
    }

}