package swmaestro.revivers.cashface.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointRepository extends CrudRepository<Point, Integer> {

    List<Point> findAllByUserId(Integer userId);

}
