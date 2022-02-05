package kz.sueta.clientservice.in_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsSendResponse {

    public String responseText;

    public static SmsSendResponse of(String responseText) {
        SmsSendResponse response = new SmsSendResponse();
        response.responseText = responseText;
        return response;
    }
}
