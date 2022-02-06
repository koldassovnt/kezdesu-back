package kz.sueta.clientservice.exceptions.ui;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SmsCodeInvalidException extends ResponseStatusException {

    public SmsCodeInvalidException(HttpStatus status, String msg) {
        super(status,msg);
    }

    public SmsCodeInvalidException() {
        super(HttpStatus.valueOf(400), "Ошибка при верификации при помощи смс кода!");
    }
}
