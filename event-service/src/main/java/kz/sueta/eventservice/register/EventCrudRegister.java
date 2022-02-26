package kz.sueta.eventservice.register;

import kz.sueta.eventservice.dto.request.DetailRequest;
import kz.sueta.eventservice.dto.request.EditEventRequest;
import kz.sueta.eventservice.dto.request.EventListFilter;
import kz.sueta.eventservice.dto.request.SaveEventRequest;
import kz.sueta.eventservice.dto.response.EventListResponse;
import kz.sueta.eventservice.dto.response.EventResponse;

public interface EventCrudRegister {

    void saveEvent(SaveEventRequest saveRequest);

    void editEvent(EditEventRequest editEventRequest);

    void blockEvent(DetailRequest eventDetailRequest);

    void deleteEvent(DetailRequest eventDetailRequest);

    EventListResponse eventList(EventListFilter filter);

    EventResponse eventDetail(DetailRequest eventDetailRequest);
}
