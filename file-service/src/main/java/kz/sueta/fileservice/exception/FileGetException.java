package kz.sueta.fileservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FileGetException extends ResponseStatusException {

    public FileGetException(HttpStatus status, String message) {
        super(status, message);
    }

    public FileGetException() {
        super(HttpStatus.valueOf(400), "g77MWylw2Q :: error during file getting!");
    }

    public FileGetException(String message) {
        super(HttpStatus.valueOf(400), message);
    }
}
