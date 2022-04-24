package kz.sueta.adminservice.register;

import kz.sueta.adminservice.dto.services.request.DetailRequest;
import kz.sueta.adminservice.dto.services.request.EditEventRequest;
import kz.sueta.adminservice.dto.services.request.EventListFilter;
import kz.sueta.adminservice.dto.services.request.SaveEventRequest;
import kz.sueta.adminservice.dto.ui.response.AdminEventListResponse;
import kz.sueta.adminservice.dto.ui.response.AdminEventResponse;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;

public interface EventRegister {

    MessageResponse saveEvent(SaveEventRequest saveRequest, String email);

    MessageResponse editEvent(EditEventRequest editEventRequest);

    MessageResponse blockEvent(DetailRequest eventDetailRequest);

    MessageResponse deleteEvent(DetailRequest eventDetailRequest);

    AdminEventListResponse listEvent(EventListFilter filter);

    AdminEventResponse detailEvent(DetailRequest eventDetailRequest);
}
