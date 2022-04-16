package kz.sueta.clientservice.exception.ui;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RestException extends ResponseStatusException {

    public RestException(HttpStatus status, String message) {
        super(status, message);
    }

    public RestException() {
        super(HttpStatus.valueOf(400), "OqO50xzl0T :: Bad Request");
    }

    public RestException(String message) {
        super(HttpStatus.valueOf(400), message);
    }
}
