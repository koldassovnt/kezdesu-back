package kz.sueta.fileservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FileSaveException extends ResponseStatusException {

    public FileSaveException(HttpStatus status, String message) {
        super(status, message);
    }

    public FileSaveException() {
        super(HttpStatus.valueOf(400), "n9uaFWhTVH :: error during file saving!");
    }

    public FileSaveException(String message) {
        super(HttpStatus.valueOf(400), message);
    }
}
