package kz.sueta.clientservice.exceptions.ui;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PhoneNumberInvalidException extends ResponseStatusException {

    public PhoneNumberInvalidException(HttpStatus status, String msg) {
        super(status,msg);
    }

    public PhoneNumberInvalidException() {
        super(HttpStatus.valueOf(400), "Z4AvAXiJ1S :: Не корректный мобильный номер!");
    }
}
