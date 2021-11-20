package kz.sueta.clientservice.register;

import kz.sueta.clientservice.in_service.model.SmsSendRequest;
import kz.sueta.clientservice.in_service.model.SmsSendResponse;

public interface SmsRegister {

    SmsSendResponse sendSms(SmsSendRequest smsSendRequest);
}
