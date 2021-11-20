package kz.sueta.clientservice.register.impl;

import kz.sueta.clientservice.in_service.SmsSendService;
import kz.sueta.clientservice.in_service.model.SmsSendRequest;
import kz.sueta.clientservice.in_service.model.SmsSendResponse;
import kz.sueta.clientservice.register.SmsRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmsRegisterImpl implements SmsRegister {

    @Autowired
    private SmsSendService smsSendService;

    @Override
    public SmsSendResponse sendSms(SmsSendRequest smsSendRequest) {
        return smsSendService.sendSms(smsSendRequest);
    }
}
