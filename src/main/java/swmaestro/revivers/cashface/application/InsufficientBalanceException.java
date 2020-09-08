package swmaestro.revivers.cashface.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientBalanceException extends RuntimeException {

    InsufficientBalanceException() {
        super("Balance is insufficient for the requested transaction");
    }

}
