package kz.sueta.adminservice.register;

import kz.sueta.adminservice.dto.services.request.ClientBlockRequest;
import kz.sueta.adminservice.dto.services.request.ClientListFilter;
import kz.sueta.adminservice.dto.services.response.ClientListResponse;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;

public interface ClientRegister {

    MessageResponse blockClient(ClientBlockRequest request);

    ClientListResponse listClient(ClientListFilter filter);
}
