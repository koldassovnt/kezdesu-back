package kz.sueta.clientservice.register.impl;

import kz.sueta.clientservice.dto.ui.request.PhoneSmsRequest;
import kz.sueta.clientservice.entity.SmsForAuth;
import kz.sueta.clientservice.in_service.SmsSendService;
import kz.sueta.clientservice.in_service.model.SmsSendRequest;
import kz.sueta.clientservice.in_service.model.SmsSendResponse;
import kz.sueta.clientservice.register.SmsRegister;
import kz.sueta.clientservice.repository.SmsForAuthDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class SmsRegisterImpl implements SmsRegister {

    private final SmsSendService smsSendService;
    private final SmsForAuthDao smsForAuthDao;

    @Autowired
    public SmsRegisterImpl(SmsSendService smsSendService, SmsForAuthDao smsForAuthDao) {
        this.smsSendService = smsSendService;
        this.smsForAuthDao = smsForAuthDao;
    }

    @Override
    public SmsSendResponse sendSms(SmsSendRequest smsSendRequest) {
        return smsSendService.sendSms(smsSendRequest);
    }

    @Override
    public boolean validateSmsCode(SmsForAuth smsForAuth, PhoneSmsRequest phoneSmsRequest) {
        return Objects.equals(smsForAuth.code, phoneSmsRequest.smsCode) &&
                !smsForAuth.isValidated &&
                smsForAuth.expiredAt.after(new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public Optional<SmsForAuth> getSmsForAuth(PhoneSmsRequest phoneSmsRequest) {
        return smsForAuthDao.findById(phoneSmsRequest.getPhoneNumber());
    }

    @Override
    public void saveSmsForAuth(String phoneNumber, String code) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2));
        SmsForAuth smsForAuth = new SmsForAuth(phoneNumber, code, timestamp, false);
        smsForAuthDao.save(smsForAuth);
    }

    @Override
    public void save(SmsForAuth smsForAuth) {
        smsForAuthDao.save(smsForAuth);
    }
}
