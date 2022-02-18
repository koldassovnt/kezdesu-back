package kz.sueta.clientservice.dto.ui.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@NotNull(message = "Request body cannot be null")
public class PhoneSmsRequest {
    public String phoneNumber;
    public String smsCode;
}
