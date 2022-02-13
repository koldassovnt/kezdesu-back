package kz.sueta.clientservice.register;

import kz.sueta.clientservice.dto.ui.request.PhoneSmsRequest;
import kz.sueta.clientservice.dto.ui.request.TokenRefreshRequest;
import kz.sueta.clientservice.dto.ui.response.JwtResponse;
import kz.sueta.clientservice.dto.ui.response.TokenRefreshResponse;

public interface AuthenticationRegister {

    void postPhoneNumberForAuth(String phoneNumber);

    JwtResponse postSmsForAuth(PhoneSmsRequest phoneSmsRequest); //todo TEST

    TokenRefreshResponse refreshToken(TokenRefreshRequest request); //todo TEST
}
