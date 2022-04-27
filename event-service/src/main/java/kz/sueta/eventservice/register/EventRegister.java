package kz.sueta.eventservice.register;

import kz.sueta.eventservice.dto.request.ClientEventRequest;
import kz.sueta.eventservice.dto.response.EventListResponse;
import kz.sueta.eventservice.dto.response.MessageResponse;

import java.sql.SQLException;

public interface EventRegister {

    MessageResponse joinEvent(ClientEventRequest request);

    MessageResponse approveEvent(ClientEventRequest request);

    EventListResponse clientEvents(Boolean creator, String clientId) throws SQLException;
}
