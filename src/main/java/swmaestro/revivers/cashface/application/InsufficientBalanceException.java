package swmaestro.revivers.cashface.application;

public class InsufficientBalanceException extends RuntimeException {

    InsufficientBalanceException(Integer balance, Integer request) {
        super("Balance is insufficient for the requested transaction");
    }

}
