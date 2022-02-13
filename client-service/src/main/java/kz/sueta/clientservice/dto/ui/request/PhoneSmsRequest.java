package kz.sueta.clientservice.dto.ui.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhoneSmsRequest {
    public String phoneNumber;
    public String smsCode;
}
