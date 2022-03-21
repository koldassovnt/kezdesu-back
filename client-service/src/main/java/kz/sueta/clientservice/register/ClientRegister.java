package kz.sueta.clientservice.register;

import kz.sueta.clientservice.dto.services.request.ClientBlockRequest;
import kz.sueta.clientservice.dto.services.request.ClientListFilter;
import kz.sueta.clientservice.dto.services.response.ClientListResponse;
import kz.sueta.clientservice.dto.ui.request.PhoneSmsRequest;
import kz.sueta.clientservice.entity.Client;

public interface ClientRegister {

    Client saveClient(PhoneSmsRequest phoneSmsRequest);

    Client getClient(PhoneSmsRequest phoneSmsRequest);

    ClientListResponse listClient(ClientListFilter filter);

    void blockClient(ClientBlockRequest request);
}
