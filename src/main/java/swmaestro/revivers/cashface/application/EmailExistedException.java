package swmaestro.revivers.cashface.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailExistedException extends RuntimeException {

    EmailExistedException(String email) {
        super("Email is already registered: " + email);
    }

}
