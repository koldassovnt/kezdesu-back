package kz.sueta.clientservice.register.impl;

import kz.sueta.clientservice.bean_security.jwt.JwtUtils;
import kz.sueta.clientservice.bean_security.services.CustomUserDetails;
import kz.sueta.clientservice.bean_security.services.RefreshTokenService;
import kz.sueta.clientservice.dto.ui.request.PhoneSmsRequest;
import kz.sueta.clientservice.dto.ui.request.TokenRefreshRequest;
import kz.sueta.clientservice.dto.ui.response.JwtResponse;
import kz.sueta.clientservice.dto.ui.response.TokenRefreshResponse;
import kz.sueta.clientservice.entity.Client;
import kz.sueta.clientservice.entity.RefreshToken;
import kz.sueta.clientservice.entity.SmsForAuth;
import kz.sueta.clientservice.exception.ui.PhoneNumberInvalidException;
import kz.sueta.clientservice.exception.ui.SmsCodeInvalidException;
import kz.sueta.clientservice.exception.ui.TokenRefreshException;
import kz.sueta.clientservice.in_service.model.SmsSendRequest;
import kz.sueta.clientservice.register.AuthenticationRegister;
import kz.sueta.clientservice.register.ClientRegister;
import kz.sueta.clientservice.register.SmsRegister;
import kz.sueta.clientservice.util.PhoneNumberUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationRegisterImpl implements AuthenticationRegister {

    private final SmsRegister smsRegister;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final ClientRegister clientRegister;

    @Autowired
    public AuthenticationRegisterImpl(SmsRegister smsRegister,
                                      AuthenticationManager authenticationManager,
                                      JwtUtils jwtUtils,
                                      RefreshTokenService refreshTokenService,
                                      ClientRegister clientRegister) {
        this.smsRegister = smsRegister;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.clientRegister = clientRegister;
    }

    @Override
    public void postPhoneNumberForAuth(String phoneNumber) {
        if (!PhoneNumberUtil.isValid(phoneNumber)) {
            throw new PhoneNumberInvalidException();
        }

        SmsSendRequest request = new SmsSendRequest(phoneNumber);
        String code = smsRegister.sendSms(request).code;

        smsRegister.saveSmsForAuth(phoneNumber, code);
    }

    @Override
    public JwtResponse postSmsForAuth(PhoneSmsRequest phoneSmsRequest) {

        if (phoneSmsRequest == null) {
            throw new SmsCodeInvalidException(HttpStatus.valueOf(400),
                    "1UdI5ZPiyA :: Пришел пустой request body!");
        }

        if (!Strings.isNotEmpty(phoneSmsRequest.getPhoneNumber()) ||
                !Strings.isNotEmpty(phoneSmsRequest.getSmsCode())) {
            throw new SmsCodeInvalidException(HttpStatus.valueOf(400),
                    "uUlfcCmw9j :: Код или телефонный номер пришел как пустое значение!");
        }

        Optional<SmsForAuth> smsForAuth = smsRegister.getSmsForAuth(phoneSmsRequest);

        if (smsForAuth.isEmpty()) {
            throw new SmsCodeInvalidException(HttpStatus.valueOf(400),
                    "CXSLkkyC0F :: Данные по телефонному номеру не существуют в БД!");
        }

        if (smsRegister.validateSmsCode(smsForAuth.get(), phoneSmsRequest)) {

            smsForAuth.get().setIsValidated(true);
            smsRegister.saveSmsForAuth(smsForAuth.get());

            Client existedClient = clientRegister.getClient(phoneSmsRequest);

            if (existedClient != null) {
                return getJwtResponse(existedClient);
            }

            Client client = clientRegister.saveClient(phoneSmsRequest);

            return getJwtResponse(client);
        } else {
            throw new SmsCodeInvalidException();
        }
    }

    @Override
    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getClientId)
                .map(clientId -> {
                    String token = jwtUtils.generateTokenFromClientId(clientId);
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "oH0N1lcn3K :: Refresh token is not in database!")).getBody();
    }

    private Authentication getAuthentication(String clientId, String password) {
        return authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (clientId, password));
    }

    private JwtResponse getJwtResponse(Client client) {

        Authentication authentication = getAuthentication(client.clientId, getPlainPassword(client));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails clientDetails = (CustomUserDetails) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(clientDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(clientDetails.getUsername());

        return JwtResponse.of(jwt, refreshToken.token, clientDetails.getUsername(), client.phone);
    }

    private String getPlainPassword(Client client) {
        return client.phone + client.clientId;
    }
}
