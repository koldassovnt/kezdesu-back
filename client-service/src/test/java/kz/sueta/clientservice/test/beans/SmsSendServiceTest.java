package kz.sueta.clientservice.test.beans;

import kz.sueta.clientservice.in_service.SmsSendService;
import kz.sueta.clientservice.in_service.model.SmsSendRequest;
import kz.sueta.clientservice.in_service.model.SmsSendResponse;

public class SmsSendServiceTest implements SmsSendService {

    @Override
    public SmsSendResponse sendSms(SmsSendRequest smsSendRequest) {
        return SmsSendResponse.of("OK");
    }
}
