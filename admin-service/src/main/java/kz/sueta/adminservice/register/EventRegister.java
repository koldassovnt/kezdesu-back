package kz.sueta.adminservice.register;

import kz.sueta.adminservice.dto.services.request.DetailRequest;
import kz.sueta.adminservice.dto.services.request.EditEventRequest;
import kz.sueta.adminservice.dto.services.request.EventListFilter;
import kz.sueta.adminservice.dto.services.request.SaveEventRequest;
import kz.sueta.adminservice.dto.services.response.EventListResponse;
import kz.sueta.adminservice.dto.services.response.EventResponse;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;

public interface EventRegister {

    MessageResponse saveEvent(SaveEventRequest saveRequest);

    MessageResponse editEvent(EditEventRequest editEventRequest);

    MessageResponse blockEvent(DetailRequest eventDetailRequest);

    MessageResponse deleteEvent(DetailRequest eventDetailRequest);

    EventListResponse listEvent(EventListFilter filter);

    EventResponse detailEvent(DetailRequest eventDetailRequest);
}
