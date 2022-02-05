package kz.sueta.clientservice.register;

import kz.sueta.clientservice.dto.ui.PhoneSmsRequest;

public interface AuthenticationRegister {

    void postPhoneNumberForAuth(String phoneNumber);

    void postSmsForAuth(PhoneSmsRequest phoneSmsRequest);
}
