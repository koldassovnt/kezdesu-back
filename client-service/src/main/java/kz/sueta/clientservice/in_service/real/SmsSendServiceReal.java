package kz.sueta.clientservice.in_service.real;

import kz.sueta.clientservice.in_service.SmsSendService;
import kz.sueta.clientservice.in_service.model.SmsSendRequest;
import kz.sueta.clientservice.in_service.model.SmsSendResponse;
import kz.sueta.clientservice.util.RndUtil;

public class SmsSendServiceReal implements SmsSendService {

    @Override
    public SmsSendResponse sendSms(SmsSendRequest smsSendRequest) {
        String code = RndUtil.getRndCodeForAuth();
        return SmsSendResponse.of("REAL_SERVICE", code);
    }
}
