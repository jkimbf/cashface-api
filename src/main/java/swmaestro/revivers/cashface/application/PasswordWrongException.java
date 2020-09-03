package swmaestro.revivers.cashface.application;

public class PasswordWrongException extends RuntimeException {

    PasswordWrongException() {
        super("Password is wrong");
    }

}
