package kz.sueta.clientservice.register.impl;

import com.google.common.base.Strings;
import kz.sueta.clientservice.dto.services.request.*;
import kz.sueta.clientservice.dto.services.response.AdminDetail;
import kz.sueta.clientservice.dto.services.response.CategoryListResponse;
import kz.sueta.clientservice.dto.services.response.EventListResponse;
import kz.sueta.clientservice.dto.services.response.EventResponse;
import kz.sueta.clientservice.dto.ui.response.ClientEventListResponse;
import kz.sueta.clientservice.dto.ui.response.ClientEventResponse;
import kz.sueta.clientservice.dto.ui.response.ClientInfoResponse;
import kz.sueta.clientservice.dto.ui.response.MessageResponse;
import kz.sueta.clientservice.entity.Client;
import kz.sueta.clientservice.entity.ClientDetail;
import kz.sueta.clientservice.exception.ui.RestException;
import kz.sueta.clientservice.register.EventRegister;
import kz.sueta.clientservice.repository.ClientDao;
import kz.sueta.clientservice.repository.ClientDetailDao;
import kz.sueta.clientservice.service_messaging.AdminServiceClient;
import kz.sueta.clientservice.service_messaging.EventServiceClient;
import kz.sueta.clientservice.util.DbUtil;
import kz.sueta.clientservice.util.ServiceFallbackStatic;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventRegisterImpl implements EventRegister {

    private final EventServiceClient eventServiceClient;
    private final ClientDao clientDao;
    private final AdminServiceClient adminServiceClient;
    private final ClientDetailDao clientDetailDao;
    private final Environment environment;

    @Autowired
    public EventRegisterImpl(EventServiceClient eventServiceClient,
                             ClientDao clientDao,
                             AdminServiceClient adminServiceClient,
                             ClientDetailDao clientDetailDao,
                             Environment environment) {
        this.eventServiceClient = eventServiceClient;
        this.clientDao = clientDao;
        this.adminServiceClient = adminServiceClient;
        this.clientDetailDao = clientDetailDao;
        this.environment = environment;
    }

    @SneakyThrows
    @Override
    public ClientEventListResponse listEvents(EventListFilter filter) {
        EventListResponse eventListResponse = eventServiceClient.listEvent(
                filter.limit,
                filter.offset,
                filter.categoryId,
                filter.labelSearch,
                filter.clientId,
                filter.actual,
                filter.blocked);

        List<ClientEventResponse> eventResponses = new ArrayList<>();

        for (EventResponse er : eventListResponse.events) {
            eventResponses.add(mapClientEventResponse(er));
        }

        return ClientEventListResponse.of(eventResponses);
    }

    @SneakyThrows
    @Override
    public ClientEventResponse detailEvent(DetailRequest request) {
        EventResponse eventResponse = eventServiceClient.detailEvent(request.id);

        if (eventResponse == null) {
            throw new RuntimeException("I66IvYen3h :: event service calling returned error for DETAIL");
        }

        return mapClientEventResponse(eventResponse);
    }

    @Override
    public MessageResponse saveEvent(SaveEventRequest saveRequest, String clientId) {
        if (Strings.isNullOrEmpty(clientId)) {
            throw new RestException("FjGDvnJ8U0 :: Authorization is incorrect, please login again!");
        }

        saveRequest.creatorId = clientId;
        MessageResponse response = eventServiceClient.saveEvent(saveRequest);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(response.message)) {
            throw new RuntimeException("pprONO71i1 :: event service calling returned error for SAVE");
        }

        return response;
    }

    @Override
    public MessageResponse editEvent(EditEventRequest editEventRequest, String clientId) {
        if (Strings.isNullOrEmpty(clientId)) {
            throw new RestException("8T07yOfO70 :: Authorization is incorrect, please login again!");
        }

        editEventRequest.clientId = clientId;
        MessageResponse response = eventServiceClient.editEvent(editEventRequest);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(response.message)) {
            throw new RuntimeException("SXJ29LEIPc :: event service calling returned error for EDIT");
        }

        return response;
    }

    @Override
    public ClientInfoResponse joinEvent(DetailRequest detailRequest, String clientId) {
        if (Strings.isNullOrEmpty(clientId)) {
            throw new RestException("vaTi0Hgd47 :: Authorization is incorrect, please login again!");
        }

        Client client = clientDao.findClientByClientIdAndActual(clientId, true);

        if (client == null) {
            throw new RuntimeException("Ad1jV19pXf :: client by clientId=" + clientId + " does not exists!");
        }

        MessageResponse response = eventServiceClient.joinEvent(ClientEventRequest.of(clientId, detailRequest.id));

        if (ServiceFallbackStatic.CREATOR_ACTING_IN_HIS_EVENT.equals(response.message)) {
            throw new RestException("wBZC6mF2Q0 :: creator can not join to his event!");
        }

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(response.message)) {
            throw new RuntimeException("e29YH4Xcs7 :: event service calling returned error for JOIN");
        }

        ClientDetail cd = clientDetailDao.findClientDetailByClient(client.client);

        if (cd == null) {
            cd = new ClientDetail();
        }

        ClientInfoResponse clientInfoResponse = new ClientInfoResponse();
        clientInfoResponse.clientId = clientId;
        clientInfoResponse.phone = client.phone;
        clientInfoResponse.displayName = cd.displayName;
        clientInfoResponse.imgId = cd.imgId;

        return clientInfoResponse;
    }

    @Override
    public MessageResponse qrEvent(DetailRequest detailRequest, String clientId) {
        if (Strings.isNullOrEmpty(clientId)) {
            throw new RestException("2kheBjZw5A :: Authorization is incorrect, please login again!");
        }

        Client client = clientDao.findClientByClientIdAndActual(clientId, true);

        if (client == null) {
            throw new RuntimeException("pUMdNZdY9F :: client by clientId=" + clientId + " does not exists!");
        }

        MessageResponse response = eventServiceClient.approveEvent(ClientEventRequest.of(clientId, detailRequest.id));

        if (ServiceFallbackStatic.CREATOR_ACTING_IN_HIS_EVENT.equals(response.message)) {
            throw new RestException("v2XE8TK6Y9 :: creator can not QR his event!");
        }

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(response.message)) {
            throw new RuntimeException("g65NVMtw87 :: event service calling returned error for APPROVE");
        }

        return response;
    }

    @Override
    public EventListResponse clientEvents(String clientId) {
        if (Strings.isNullOrEmpty(clientId)) {
            throw new RestException("oOGpBitVyG :: Authorization is incorrect, please login again!");
        }

        return eventServiceClient.clientEvents(true, clientId);
    }

    @Override
    public EventListResponse clientParticipatedEvents(String clientId) {
        if (Strings.isNullOrEmpty(clientId)) {
            throw new RestException("d3dyc3cm51 :: Authorization is incorrect, please login again!");
        }

        return eventServiceClient.clientEvents(false, clientId);
    }

    @Override
    public CategoryListResponse categoryList() {
        return eventServiceClient.listCategory(100, 0, true);
    }

    private ClientEventResponse mapClientEventResponse(EventResponse er) throws SQLException {

        if (er == null) {
            return new ClientEventResponse();
        }

        ClientEventResponse cer = new ClientEventResponse();

        cer.eventId = er.eventId;
        cer.label = er.label;
        cer.description = er.description;
        cer.startedAt = er.startedAt;
        cer.endedAt = er.endedAt;
        cer.latitude = er.latitude;
        cer.longitude = er.longitude;
        cer.categoryId = er.categoryId;
        cer.actual = er.actual;
        cer.blocked = er.blocked;
        cer.address = er.address;
        cer.contentList = er.contentList;

        cer.creatorInfo = new ClientInfoResponse();

        Client client = clientDao.findClientByClientIdAndActual(er.creatorId, true);

        if (client != null) {

            ClientDetail clientDetail = clientDetailDao.findClientDetailByClient(client.client);

            if (clientDetail == null) {
                clientDetail = new ClientDetail();
            }

            String displayName = clientDetail.displayName;

            cer.creatorInfo.clientId = client.clientId;
            cer.creatorInfo.phone = client.phone;
            cer.creatorInfo.displayName = displayName;
            cer.creatorInfo.imgId = clientDetail.imgId;
        } else {
            AdminDetail adminDetail = adminServiceClient.getAdminDetail(er.creatorId);
            cer.creatorInfo.clientId = adminDetail.adminId;
            cer.creatorInfo.phone = adminDetail.phone;
            cer.creatorInfo.displayName = adminDetail.displayName;
        }

        try (Connection connection = DbUtil.getConnection(environment)) {

            for (String id : er.participantList) {

                try (PreparedStatement ps = connection.prepareStatement(
                        "select x.client_id        as clientId, " +
                                "       x.phone        as phone, " +
                                "       x1.displayname as displayName, " +
                                "       x1.img_id      as imgId " +
                                "from client x " +
                                "left join client_detail x1 on x1.client = x.client " +
                                "where client_id = ? and actual = true")) {

                    ps.setString(1, id);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            ClientInfoResponse infoResponse = new ClientInfoResponse();
                            infoResponse.clientId = rs.getString("clientId");
                            infoResponse.phone = rs.getString("phone");
                            infoResponse.displayName = rs.getString("displayName");
                            infoResponse.imgId = rs.getString("imgId");
                            cer.participantList.add(infoResponse);
                        }
                    }
                }
            }
        }

        return cer;
    }
}
