package kz.sueta.eventservice.register.impl;

import kz.sueta.eventservice.dto.request.ClientEventRequest;
import kz.sueta.eventservice.dto.request.ComplainEventRequest;
import kz.sueta.eventservice.dto.request.SaveEventContentRequest;
import kz.sueta.eventservice.dto.response.EventListResponse;
import kz.sueta.eventservice.dto.response.EventResponse;
import kz.sueta.eventservice.dto.response.MessageResponse;
import kz.sueta.eventservice.entity.*;
import kz.sueta.eventservice.entity.id_class.EventCreatorId;
import kz.sueta.eventservice.entity.id_class.EventParticipantId;
import kz.sueta.eventservice.register.EventRegister;
import kz.sueta.eventservice.repository.*;
import kz.sueta.eventservice.util.DbUtil;
import kz.sueta.eventservice.util.ServiceFallBackStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class EventRegisterImpl implements EventRegister {

    private final EventDao eventDao;
    private final EventCreatorDao eventCreatorDao;
    private final EventParticipantDao eventParticipantDao;
    private final Environment environment;
    private final EventContentDao eventContentDao;
    private final ComplainDao complainDao;
    private final EventComplainDao eventComplainDao;

    @Autowired
    public EventRegisterImpl(EventDao eventDao,
                             EventCreatorDao eventCreatorDao,
                             EventParticipantDao eventParticipantDao,
                             Environment environment,
                             EventContentDao eventContentDao,
                             ComplainDao complainDao,
                             EventComplainDao eventComplainDao) {
        this.eventDao = eventDao;
        this.eventCreatorDao = eventCreatorDao;
        this.eventParticipantDao = eventParticipantDao;
        this.environment = environment;
        this.eventContentDao = eventContentDao;
        this.complainDao = complainDao;
        this.eventComplainDao = eventComplainDao;
    }

    @Override
    public MessageResponse joinEvent(ClientEventRequest request) {
        Event event = eventDao.findEventByEventIdAndActual(request.eventId, true);

        if (event == null) {
            throw new RuntimeException("I2mNKWWpRR :: event does not exists by this id=" + request.eventId);
        }

        try {
            eventCreatorDao.getById(EventCreatorId.of(request.eventId, request.clientId));
        } catch (EntityNotFoundException ignored) {
            return MessageResponse.of(ServiceFallBackStatic.CREATOR_ACTING_IN_HIS_EVENT);
        }

        EventParticipant eventParticipant = new EventParticipant();
        eventParticipant.eventId = request.eventId;
        eventParticipant.clientId = request.clientId;
        eventParticipant.approved = false;

        eventParticipantDao.saveAndFlush(eventParticipant);

        return MessageResponse.of("Client successfully joined to event!");
    }

    @Override
    public MessageResponse approveEvent(ClientEventRequest request) {
        Event event = eventDao.findEventByEventIdAndActual(request.eventId, true);

        if (event == null) {
            throw new RuntimeException("ovXlkZ0qL7 :: event does not exists by this id=" + request.eventId);
        }

        try {
            eventCreatorDao.getById(EventCreatorId.of(request.eventId, request.clientId));
        } catch (EntityNotFoundException ignored) {
            return MessageResponse.of(ServiceFallBackStatic.CREATOR_ACTING_IN_HIS_EVENT);
        }

        EventParticipant eventParticipant = eventParticipantDao
                .getById(EventParticipantId.of(request.eventId, request.clientId));

        eventParticipant.approved = true;
        eventParticipantDao.saveAndFlush(eventParticipant);

        return MessageResponse.of("Client participating successfully approved!");
    }

    @Override
    public EventListResponse clientEvents(Boolean creator, String clientId) throws SQLException {

        if (creator == null) {
            throw new RuntimeException("1EhxUE9ON6 :: creator can not be null!");
        }

        List<EventResponse> responses = new ArrayList<>();

        String sql =
                " select e.event_Id       as eventId, " +
                        "        e.label         as label, " +
                        "        e.description   as description, " +
                        "        e.started_At     as startedAt, " +
                        "        e.ended_At       as endedAt, " +
                        "        e.latitude      as latitude, " +
                        "        e.longitude     as longitude, " +
                        "        e.category_Id    as categoryId, " +
                        "        e.actual        as actual, " +
                        "        e.blocked       as blocked, " +
                        "        e.address       as address " +
                        " from event e ";

        if (creator) {
            sql += " left join event_creator ec on ec.event_Id = e.event_Id " +
                    " where ec.client_id = ?";
        } else {
            sql += " left join event_participant ep on ep.event_Id = e.event_Id " +
                    " where ep.client_id = ?";
        }

        try (Connection connection = DbUtil.getConnection(environment)) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setString(1, clientId);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        EventResponse eventResponse = new EventResponse();
                        eventResponse.eventId = rs.getString("eventId");
                        eventResponse.label = rs.getString("label");
                        eventResponse.description = rs.getString("description");
                        eventResponse.startedAt = rs.getTimestamp("startedAt");
                        eventResponse.endedAt = rs.getTimestamp("endedAt");
                        eventResponse.latitude = rs.getDouble("latitude");
                        eventResponse.longitude = rs.getDouble("longitude");
                        eventResponse.categoryId = rs.getString("categoryId");
                        eventResponse.actual = rs.getBoolean("actual");
                        eventResponse.blocked = rs.getBoolean("blocked");
                        eventResponse.address = rs.getString("address");
                        responses.add(eventResponse);
                    }
                }
            }
        }

        return EventListResponse.of(responses);
    }

    @Override
    public Boolean isEventExists(String id) {
        Event event = eventDao.findEventByEventIdAndActual(id, true);
        return event != null;
    }

    @Override
    public MessageResponse saveContent(SaveEventContentRequest request) {
        EventContent eventContent = new EventContent();
        eventContent.eventId = request.eventId;
        eventContent.fileId = request.contentId;
        eventContentDao.saveAndFlush(eventContent);

        return MessageResponse.of("OwQoC41DJK :: content was successfully saved");
    }

    @Override
    public MessageResponse complainEvent(ComplainEventRequest request) {
        Event event = eventDao.findEventByEventId(request.eventId);

        if (event == null) {
            throw new RuntimeException("En3vNtOfZm :: event does not exists by this id=" + request.eventId);
        }

        Complain complain = new Complain();
        complain.complainId = UUID.randomUUID().toString();
        complain.clientId = request.clientId;
        complain.complainText = request.complainText;

        complainDao.saveAndFlush(complain);

        EventComplain eventComplain = new EventComplain();
        eventComplain.complainId = complain.complainId;
        eventComplain.eventId = request.eventId;
        eventComplainDao.saveAndFlush(eventComplain);

        return MessageResponse.of("LpUkL30RWk :: event complain was successfully saved");
    }
}
