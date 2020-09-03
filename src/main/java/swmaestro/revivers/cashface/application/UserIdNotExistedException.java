package swmaestro.revivers.cashface.application;

public class UserIdNotExistedException extends RuntimeException {

    UserIdNotExistedException(Integer userId) {
        super("This id is not registered or invalid: " + userId);
    }

}
