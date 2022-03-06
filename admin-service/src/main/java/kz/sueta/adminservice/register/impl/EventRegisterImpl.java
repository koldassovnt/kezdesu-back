package kz.sueta.adminservice.register.impl;

import kz.sueta.adminservice.dto.services.request.DetailRequest;
import kz.sueta.adminservice.dto.services.request.EditEventRequest;
import kz.sueta.adminservice.dto.services.request.EventListFilter;
import kz.sueta.adminservice.dto.services.request.SaveEventRequest;
import kz.sueta.adminservice.dto.services.response.EventListResponse;
import kz.sueta.adminservice.dto.services.response.EventResponse;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;
import kz.sueta.adminservice.register.EventRegister;
import kz.sueta.adminservice.service_messaging.EventServiceClient;
import kz.sueta.adminservice.util.ServiceFallbackStatic;
import org.springframework.stereotype.Component;

@Component
public class EventRegisterImpl implements EventRegister {

    private final EventServiceClient eventServiceClient;

    public EventRegisterImpl(EventServiceClient eventServiceClient) {
        this.eventServiceClient = eventServiceClient;
    }


    @Override
    public MessageResponse saveEvent(SaveEventRequest saveRequest) {
        MessageResponse messageResponse = eventServiceClient.saveEvent(saveRequest);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(messageResponse.message)) {
            throw new RuntimeException("ucz9l272zk :: event service calling returned error for SAVE");
        }

        return messageResponse;
    }

    @Override
    public MessageResponse editEvent(EditEventRequest editEventRequest) {
        MessageResponse messageResponse = eventServiceClient.editEvent(editEventRequest);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(messageResponse.message)) {
            throw new RuntimeException("e93d55qG86 :: event service calling returned error for EDIT");
        }

        return messageResponse;
    }

    @Override
    public MessageResponse blockEvent(DetailRequest eventDetailRequest) {
        MessageResponse messageResponse = eventServiceClient.blockEvent(eventDetailRequest);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(messageResponse.message)) {
            throw new RuntimeException("82n5jH3NM6 :: event service calling returned error for BLOCK");
        }

        return messageResponse;
    }

    @Override
    public MessageResponse deleteEvent(DetailRequest eventDetailRequest) {
        MessageResponse messageResponse = eventServiceClient.deleteEvent(eventDetailRequest);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(messageResponse.message)) {
            throw new RuntimeException("UJ2f5qa6pt :: event service calling returned error for DELETE");
        }

        return messageResponse;
    }

    @Override
    public EventListResponse listEvent(EventListFilter filter) {
        return eventServiceClient.listEvent(filter.limit, filter.offset, filter.categoryId,
                filter.labelSearch, filter.clientId, filter.actual, filter.blocked);
    }

    @Override
    public EventResponse detailEvent(DetailRequest eventDetailRequest) {
        EventResponse eventResponse = eventServiceClient.detailEvent(eventDetailRequest.id);

        if (eventResponse == null) {
            throw new RuntimeException("8m2VlOC3gT :: event service calling returned error for DETAIL");
        }

        return eventResponse;
    }
}
