package kz.sueta.clientservice.rest_response;

import org.springframework.web.client.RestClientException;

public class PhoneNumberInvalidException extends RestClientException {

    public PhoneNumberInvalidException(String msg) {
        super(msg);
    }

    public PhoneNumberInvalidException() {
        super("Не корректный мобильный номер!");
    }
}
