package kz.sueta.eventservice.register.impl;

import com.google.common.base.Strings;
import kz.sueta.eventservice.dto.request.*;
import kz.sueta.eventservice.dto.response.EventListResponse;
import kz.sueta.eventservice.dto.response.EventResponse;
import kz.sueta.eventservice.dto.response.FileIdModel;
import kz.sueta.eventservice.entity.Event;
import kz.sueta.eventservice.entity.EventContent;
import kz.sueta.eventservice.entity.EventCreator;
import kz.sueta.eventservice.register.EventCrudRegister;
import kz.sueta.eventservice.repository.CategoryDictionaryDao;
import kz.sueta.eventservice.repository.EventContentDao;
import kz.sueta.eventservice.repository.EventCreatorDao;
import kz.sueta.eventservice.repository.EventDao;
import kz.sueta.eventservice.service_messaging.FileServiceClient;
import kz.sueta.eventservice.util.CategoryStatic;
import kz.sueta.eventservice.util.ServiceFallBackStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Component
public class EventCrudRegisterImpl implements EventCrudRegister {

    private final EventDao eventDao;
    private final EventCreatorDao eventCreatorDao;
    private final EventContentDao eventContentDao;
    private final FileServiceClient fileServiceClient;
    private final CategoryDictionaryDao categoryDictionaryDao;
    private final EntityManager entityManager;

    @Autowired
    public EventCrudRegisterImpl(EventDao eventDao,
                                 EventCreatorDao eventCreatorDao,
                                 EventContentDao eventContentDao,
                                 FileServiceClient fileServiceClient,
                                 CategoryDictionaryDao categoryDictionaryDao,
                                 EntityManager entityManager) {
        this.eventDao = eventDao;
        this.eventCreatorDao = eventCreatorDao;
        this.eventContentDao = eventContentDao;
        this.fileServiceClient = fileServiceClient;
        this.categoryDictionaryDao = categoryDictionaryDao;
        this.entityManager = entityManager;
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

        setCategoryId(event, saveRequest.categoryId);

        eventDao.save(event);

        EventCreator eventCreator = new EventCreator();
        eventCreator.eventId = event.eventId;
        eventCreator.clientId = saveRequest.creatorId;
        eventCreatorDao.save(eventCreator);

        saveEventContent(saveRequest.images, event.eventId);
        saveEventContent(saveRequest.videos, event.eventId);
    }

    private void saveEventContent(List<MultipartFile> multipartFiles, String eventId) {
        EventContent eventContent = new EventContent();
        eventContent.eventId = eventId;

        if (!multipartFiles.isEmpty()) {
            for (MultipartFile file : multipartFiles) {
                FileIdModel fileIdModel = fileServiceClient.saveFile(FileCreateRequest.of(file));

                if (fileIdModel != null && !Strings.isNullOrEmpty(fileIdModel.fileId)
                        && !ServiceFallBackStatic.SERVICE_FALLBACK_ID.equals(fileIdModel.fileId)) {
                    eventContent.fileId = fileIdModel.fileId;

                    eventContentDao.save(eventContent);
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

        saveEventContent(editEventRequest.images, editEventRequest.eventId);
        saveEventContent(editEventRequest.videos, editEventRequest.eventId);
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
    public void blockEvent(EventDetailRequest eventDetailRequest) {
        eventDao.updateEventBlocked(true, eventDetailRequest.eventId);
    }

    @Override
    public void deleteEvent(EventDetailRequest eventDetailRequest) {
        eventDao.updateEventActual(false, eventDetailRequest.eventId);
    }

    @Override
    public EventListResponse eventList(EventListFilter filter) {

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

        boolean addCategoryId = false;
        boolean addLabelSearch = false;
        boolean addClientId = false;

        String sql =
                " select e.eventId       as eventId, " +
                "        e.label         as label, " +
                "        e.description   as description, " +
                "        e.startedAt     as startedAt, " +
                "        e.endedAt       as endedAt, " +
                "        e.latitude      as latitude, " +
                "        e.longitude     as longitude, " +
                "        e.categoryId    as categoryId, " +
                "        e.actual        as actual, " +
                "        e.blocked       as blocked, " +
                "        ec.clientId     as creatorId " +
                " from Event e " +
                " left join EventCreator ec on ec.eventId = e.eventId " +
                " where e.actual = :actual and e.blocked = :blocked ";

        if (!Strings.isNullOrEmpty(filter.categoryId)) {
            sql += " and e.categoryId = :categoryId ";
            addCategoryId = true;
        }

        if (!Strings.isNullOrEmpty(filter.labelSearch)) {
            sql += " and e.label like CONCAT('%',:labelSearch,'%') ";
            addLabelSearch = true;
        }

        if (!Strings.isNullOrEmpty(filter.clientId)) {
            sql += " and ec.clientId = :clientId ";
            addClientId = true;
        }

        sql += " limit " + filter.limit + " offset " + filter.offset + " ";

        TypedQuery<EventResponse> query = entityManager.createQuery(sql, EventResponse.class);

        query.setParameter("actual", filter.actual)
                .setParameter("blocked", filter.blocked);

        if (addCategoryId) {
            query.setParameter("categoryId", filter.categoryId);
        }
        if (addLabelSearch) {
            query.setParameter("labelSearch", filter.labelSearch);
        }
        if (addClientId) {
            query.setParameter("clientId", filter.clientId);
        }

        List<EventResponse> responses = query.getResultList();

        return EventListResponse.of(responses);
    }

    @Override
    public EventResponse eventDetail(EventDetailRequest eventDetailRequest) {

        String sql =
                        " select e.eventId       as eventId, " +
                        "        e.label         as label, " +
                        "        e.description   as description, " +
                        "        e.startedAt     as startedAt, " +
                        "        e.endedAt       as endedAt, " +
                        "        e.latitude      as latitude, " +
                        "        e.longitude     as longitude, " +
                        "        e.categoryId    as categoryId, " +
                        "        e.actual        as actual, " +
                        "        e.blocked       as blocked, " +
                        "        ec.clientId     as creatorId " +
                        " from Event e " +
                        " left join EventCreator ec on ec.eventId = e.eventId " +
                        " where e.actual = true and e.blocked = false ";

        TypedQuery<EventResponse> query = entityManager.createQuery(sql, EventResponse.class);

        return query.getSingleResult();
    }
}
