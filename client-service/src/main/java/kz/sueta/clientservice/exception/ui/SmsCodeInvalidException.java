package kz.sueta.clientservice.exception.ui;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SmsCodeInvalidException extends ResponseStatusException {

    public SmsCodeInvalidException(HttpStatus status, String msg) {
        super(status,msg);
    }

    public SmsCodeInvalidException() {
        super(HttpStatus.valueOf(400), "u6N6q48npW :: Ошибка при верификации при помощи смс кода!");
    }
}
