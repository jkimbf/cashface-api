package swmaestro.revivers.cashface.application;

public class InsufficientBalanceException extends RuntimeException {

    InsufficientBalanceException() {
        super("Balance is insufficient for the requested transaction");
    }

}
