package pl.michal_sobiech.engineering_thesis.exceptions.exceptions;

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
