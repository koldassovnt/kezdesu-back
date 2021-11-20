package kz.sueta.clientservice.in_service.model;

import lombok.Data;

@Data
public class SmsSendResponse {

    private String responseText;

    public static SmsSendResponse of(String responseText) {
        SmsSendResponse response = new SmsSendResponse();
        response.responseText = responseText;
        return response;
    }
}
