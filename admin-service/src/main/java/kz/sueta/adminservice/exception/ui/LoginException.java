package kz.sueta.adminservice.exception.ui;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoginException extends ResponseStatusException {

    public LoginException(HttpStatus status, String message) {
        super(status, message);
    }

    public LoginException() {
        super(HttpStatus.valueOf(400), "cpm8mG6lNF :: Error during login!");
    }
}
