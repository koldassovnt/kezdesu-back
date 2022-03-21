package kz.sueta.adminservice.register.impl;

import kz.sueta.adminservice.dto.services.request.ClientBlockRequest;
import kz.sueta.adminservice.dto.services.request.ClientListFilter;
import kz.sueta.adminservice.dto.services.response.ClientListResponse;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;
import kz.sueta.adminservice.register.ClientRegister;
import kz.sueta.adminservice.service_messaging.ClientServiceClient;
import kz.sueta.adminservice.util.ServiceFallbackStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientRegisterImpl implements ClientRegister {

    private final ClientServiceClient serviceClient;

    @Autowired
    public ClientRegisterImpl(ClientServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    @Override
    public MessageResponse blockClient(ClientBlockRequest request) {
        MessageResponse messageResponse = serviceClient.blockClient(request);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(messageResponse.message)) {
            throw new RuntimeException("hRDdJyf2cw :: client service calling returned error for BLOCK");
        }

        return messageResponse;
    }

    @Override
    public ClientListResponse listClient(ClientListFilter filter) {
        return serviceClient.listClient(filter.limit, filter.offset, filter.actual, filter.blocked);
    }
}
