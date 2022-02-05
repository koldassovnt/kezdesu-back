package kz.sueta.clientservice.register;

import kz.sueta.clientservice.dto.PhoneSmsRequest;

public interface AuthenticationRegister {

    void postPhoneNumberForAuth(String phoneNumber);

    void postSmsForAuth(PhoneSmsRequest phoneSmsRequest);
}
