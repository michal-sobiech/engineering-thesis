package pl.michal_sobiech.core.exceptions;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
    }

    public UnauthorizedException(String message, Exception cause) {
        super(message, cause);
    }

    public UnauthorizedException(Exception cause) {
        super(cause);
    }

}
