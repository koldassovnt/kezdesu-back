package kz.sueta.clientservice.register.impl;

import com.google.common.base.Strings;
import kz.sueta.clientservice.dto.services.request.DetailRequest;
import kz.sueta.clientservice.dto.services.request.EditEventRequest;
import kz.sueta.clientservice.dto.services.request.EventListFilter;
import kz.sueta.clientservice.dto.services.request.SaveEventRequest;
import kz.sueta.clientservice.dto.services.response.EventListResponse;
import kz.sueta.clientservice.dto.services.response.EventResponse;
import kz.sueta.clientservice.dto.ui.response.MessageResponse;
import kz.sueta.clientservice.exception.ui.RestException;
import kz.sueta.clientservice.register.EventRegister;
import kz.sueta.clientservice.service_messaging.EventServiceClient;
import kz.sueta.clientservice.util.ServiceFallbackStatic;
import org.springframework.stereotype.Component;

@Component
public class EventRegisterImpl implements EventRegister {

    private final EventServiceClient eventServiceClient;

    public EventRegisterImpl(EventServiceClient eventServiceClient) {
        this.eventServiceClient = eventServiceClient;
    }

    @Override
    public EventListResponse listEvents(EventListFilter filter) {
        return eventServiceClient.listEvent(
                filter.limit,
                filter.offset,
                filter.categoryId,
                filter.labelSearch,
                filter.clientId,
                filter.actual,
                filter.blocked);
    }

    @Override
    public EventResponse detailEvent(DetailRequest request) {
        EventResponse eventResponse = eventServiceClient.detailEvent(request.id);

        if (eventResponse == null) {
            throw new RuntimeException("I66IvYen3h :: event service calling returned error for DETAIL");
        }

        return eventResponse;
    }

    @Override
    public MessageResponse saveEvent(SaveEventRequest saveRequest, String clientId) {
        if (Strings.isNullOrEmpty(clientId)) {
            throw new RestException("FjGDvnJ8U0 :: Authorization is incorrect, please login again!");
        }

        saveRequest.creatorId = clientId;
        MessageResponse response = eventServiceClient.saveEvent(saveRequest);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(response.message)) {
            throw new RuntimeException("pprONO71i1 :: event service calling returned error for SAVE");
        }

        return response;
    }

    @Override
    public MessageResponse editEvent(EditEventRequest editEventRequest, String clientId) {
        if (Strings.isNullOrEmpty(clientId)) {
            throw new RestException("8T07yOfO70 :: Authorization is incorrect, please login again!");
        }

        editEventRequest.clientId = clientId;
        MessageResponse response = eventServiceClient.editEvent(editEventRequest);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(response.message)) {
            throw new RuntimeException("SXJ29LEIPc :: event service calling returned error for EDIT");
        }

        return response;
    }


}
