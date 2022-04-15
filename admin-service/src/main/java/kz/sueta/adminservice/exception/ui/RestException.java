package kz.sueta.adminservice.exception.ui;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RestException extends ResponseStatusException {

    public RestException(HttpStatus status, String message) {
        super(status, message);
    }

    public RestException() {
        super(HttpStatus.valueOf(400), "x6Zyoa3KhP :: Bad Request");
    }

    public RestException(String message) {
        super(HttpStatus.valueOf(400), message);
    }
}
