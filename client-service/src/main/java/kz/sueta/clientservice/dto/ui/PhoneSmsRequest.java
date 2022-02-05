package kz.sueta.clientservice.dto.ui;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhoneSmsRequest {

    public String phoneNumber;
    public String smsCode;
}
