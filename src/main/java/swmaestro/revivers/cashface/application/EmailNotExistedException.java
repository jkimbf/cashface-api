package swmaestro.revivers.cashface.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailNotExistedException extends RuntimeException {
    EmailNotExistedException(String email) {
        super("Email is not registered: " + email);
    }
}
