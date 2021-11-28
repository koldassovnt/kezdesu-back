package kz.sueta.clientservice.register.impl;

import kz.sueta.clientservice.entity.SmsForAuth;
import kz.sueta.clientservice.in_service.model.SmsSendRequest;
import kz.sueta.clientservice.register.AuthenticationRegister;
import kz.sueta.clientservice.register.SmsRegister;
import kz.sueta.clientservice.repository.SmsForAuthDao;
import kz.sueta.clientservice.rest_response.PhoneNumberInvalidException;
import kz.sueta.clientservice.util.PhoneNumberUtil;
import kz.sueta.clientservice.util.RndUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class AuthenticationRegisterImpl implements AuthenticationRegister {

    @Autowired
    private SmsRegister smsRegister;

    @Autowired
    private SmsForAuthDao smsForAuthDao;

    @Override
    public void getPhoneNumberForAuth(String phoneNumber) {
        if (!PhoneNumberUtil.isValid(phoneNumber)) {
            throw new PhoneNumberInvalidException();
        }

        String code = RndUtil.getRndCodeForAuth();

        SmsSendRequest request = new SmsSendRequest(phoneNumber, code);
        smsRegister.sendSms(request);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SmsForAuth smsForAuth = new SmsForAuth(phoneNumber, code, timestamp, false);
        smsForAuthDao.save(smsForAuth);
    }
}
