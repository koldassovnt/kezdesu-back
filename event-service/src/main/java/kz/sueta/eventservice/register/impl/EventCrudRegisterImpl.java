package kz.sueta.eventservice.register.impl;

import com.google.common.base.Strings;
import kz.sueta.eventservice.dto.request.DetailRequest;
import kz.sueta.eventservice.dto.request.EditEventRequest;
import kz.sueta.eventservice.dto.request.EventListFilter;
import kz.sueta.eventservice.dto.request.SaveEventRequest;
import kz.sueta.eventservice.dto.response.ContentResponse;
import kz.sueta.eventservice.dto.response.EventListResponse;
import kz.sueta.eventservice.dto.response.EventResponse;
import kz.sueta.eventservice.entity.Event;
import kz.sueta.eventservice.entity.EventCreator;
import kz.sueta.eventservice.entity.id_class.EventCreatorId;
import kz.sueta.eventservice.exception.NoDataByIdException;
import kz.sueta.eventservice.register.EventCrudRegister;
import kz.sueta.eventservice.repository.CategoryDictionaryDao;
import kz.sueta.eventservice.repository.EventCreatorDao;
import kz.sueta.eventservice.repository.EventDao;
import kz.sueta.eventservice.service_messaging.FileServiceClient;
import kz.sueta.eventservice.util.CategoryStatic;
import kz.sueta.eventservice.util.DbUtil;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class EventCrudRegisterImpl implements EventCrudRegister {

    private static final Logger logger = LoggerFactory.getLogger(EventCrudRegisterImpl.class);

    private final EventDao eventDao;
    private final EventCreatorDao eventCreatorDao;
    private final FileServiceClient fileServiceClient;
    private final CategoryDictionaryDao categoryDictionaryDao;
    private final Environment environment;

    @Autowired
    public EventCrudRegisterImpl(EventDao eventDao,
                                 EventCreatorDao eventCreatorDao,
                                 FileServiceClient fileServiceClient,
                                 CategoryDictionaryDao categoryDictionaryDao,
                                 Environment environment) {
        this.eventDao = eventDao;
        this.eventCreatorDao = eventCreatorDao;
        this.fileServiceClient = fileServiceClient;
        this.categoryDictionaryDao = categoryDictionaryDao;
        this.environment = environment;
    }

    @Override
    public void saveEvent(SaveEventRequest saveRequest) {
        Event event = new Event();
        event.eventId = UUID.randomUUID().toString();
        event.actual = true;
        event.label = saveRequest.label;
        event.description = saveRequest.description;
        event.startedAt = new Timestamp(saveRequest.startedAt.getTime());
        event.endedAt = new Timestamp(saveRequest.endedAt.getTime());
        event.latitude = saveRequest.latitude;
        event.longitude = saveRequest.longitude;
        event.blocked = false;
        event.address = saveRequest.address;

        setCategoryId(event, saveRequest.categoryId);

        eventDao.saveAndFlush(event);

        EventCreator eventCreator = new EventCreator();
        eventCreator.eventId = event.eventId;
        eventCreator.clientId = saveRequest.creatorId;
        eventCreatorDao.saveAndFlush(eventCreator);
    }

    @Override
    public void editEvent(EditEventRequest editEventRequest) {

        EventCreatorId eventCreatorId = EventCreatorId.of(editEventRequest.eventId, editEventRequest.clientId);

        Optional<EventCreator> eventCreator = eventCreatorDao.findById(eventCreatorId);

        if (eventCreator.isEmpty()) {
            throw new RuntimeException("v67FY6CGg8 :: there is no event of this client = " + editEventRequest.clientId);
        }

        Event event = eventDao.findEventByEventIdAndActual(editEventRequest.eventId, true);

        if (event == null) {
            throw new NoDataByIdException();
        }

        if (!Strings.isNullOrEmpty(editEventRequest.label)) {
            event.label = editEventRequest.label;
        }

        if (!Strings.isNullOrEmpty(editEventRequest.description)) {
            event.description = editEventRequest.description;
        }

        if (editEventRequest.startedAt != null) {
            event.startedAt = new Timestamp(editEventRequest.startedAt.getTime());
        }

        if (editEventRequest.endedAt != null) {
            event.endedAt = new Timestamp(editEventRequest.endedAt.getTime());
        }

        if (editEventRequest.latitude != null) {
            event.latitude = editEventRequest.latitude;
        }

        if (editEventRequest.longitude != null) {
            event.longitude = editEventRequest.longitude;
        }

        if (!Strings.isNullOrEmpty(editEventRequest.address)) {
            event.address = editEventRequest.address;
        }

        setCategoryId(event, editEventRequest.categoryId);

        eventDao.saveAndFlush(event);
    }

    private void setCategoryId(Event event, String categoryId) {
        if (categoryDictionaryDao.countCategoryDictionariesByCategoryIdAndActual
                (categoryId, true) >= 1) {
            event.categoryId = categoryId;
        } else {
            logger.info("Ji11HoFFKb :: category was not found by Id = " + categoryId +
                    " category will be = " + CategoryStatic.GENERAL_CATEGORY_CODE);
            event.categoryId = CategoryStatic.GENERAL_CATEGORY_CODE;
        }
    }

    @Override
    public void blockEvent(DetailRequest eventDetailRequest) {
        Event event = eventDao.findEventByEventId(eventDetailRequest.id);

        if (event == null) {
            throw new NoDataByIdException("VnwnHg1M2X :: event by this id does not exists!");
        }

        event.blocked = true;
        eventDao.saveAndFlush(event);
    }

    @Override
    public void deleteEvent(DetailRequest eventDetailRequest) {
        Event event = eventDao.findEventByEventId(eventDetailRequest.id);

        if (event == null) {
            throw new NoDataByIdException("X9O0sPT5BB :: event by this id does not exists!");
        }

        event.actual = false;
        eventDao.saveAndFlush(event);
    }

    @Override
    public EventListResponse eventList(EventListFilter filter) throws SQLException {

        if (filter.blocked == null) {
            filter.blocked = false;
        }

        if (filter.actual == null) {
            filter.actual = true;
        }

        if (filter.limit == null) {
            filter.limit = 10;
        }

        if (filter.offset == null) {
            filter.offset = 0;
        }

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
                        "        ec.client_Id     as creatorId, " +
                        "        e.address        as address " +
                        " from event e " +
                        " left join event_creator ec on ec.event_Id = e.event_Id " +
                        " where e.actual = ? and e.blocked = ? ";

        if (!Strings.isNullOrEmpty(filter.categoryId)) {
            sql += " and e.category_Id = '" + filter.categoryId + "' ";
        }

        if (!Strings.isNullOrEmpty(filter.labelSearch)) {
            sql += " and e.label like CONCAT('%', '" + filter.labelSearch + "' ,'%') ";
        }

        if (!Strings.isNullOrEmpty(filter.clientId)) {
            sql += " and ec.client_Id =  '" + filter.clientId + "' ";
        }

        sql += " limit " + filter.limit + " offset " + filter.offset + " ";

        List<EventResponse> responses = new ArrayList<>();

        try (Connection connection = DbUtil.getConnection(environment)) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setBoolean(1, filter.actual);
                ps.setBoolean(2, filter.blocked);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        EventResponse eventResponse = new EventResponse();

                        setEventResponse(eventResponse, rs);
                        setEventParticipants(eventResponse, connection);
                        setEventContent(eventResponse, connection);

                        responses.add(eventResponse);
                    }
                }
            }
        }

        return EventListResponse.of(responses);
    }

    @SneakyThrows
    private void setEventParticipants(EventResponse eventResponse, Connection connection) {

        try (PreparedStatement ps = connection.prepareStatement(
                "select client_id from event_participant where event_id = ?")) {
            ps.setString(1, eventResponse.eventId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    eventResponse.participantList.add(rs.getString("client_id"));
                }
            }
        }
    }

    @SneakyThrows
    private void setEventContent(EventResponse eventResponse, Connection connection) {

        try (PreparedStatement ps = connection.prepareStatement(
                "select file_id from event_content where event_id = ?")) {
            ps.setString(1, eventResponse.eventId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ContentResponse response = new ContentResponse();
                    response.contentId = rs.getString("file_id");
                    response.contentType = fileServiceClient.getFileType(response.contentId);
                    eventResponse.contentList.add(response);
                }
            }
        }
    }

    @Override
    public EventResponse eventDetail(DetailRequest eventDetailRequest) throws SQLException {

        EventResponse eventResponse = new EventResponse();

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
                        "        ec.client_Id     as creatorId, " +
                        "        e.address        as address " +
                        " from event e " +
                        " left join event_creator ec on ec.event_Id = e.event_Id " +
                        " where e.actual = true and e.blocked = false " +
                        " and e.event_Id = ? ";

        try (Connection connection = DbUtil.getConnection(environment)) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, eventDetailRequest.id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        setEventResponse(eventResponse, rs);
                        setEventParticipants(eventResponse, connection);
                        setEventContent(eventResponse, connection);
                    }
                }
            }
        }

        return eventResponse;
    }

    @Override
    public void closeEvent() throws SQLException {
        logger.info("7BdCCst3X2 :: close Event task start");
        AtomicInteger updatedCount = new AtomicInteger(0);

        try (Connection connection = DbUtil.getConnection(environment)) {
            try (PreparedStatement ps = connection.prepareStatement("" +
                    "update event set actual = false " +
                    "where ended_at < current_timestamp and actual = true")) {
                int update = ps.executeUpdate();
                updatedCount.set(update);
            }
        }

        logger.info("vHtyJ3g5WN :: close Event task finished with updated count = " + updatedCount.get());
    }

    private void setEventResponse(EventResponse eventResponse, ResultSet rs) throws SQLException {
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
        eventResponse.creatorId = rs.getString("creatorId");
        eventResponse.address = rs.getString("address");
    }
}
