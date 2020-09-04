package swmaestro.revivers.cashface.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swmaestro.revivers.cashface.domain.Point;
import swmaestro.revivers.cashface.domain.PointRepository;
import swmaestro.revivers.cashface.domain.User;
import swmaestro.revivers.cashface.domain.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PointService {

    private PointRepository pointRepository;

    private UserRepository userRepository;

    @Autowired
    public PointService(PointRepository pointRepository, UserRepository userRepository) {
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
    }

    public List<Point> getPointsByUserId(Integer userId) {
        return pointRepository.findAllByUserId(userId);
    }

    public Point registerPoint(Point resource) {

        if (resource.getAmount() < 0) {
            User user = userRepository.findById(resource.getUserId())
                    .orElseThrow(() -> new UserIdNotExistedException(resource.getUserId()));

            if (user.getTotalPoints() + resource.getAmount() < 0) {
                throw new InsufficientBalanceException(user.getTotalPoints(), resource.getAmount());
            }
        }

        Point point = Point.builder()
                .id(resource.getId())
                .userId(resource.getUserId())
                .adsType(resource.getAdsType())
                .transactionType(resource.getTransactionType())
                .amount(resource.getAmount())
                .date(resource.getDate())
                .build();

        return pointRepository.save(point);
    }
}
