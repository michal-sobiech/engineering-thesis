package pl.michal_sobiech.core.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException(String message, Exception cause) {
        super(message, cause);
    }

    public NotFoundException(Exception cause) {
        super(cause);
    }

}
