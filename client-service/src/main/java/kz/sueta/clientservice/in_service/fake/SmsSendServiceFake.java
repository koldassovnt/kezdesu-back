package kz.sueta.clientservice.in_service.fake;

import kz.sueta.clientservice.in_service.SmsSendService;
import kz.sueta.clientservice.in_service.model.SmsSendRequest;
import kz.sueta.clientservice.in_service.model.SmsSendResponse;

public class SmsSendServiceFake implements SmsSendService {

    @Override
    public SmsSendResponse sendSms(SmsSendRequest smsSendRequest) {
        return SmsSendResponse.of("FAKE_SERVICE");
    }
}
