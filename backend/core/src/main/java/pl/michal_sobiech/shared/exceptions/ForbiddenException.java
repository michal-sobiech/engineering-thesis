package pl.michal_sobiech.shared.exceptions;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
    }

    public ForbiddenException(String message, Exception cause) {
        super(message, cause);
    }

    public ForbiddenException(Exception cause) {
        super(cause);
    }

}
