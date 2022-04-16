package kz.sueta.eventservice.register.impl;

import com.google.common.base.Strings;
import kz.sueta.eventservice.dto.request.*;
import kz.sueta.eventservice.dto.response.EventListResponse;
import kz.sueta.eventservice.dto.response.EventResponse;
import kz.sueta.eventservice.dto.response.FileIdModel;
import kz.sueta.eventservice.entity.Event;
import kz.sueta.eventservice.entity.EventContent;
import kz.sueta.eventservice.entity.EventCreator;
import kz.sueta.eventservice.exception.NoDataByIdException;
import kz.sueta.eventservice.register.EventCrudRegister;
import kz.sueta.eventservice.repository.CategoryDictionaryDao;
import kz.sueta.eventservice.repository.EventContentDao;
import kz.sueta.eventservice.repository.EventCreatorDao;
import kz.sueta.eventservice.repository.EventDao;
import kz.sueta.eventservice.service_messaging.FileServiceClient;
import kz.sueta.eventservice.util.CategoryStatic;
import kz.sueta.eventservice.util.DbUtil;
import kz.sueta.eventservice.util.ServiceFallBackStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class EventCrudRegisterImpl implements EventCrudRegister {

    private final EventDao eventDao;
    private final EventCreatorDao eventCreatorDao;
    private final EventContentDao eventContentDao;
    private final FileServiceClient fileServiceClient;
    private final CategoryDictionaryDao categoryDictionaryDao;
    private final Environment environment;

    @Autowired
    public EventCrudRegisterImpl(EventDao eventDao,
                                 EventCreatorDao eventCreatorDao,
                                 EventContentDao eventContentDao,
                                 FileServiceClient fileServiceClient,
                                 CategoryDictionaryDao categoryDictionaryDao,
                                 Environment environment) {
        this.eventDao = eventDao;
        this.eventCreatorDao = eventCreatorDao;
        this.eventContentDao = eventContentDao;
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

        setCategoryId(event, saveRequest.categoryId);

        eventDao.saveAndFlush(event);

        EventCreator eventCreator = new EventCreator();
        eventCreator.eventId = event.eventId;
        eventCreator.clientId = saveRequest.creatorId;
        eventCreatorDao.saveAndFlush(eventCreator);

        saveEventContent(saveRequest.images, event.eventId);
        saveEventContent(saveRequest.videos, event.eventId);
    }

    private void saveEventContent(List<MultipartFile> multipartFiles, String eventId) {
        EventContent eventContent = new EventContent();
        eventContent.eventId = eventId;

        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            for (MultipartFile file : multipartFiles) {
                FileIdModel fileIdModel = fileServiceClient.saveFile(FileCreateRequest.of(file));

                if (fileIdModel != null && !Strings.isNullOrEmpty(fileIdModel.fileId)
                        && !ServiceFallBackStatic.SERVICE_FALLBACK_ID.equals(fileIdModel.fileId)) {
                    eventContent.fileId = fileIdModel.fileId;

                    eventContentDao.saveAndFlush(eventContent);
                } else {
                    System.out.println("File saving error");
                    //todo add logging
                }
            }
        }
    }

    @Override
    public void editEvent(EditEventRequest editEventRequest) {
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

        setCategoryId(event, editEventRequest.categoryId);

        eventDao.saveAndFlush(event);

        saveEventContent(editEventRequest.images, event.eventId);
        saveEventContent(editEventRequest.videos, event.eventId);

    }

    private void setCategoryId(Event event, String categoryId) {
        if (categoryDictionaryDao.countCategoryDictionariesByCategoryIdAndActual
                (categoryId, true) > 1) {
            event.categoryId = categoryId;
        } else {
            //todo add logging
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
    public EventListResponse eventList(EventListFilter filter) throws SQLException { //todo add participants, content

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
                        "        ec.client_Id     as creatorId " +
                        " from event e " +
                        " left join event_creator ec on ec.event_Id = e.event_Id " +
                        " where e.actual = ? and e.blocked = ? ";

        if (!Strings.isNullOrEmpty(filter.categoryId)) {
            sql += " and e.category_Id = ''" + filter.categoryId + "'";
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
                        responses.add(eventResponse);
                    }
                }
            }
        }

        return EventListResponse.of(responses);
    }

    @Override
    public EventResponse eventDetail(DetailRequest eventDetailRequest) throws SQLException { //todo add participants, content

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
                        "        ec.client_Id     as creatorId " +
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
                    }
                }
            }
        }

        return eventResponse;
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
    }
}
