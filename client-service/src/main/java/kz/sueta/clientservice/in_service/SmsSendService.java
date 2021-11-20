package kz.sueta.clientservice.in_service;

import kz.sueta.clientservice.in_service.model.SmsSendRequest;
import kz.sueta.clientservice.in_service.model.SmsSendResponse;

public interface SmsSendService {

    SmsSendResponse sendSms(SmsSendRequest smsSendRequest);
}
