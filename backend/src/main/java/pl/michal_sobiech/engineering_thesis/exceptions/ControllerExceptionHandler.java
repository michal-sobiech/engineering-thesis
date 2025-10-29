package pl.michal_sobiech.engineering_thesis.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.michal_sobiech.engineering_thesis.exceptions.exceptions.ConflictException;
import pl.michal_sobiech.engineering_thesis.exceptions.exceptions.ForbiddenException;
import pl.michal_sobiech.engineering_thesis.exceptions.exceptions.UnauthorizedException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleForbidden() {
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleUnauthorized() {
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleConflict() {
    }

}
