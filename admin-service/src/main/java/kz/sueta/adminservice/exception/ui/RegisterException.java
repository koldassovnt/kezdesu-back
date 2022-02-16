package kz.sueta.adminservice.exception.ui;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RegisterException extends ResponseStatusException {

    public RegisterException(HttpStatus status, String message) {
        super(status, message);
    }

    public RegisterException() {
        super(HttpStatus.valueOf(400), "x6Zyoa3KhP :: Error during registration!");
    }
}
