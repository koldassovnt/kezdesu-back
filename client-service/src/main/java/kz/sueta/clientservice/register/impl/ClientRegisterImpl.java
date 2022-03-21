package kz.sueta.clientservice.register.impl;

import kz.sueta.clientservice.dto.services.request.ClientBlockRequest;
import kz.sueta.clientservice.dto.services.request.ClientListFilter;
import kz.sueta.clientservice.dto.services.response.ClientListResponse;
import kz.sueta.clientservice.dto.services.response.ClientResponse;
import kz.sueta.clientservice.dto.ui.request.PhoneSmsRequest;
import kz.sueta.clientservice.dto.ui.response.MessageResponse;
import kz.sueta.clientservice.entity.Client;
import kz.sueta.clientservice.register.ClientRegister;
import kz.sueta.clientservice.repository.ClientDao;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class ClientRegisterImpl implements ClientRegister {

    private final PasswordEncoder passwordEncoder;

    private final ClientDao clientDao;

    @Autowired
    public ClientRegisterImpl(PasswordEncoder passwordEncoder, ClientDao clientDao) {
        this.passwordEncoder = passwordEncoder;
        this.clientDao = clientDao;
    }

    @Override
    public Client saveClient(PhoneSmsRequest phoneSmsRequest) {
        Client client = new Client();
        client.clientId = UUID.randomUUID().toString();
        client.phone = phoneSmsRequest.phoneNumber;
        client.createdAt = new Timestamp(new Date().getTime());
        client.password = passwordEncoder.encode(phoneSmsRequest.phoneNumber + client.clientId);
        client.actual = true;

        clientDao.save(client);

        return client;
    }

    @Override
    public Client getClient(PhoneSmsRequest phoneSmsRequest) {
        return clientDao.findClientByPhoneAndActual(phoneSmsRequest.phoneNumber, true);
    }

    @SneakyThrows
    @Override
    public ClientListResponse listClient(ClientListFilter filter) {

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
                "select c.client_id      as clientId, " +
                        "       c.phone          as phone, " +
                        "       c.email          as email, " +
                        "       c.actual         as actual, " +
                        "       c.blocked        as blocked, " +
                        "       c.blocked_reason as blockedReason, " +
                        "       cd.displayname   as displayName, " +
                        "       cd.name          as name, " +
                        "       cd.surname       as surname, " +
                        "       cd.birthDate     as birthDate " +
                        "from client c " +
                        "         left join client_detail cd on c.client = cd.client " +
                        "where c.actual = ? " +
                        "  and c.blocked = ? " +
                        "limit " + filter.limit + " offset " + filter.offset;

        List<ClientResponse> response = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5444/client-service", "admin", "admin")) {
            try (PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setBoolean(1, filter.actual);
                ps.setBoolean(2, filter.blocked);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        ClientResponse clientResponse = new ClientResponse();
                        clientResponse.clientId = rs.getString("clientId");
                        clientResponse.phone = rs.getString("phone");
                        clientResponse.email = rs.getString("email");
                        clientResponse.actual = rs.getBoolean("actual");
                        clientResponse.blocked = rs.getBoolean("blocked");
                        clientResponse.blockedReason = rs.getString("blockedReason");
                        clientResponse.displayName = rs.getString("displayName");
                        clientResponse.name = rs.getString("name");
                        clientResponse.surname = rs.getString("surname");
                        clientResponse.birthDate = rs.getTimestamp("birthDate");
                        response.add(clientResponse);
                    }
                }
            }
        }

        return ClientListResponse.of(response);
    }

    @Override
    public void blockClient(ClientBlockRequest request) {
        clientDao.updateClientBlocked(true, request.blockReason, request.id);
    }

}
