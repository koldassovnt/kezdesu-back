package kz.sueta.clientservice.test.bean;

import kz.sueta.clientservice.in_service.SmsSendService;
import kz.sueta.clientservice.in_service.model.SmsSendRequest;
import kz.sueta.clientservice.in_service.model.SmsSendResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class SmsSendServiceTest implements SmsSendService {

    @Override
    public SmsSendResponse sendSms(SmsSendRequest smsSendRequest) {
        return SmsSendResponse.of("OK", "0000");
    }
}
