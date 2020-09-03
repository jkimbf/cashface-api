package swmaestro.revivers.cashface.application;

public class EmailExistedException extends RuntimeException {

    EmailExistedException(String email) {
        super("Email is already registered: " + email);
    }

}
