package pl.michal_sobiech.engineering_thesis.exceptions.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException() {
    }

    public ConflictException(String message, Exception cause) {
        super(message, cause);
    }

    public ConflictException(Exception cause) {
        super(cause);
    }

}
