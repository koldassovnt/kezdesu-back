package kz.sueta.clientservice.register;

import kz.sueta.clientservice.dto.ui.request.PhoneSmsRequest;
import kz.sueta.clientservice.entity.SmsForAuth;
import kz.sueta.clientservice.in_service.model.SmsSendRequest;
import kz.sueta.clientservice.in_service.model.SmsSendResponse;

import java.util.Optional;

public interface SmsRegister {

    SmsSendResponse sendSms(SmsSendRequest smsSendRequest);

    boolean validateSmsCode(SmsForAuth smsForAuth, PhoneSmsRequest phoneSmsRequest);

    Optional<SmsForAuth> getSmsForAuth(PhoneSmsRequest phoneSmsRequest);

    void saveSmsForAuth(String phoneNumber, String code);

    void save(SmsForAuth smsForAuth);
}
