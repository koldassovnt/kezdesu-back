package kz.sueta.clientservice.in_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsSendRequest {
    public String phoneNumber;
    public String code;
}
