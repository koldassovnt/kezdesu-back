package kz.sueta.clientservice.register.impl;

import kz.sueta.clientservice.dto.ui.PhoneSmsRequest;
import kz.sueta.clientservice.entity.SmsForAuth;
import kz.sueta.clientservice.in_service.model.SmsSendRequest;
import kz.sueta.clientservice.register.AuthenticationRegister;
import kz.sueta.clientservice.register.SmsRegister;
import kz.sueta.clientservice.repository.SmsForAuthDao;
import kz.sueta.clientservice.rest_exceptions.PhoneNumberInvalidException;
import kz.sueta.clientservice.rest_exceptions.SmsCodeInvalidException;
import kz.sueta.clientservice.util.PhoneNumberUtil;
import kz.sueta.clientservice.util.RndUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class AuthenticationRegisterImpl implements AuthenticationRegister {

    @Autowired
    private SmsRegister smsRegister;

    @Autowired
    private SmsForAuthDao smsForAuthDao;

    @Override
    public void postPhoneNumberForAuth(String phoneNumber) {
        if (!PhoneNumberUtil.isValid(phoneNumber)) {
            throw new PhoneNumberInvalidException();
        }

        String code = RndUtil.getRndCodeForAuth();

        SmsSendRequest request = new SmsSendRequest(phoneNumber, code);
        smsRegister.sendSms(request);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2));
        SmsForAuth smsForAuth = new SmsForAuth(phoneNumber, code, timestamp, false);
        smsForAuthDao.save(smsForAuth);
    }

    @Override
    public void postSmsForAuth(PhoneSmsRequest phoneSmsRequest) {

        if (phoneSmsRequest == null) {
            throw new SmsCodeInvalidException(HttpStatus.valueOf(400), "Пришел пустой request body!");
        }

        if (!Strings.isNotEmpty(phoneSmsRequest.getPhoneNumber()) ||
                !Strings.isNotEmpty(phoneSmsRequest.getSmsCode())) {
            throw new SmsCodeInvalidException(HttpStatus.valueOf(400), "Код или телефонный номер пришел как пустое значение!");
        }

        Optional<SmsForAuth> smsForAuth = smsForAuthDao.findById(phoneSmsRequest.getPhoneNumber());

        if (Objects.equals(smsForAuth.get().getCode(), phoneSmsRequest.getSmsCode()) &&
                !smsForAuth.get().getIsValidated() &&
                smsForAuth.get().getExpiredAt().before(new Timestamp(System.currentTimeMillis()))) {

            smsForAuth.get().setIsValidated(true);
            smsForAuthDao.save(smsForAuth.get());


            //todo save user and return tokens

        } else {
            throw new SmsCodeInvalidException();
        }

    }
}
