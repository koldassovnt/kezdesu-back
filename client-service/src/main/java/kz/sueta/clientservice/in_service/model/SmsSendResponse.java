package kz.sueta.clientservice.in_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsSendResponse {

    public String responseText;
    public String code;

    public static SmsSendResponse of(String responseText, String code) {
        return new SmsSendResponse(responseText, code);
    }
}
