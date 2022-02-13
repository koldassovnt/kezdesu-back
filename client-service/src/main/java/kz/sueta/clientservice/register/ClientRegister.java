package kz.sueta.clientservice.register;

import kz.sueta.clientservice.dto.ui.request.PhoneSmsRequest;
import kz.sueta.clientservice.entity.Client;

public interface ClientRegister {

    Client saveClient(PhoneSmsRequest phoneSmsRequest); //todo TEST

    Client getClient(PhoneSmsRequest phoneSmsRequest); //todo TEST
}
