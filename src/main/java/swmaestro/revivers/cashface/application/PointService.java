package swmaestro.revivers.cashface.application;

import org.springframework.stereotype.Service;
import swmaestro.revivers.cashface.domain.Point;
import swmaestro.revivers.cashface.domain.PointRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PointService {

    private PointRepository pointRepository;

    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public List<Point> getPointsByUserId(Integer userId) {
        return pointRepository.findAllByUserId(userId);
    }

    public Point registerPoint(Point resource) {

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
