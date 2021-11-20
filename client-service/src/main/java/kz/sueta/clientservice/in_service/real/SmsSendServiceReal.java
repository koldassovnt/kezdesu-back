package kz.sueta.clientservice.in_service.real;

import kz.sueta.clientservice.in_service.SmsSendService;
import kz.sueta.clientservice.in_service.model.SmsSendRequest;
import kz.sueta.clientservice.in_service.model.SmsSendResponse;

public class SmsSendServiceReal implements SmsSendService {

    @Override
    public SmsSendResponse sendSms(SmsSendRequest smsSendRequest) {
        return SmsSendResponse.of("REAL_SERVICE");
    }
}
