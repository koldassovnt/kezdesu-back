package kz.sueta.eventservice.register;

import kz.sueta.eventservice.dto.request.ClientEventRequest;
import kz.sueta.eventservice.dto.request.ComplainEventRequest;
import kz.sueta.eventservice.dto.request.SaveEventContentRequest;
import kz.sueta.eventservice.dto.response.EventListResponse;
import kz.sueta.eventservice.dto.response.MessageResponse;

import java.sql.SQLException;

public interface EventRegister {

    MessageResponse joinEvent(ClientEventRequest request);

    MessageResponse approveEvent(ClientEventRequest request);

    EventListResponse clientEvents(Boolean creator, String clientId) throws SQLException;

    Boolean isEventExists(String id);

    MessageResponse saveContent(SaveEventContentRequest request);

    MessageResponse complainEvent(ComplainEventRequest request);
}
