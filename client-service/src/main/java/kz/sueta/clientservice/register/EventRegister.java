package kz.sueta.clientservice.register;

import kz.sueta.clientservice.dto.services.request.DetailRequest;
import kz.sueta.clientservice.dto.services.request.EditEventRequest;
import kz.sueta.clientservice.dto.services.request.EventListFilter;
import kz.sueta.clientservice.dto.services.request.SaveEventRequest;
import kz.sueta.clientservice.dto.services.response.EventListResponse;
import kz.sueta.clientservice.dto.services.response.EventResponse;
import kz.sueta.clientservice.dto.ui.response.MessageResponse;

public interface EventRegister {

    EventListResponse listEvents(EventListFilter filter);

    EventResponse detailEvent(DetailRequest request);

    MessageResponse saveEvent(SaveEventRequest saveRequest, String clientId);

    MessageResponse editEvent(EditEventRequest editEventRequest, String clientId);
}
