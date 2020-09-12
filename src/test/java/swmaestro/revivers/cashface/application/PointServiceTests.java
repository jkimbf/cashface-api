package swmaestro.revivers.cashface.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import swmaestro.revivers.cashface.domain.Point;
import swmaestro.revivers.cashface.domain.PointRepository;
import swmaestro.revivers.cashface.domain.User;
import swmaestro.revivers.cashface.domain.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class PointServiceTests {

    private PointService pointService;

    @Mock
    private PointRepository pointRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        pointService = new PointService(pointRepository, userRepository);
    }

    @Test
    public void getPointsByUserId() {
        List<Point> mockPoints = new ArrayList<>();
        mockPoints.add(Point.builder()
                .userId(1004)
                .adsType("Google Ads")
                .transactionType("적립")
                .amount(10)
                .build()
        );
        mockPoints.add(Point.builder()
                .userId(1004)
                .adsType("Kakao Ads")
                .transactionType("적립")
                .amount(13)
                .build()
        );

        given(pointRepository.findAllByUserId(1004)).willReturn(mockPoints);

        List<Point> points = pointService.getPointsByUserId(1004);

        assertEquals(points.get(0).getAmount(), 10);
        assertEquals(points.get(0).getAdsType(), "Google Ads");
        assertEquals(points.get(1).getAmount(), 13);
        assertEquals(points.get(1).getAdsType(), "Kakao Ads");
    }

    @Test
    public void registerPointWithValidAmount() {
        Point mockPoint = Point.builder()
                .userId(1004)
                .adsType("Google Ads")
                .transactionType("적립")
                .amount(10)
                .build();

        given(pointRepository.save(any())).willReturn(mockPoint);

        Point point = pointService.registerPoint(Point.builder().adsType("Google Ads").amount(10).build());

        assertEquals("Google Ads", point.getAdsType());
        assertEquals(10, point.getAmount());
    }

    @Test
    public void registerPointWithInvalidAmount() {
        User user = User.builder().id(1004).totalPoints(0).build();

        given(userRepository.findById(1004)).willReturn(Optional.of(user));

        Point point = Point.builder()
                .userId(1004)
                .adsType("-")
                .transactionType("환급")
                .amount(-99999)
                .build();

        Exception exception = assertThrows(InsufficientBalanceException.class, () -> {
            pointService.registerPoint(point);
        });

        String expectedMessage = "Balance is insufficient for the requested transaction";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}