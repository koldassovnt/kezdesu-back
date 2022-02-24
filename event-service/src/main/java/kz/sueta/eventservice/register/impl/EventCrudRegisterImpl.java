package kz.sueta.eventservice.register.impl;

import kz.sueta.eventservice.dto.request.EventDetailRequest;
import kz.sueta.eventservice.dto.request.EditEventRequest;
import kz.sueta.eventservice.dto.request.EventListFilter;
import kz.sueta.eventservice.dto.request.SaveEventRequest;
import kz.sueta.eventservice.dto.response.EventListResponse;
import kz.sueta.eventservice.dto.response.EventResponse;
import kz.sueta.eventservice.register.EventCrudRegister;
import kz.sueta.eventservice.repository.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventCrudRegisterImpl implements EventCrudRegister {

    private final EventDao eventDao;

    @Autowired
    public EventCrudRegisterImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public void saveEvent(SaveEventRequest saveRequest) {
    }

    @Override
    public void editEvent(EditEventRequest editEventRequest) {

    }

    @Override
    public void blockEvent(EventDetailRequest eventDetailRequest) {
        eventDao.updateEventBlocked(true, eventDetailRequest.eventId);
    }

    @Override
    public void deleteEvent(EventDetailRequest eventDetailRequest) {
        eventDao.updateEventActual(false, eventDetailRequest.eventId);
    }

    @Override
    public EventListResponse eventList(EventListFilter filter) {
        return null;
    }

    @Override
    public EventResponse eventDetail(EventDetailRequest eventDetailRequest) {
        return null;
    }
}
