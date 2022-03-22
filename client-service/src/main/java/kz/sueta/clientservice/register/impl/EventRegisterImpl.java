package kz.sueta.clientservice.register.impl;

import kz.sueta.clientservice.dto.services.response.EventListResponse;
import kz.sueta.clientservice.register.EventRegister;
import kz.sueta.clientservice.service_messaging.EventServiceClient;
import org.springframework.stereotype.Component;

@Component
public class EventRegisterImpl implements EventRegister {

    private final EventServiceClient eventServiceClient;

    public EventRegisterImpl(EventServiceClient eventServiceClient) {
        this.eventServiceClient = eventServiceClient;
    }

    @Override
    public EventListResponse listEvents() {
        return eventServiceClient.listEvent(1000,
                0,
                null,
                null,
                null,
                true,
                false);
    }
}
