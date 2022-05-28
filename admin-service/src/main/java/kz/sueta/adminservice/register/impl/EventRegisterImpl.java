package kz.sueta.adminservice.register.impl;

import com.netflix.servo.util.Strings;
import kz.sueta.adminservice.dto.services.request.*;
import kz.sueta.adminservice.dto.services.response.*;
import kz.sueta.adminservice.dto.ui.response.AdminEventListResponse;
import kz.sueta.adminservice.dto.ui.response.AdminEventResponse;
import kz.sueta.adminservice.dto.ui.response.ClientInfoResponse;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;
import kz.sueta.adminservice.entity.Account;
import kz.sueta.adminservice.exception.ui.RestException;
import kz.sueta.adminservice.register.EventRegister;
import kz.sueta.adminservice.repository.AccountDao;
import kz.sueta.adminservice.service_messaging.ClientServiceClient;
import kz.sueta.adminservice.service_messaging.EventServiceClient;
import kz.sueta.adminservice.util.ServiceFallbackStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventRegisterImpl implements EventRegister {

    private final EventServiceClient eventServiceClient;
    private final AccountDao accountDao;
    private final ClientServiceClient clientServiceClient;

    @Autowired
    public EventRegisterImpl(EventServiceClient eventServiceClient,
                             AccountDao accountDao,
                             ClientServiceClient clientServiceClient) {
        this.eventServiceClient = eventServiceClient;
        this.accountDao = accountDao;
        this.clientServiceClient = clientServiceClient;
    }

    @Override
    public MessageResponse saveEvent(SaveEventRequest saveRequest, String email) {

        if (Strings.isNullOrEmpty(email)) {
            throw new RestException("2h1mkSxfnT :: Authorization is incorrect, please login again!");
        }

        Account account = accountDao.findAccountByEmailAndActual(email, true);

        if (account == null) {
            throw new RuntimeException("hCp867b9Lk :: account is null by email!");
        }

        saveRequest.creatorId = account.accountId;

        MessageResponse messageResponse = eventServiceClient.saveEvent(saveRequest);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(messageResponse.message)) {
            throw new RuntimeException("ucz9l272zk :: event service calling returned error for SAVE");
        }

        return messageResponse;
    }

    @Override
    public MessageResponse editEvent(EditEventRequest editEventRequest) {
        MessageResponse messageResponse = eventServiceClient.editEvent(editEventRequest);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(messageResponse.message)) {
            throw new RuntimeException("e93d55qG86 :: event service calling returned error for EDIT");
        }

        return messageResponse;
    }

    @Override
    public MessageResponse blockEvent(DetailRequest eventDetailRequest) {
        MessageResponse messageResponse = eventServiceClient.blockEvent(eventDetailRequest);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(messageResponse.message)) {
            throw new RuntimeException("82n5jH3NM6 :: event service calling returned error for BLOCK");
        }

        return messageResponse;
    }

    @Override
    public MessageResponse deleteEvent(DetailRequest eventDetailRequest) {
        MessageResponse messageResponse = eventServiceClient.deleteEvent(eventDetailRequest);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(messageResponse.message)) {
            throw new RuntimeException("UJ2f5qa6pt :: event service calling returned error for DELETE");
        }

        return messageResponse;
    }

    @Override
    public AdminEventListResponse listEvent(EventListFilter filter) {
        EventListResponse eventListResponse = eventServiceClient.listEvent(filter.limit, filter.offset, filter.categoryId,
                filter.labelSearch, filter.clientId, filter.actual, filter.blocked);

        List<AdminEventResponse> adminEventResponseList = new ArrayList<>();

        for (EventResponse er : eventListResponse.events) {
            adminEventResponseList.add(mapAdminEventResponse(er));
        }

        return AdminEventListResponse.of(adminEventResponseList);
    }

    @Override
    public AdminEventResponse detailEvent(DetailRequest eventDetailRequest) {
        EventResponse eventResponse = eventServiceClient.detailEvent(eventDetailRequest.id);

        if (eventResponse == null) {
            throw new RuntimeException("8m2VlOC3gT :: event service calling returned error for DETAIL");
        }

        return mapAdminEventResponse(eventResponse);
    }

    @Override
    public AdminComplainListResponse complainList() {
        ComplainListResponse response = eventServiceClient.complainList();

        List<AdminComplainResponse> adminComplainResponses = new ArrayList<>();

        for (ComplainResponse cr : response.detailResponses) {
            AdminComplainResponse acr = new AdminComplainResponse();
            acr.complainId = cr.complainId;
            acr.complainText = cr.complainText;
            acr.eventId = cr.eventId;
            acr.eventName = cr.eventName;
            acr.clientId = cr.clientId;

            ClientResponse clientResponse = clientServiceClient.detailClient(cr.clientId);
            acr.clientPhone = clientResponse.phone;
            adminComplainResponses.add(acr);
        }

        return AdminComplainListResponse.of(adminComplainResponses);
    }

    private AdminEventResponse mapAdminEventResponse(EventResponse er) {
        if (er == null) {
            return new AdminEventResponse();
        }

        AdminEventResponse aer = new AdminEventResponse();
        aer.eventId = er.eventId;
        aer.label = er.label;
        aer.description = er.description;
        aer.startedAt = er.startedAt;
        aer.endedAt = er.endedAt;
        aer.latitude = er.latitude;
        aer.longitude = er.longitude;
        aer.categoryId = er.categoryId;
        aer.actual = er.actual;
        aer.blocked = er.blocked;
        aer.address = er.address;

        aer.creatorInfo = new ClientInfoResponse();

        Account account = accountDao.findAccountByAccountIdAndActual(er.creatorId, true);

        if (account != null) {
            aer.creatorInfo.clientId = er.creatorId;
            aer.creatorInfo.phone = account.phone;
            aer.creatorInfo.displayName = account.displayName;
        } else {
            ClientResponse clientResponse = clientServiceClient.detailClient(er.creatorId);

            if (clientResponse == null) {
                clientResponse = new ClientResponse();
            }
            aer.creatorInfo.clientId = clientResponse.clientId;
            aer.creatorInfo.phone = clientResponse.phone;
            aer.creatorInfo.displayName = clientResponse.displayName;
            aer.creatorInfo.imgId = clientResponse.imgId;
        }

        ClientListResponse clientListResponse = clientServiceClient
                .listClientById(IdListRequest.of(er.participantList));

        for (ClientResponse cr : clientListResponse.clients) {
            ClientInfoResponse infoResponse = new ClientInfoResponse();
            infoResponse.clientId = cr.clientId;
            infoResponse.phone = cr.phone;
            infoResponse.displayName = cr.displayName;
            infoResponse.imgId = cr.imgId;
            aer.participantList.add(infoResponse);
        }

        return aer;
    }
}
