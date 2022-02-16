package kz.sueta.clientservice.exception.ui;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PhoneNumberInvalidException extends ResponseStatusException {

    public PhoneNumberInvalidException(HttpStatus status, String msg) {
        super(status,msg);
    }

    public PhoneNumberInvalidException() {
        super(HttpStatus.valueOf(400), "Z4AvAXiJ1S :: Phone number is not appropriate!");
    }
}
