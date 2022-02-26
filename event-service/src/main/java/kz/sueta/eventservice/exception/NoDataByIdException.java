package kz.sueta.eventservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoDataByIdException extends ResponseStatusException {

    public NoDataByIdException(HttpStatus status, String msg) {
        super(status, msg);
    }

    public NoDataByIdException(String msg) {
        super(HttpStatus.valueOf(400), msg);
    }

    public NoDataByIdException() {
        super(HttpStatus.valueOf(400), "7eYZfElbpW :: No date returned by Id");
    }
}
