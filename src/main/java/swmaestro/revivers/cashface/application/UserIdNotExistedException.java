package swmaestro.revivers.cashface.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserIdNotExistedException extends RuntimeException {

    UserIdNotExistedException(Integer userId) {
        super("This id is not registered or invalid: " + userId);
    }

}
