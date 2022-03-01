package kz.sueta.adminservice.register.impl;

import kz.sueta.adminservice.dto.services.request.SaveEventRequest;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;
import kz.sueta.adminservice.register.EventRegister;
import kz.sueta.adminservice.service_messaging.EventServiceClient;
import org.springframework.stereotype.Component;

@Component
public class EventRegisterImpl implements EventRegister {

    private final EventServiceClient eventServiceClient;

    public EventRegisterImpl(EventServiceClient eventServiceClient) {
        this.eventServiceClient = eventServiceClient;
    }


    @Override
    public MessageResponse saveEvent(SaveEventRequest saveRequest) {
        return null;
    }
}
