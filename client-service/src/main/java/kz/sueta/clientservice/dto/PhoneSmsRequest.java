package kz.sueta.clientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhoneSmsRequest {

    private String phoneNumber;
    private String smsCode;
}
