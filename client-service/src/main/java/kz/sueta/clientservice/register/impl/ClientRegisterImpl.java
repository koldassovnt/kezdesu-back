package kz.sueta.clientservice.register.impl;

import com.google.common.base.Strings;
import kz.sueta.clientservice.dto.services.request.*;
import kz.sueta.clientservice.dto.services.response.ClientListResponse;
import kz.sueta.clientservice.dto.services.response.ClientResponse;
import kz.sueta.clientservice.dto.services.response.FileIdModel;
import kz.sueta.clientservice.dto.ui.request.EditClientRequest;
import kz.sueta.clientservice.dto.ui.request.PhoneSmsRequest;
import kz.sueta.clientservice.dto.ui.response.MessageResponse;
import kz.sueta.clientservice.entity.Client;
import kz.sueta.clientservice.entity.ClientDetail;
import kz.sueta.clientservice.exception.ui.RestException;
import kz.sueta.clientservice.register.ClientRegister;
import kz.sueta.clientservice.repository.ClientDao;
import kz.sueta.clientservice.repository.ClientDetailDao;
import kz.sueta.clientservice.service_messaging.FileServiceClient;
import kz.sueta.clientservice.util.DbUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.sql.*;
import java.util.*;
import java.util.Date;

@Component
public class ClientRegisterImpl implements ClientRegister {

    private final PasswordEncoder passwordEncoder;
    private final ClientDao clientDao;
    private final Environment environment;
    private final ClientDetailDao clientDetailDao;
    private final FileServiceClient fileServiceClient;

    @Autowired
    public ClientRegisterImpl(PasswordEncoder passwordEncoder,
                              ClientDao clientDao,
                              ClientDetailDao clientDetailDao,
                              Environment environment,
                              FileServiceClient fileServiceClient) {
        this.passwordEncoder = passwordEncoder;
        this.clientDao = clientDao;
        this.environment = environment;
        this.clientDetailDao = clientDetailDao;
        this.fileServiceClient = fileServiceClient;
    }

    @Override
    public Client saveClient(PhoneSmsRequest phoneSmsRequest) {
        Client client = new Client();
        client.clientId = UUID.randomUUID().toString();
        client.phone = phoneSmsRequest.phoneNumber;
        client.createdAt = new Timestamp(new Date().getTime());
        client.password = passwordEncoder.encode(phoneSmsRequest.phoneNumber + client.clientId);
        client.actual = true;
        client.blocked = false;
        client.blockedReason = null;

        clientDao.save(client);

        ClientDetail clientDetail = new ClientDetail();
        clientDetail.client = client.client;
        clientDetailDao.save(clientDetail);

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
                        "       cd.birthDate     as birthDate," +
                        "       cd.img_id        as imgId " +
                        "from client c " +
                        "         left join client_detail cd on c.client = cd.client " +
                        "where c.actual = ? " +
                        "  and c.blocked = ? " +
                        "limit " + filter.limit + " offset " + filter.offset;

        List<ClientResponse> response = new ArrayList<>();

        try (Connection connection = DbUtil.getConnection(environment)) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setBoolean(1, filter.actual);
                ps.setBoolean(2, filter.blocked);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        response.add(getClientResponse(rs));
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

    @Override
    public ClientResponse getClientById(String id) throws SQLException {

        ClientResponse clientResponse = null;

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
                        "       cd.birthDate     as birthDate," +
                        "       cd.img_id        as imgId " +
                        "from client c " +
                        "         left join client_detail cd on c.client = cd.client " +
                        "where c.actual = true and c.client_id = ? ";

        try (Connection connection = DbUtil.getConnection(environment)) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        clientResponse = getClientResponse(rs);
                    }
                }
            }
        }

        return clientResponse;
    }

    @Override
    public ClientListResponse listClientById(IdListRequest idListRequest) throws SQLException {

        List<ClientResponse> response = new ArrayList<>();

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
                        "       cd.birthDate     as birthDate," +
                        "       cd.img_id        as imgId " +
                        "from client c " +
                        "         left join client_detail cd on c.client = cd.client " +
                        "where c.actual = true and c.client_id = ? ";

        try (Connection connection = DbUtil.getConnection(environment)) {

            for (String id : idListRequest.idList) {

                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            response.add(getClientResponse(rs));
                        }
                    }
                }
            }
        }

        return ClientListResponse.of(response);
    }

    @Override
    public MessageResponse editClient(EditClientRequest request, String clientId) {
        if (Strings.isNullOrEmpty(clientId)) {
            throw new RestException("SGN8209c3g :: Authorization is incorrect, please login again!");
        }

        Client client = clientDao.findClientByClientIdAndActual(clientId, true);

        if (client == null) {
            throw new RestException("14oW532k0M :: Authorization is incorrect, please login again!");
        }

        ClientDetail clientDetail = clientDetailDao.findClientDetailByClient(client.client);

        if (!Strings.isNullOrEmpty(request.email)) {
            client.email = request.email;
            clientDao.save(client);
        }

        if (!Strings.isNullOrEmpty(request.displayName)) {
            clientDetail.displayName = request.displayName;
        }

        if (!Strings.isNullOrEmpty(request.name)) {
            clientDetail.name = request.name;
        }

        if (!Strings.isNullOrEmpty(request.surname)) {
            clientDetail.surname = request.surname;
        }

        if (request.birthDate != null) {
            clientDetail.birthdate = new Timestamp(request.birthDate.getTime());
        }

        clientDetailDao.save(clientDetail);

        return MessageResponse.of("Client details successfully edited!");
    }

    @Override
    public void unblockClient(DetailRequest request) {
        clientDao.updateClientBlocked(false, null, request.id);
    }

    @Override
    public MessageResponse saveClientImage(MultipartFile file, String clientId) {
        if (Strings.isNullOrEmpty(clientId)) {
            throw new RestException("yRllKciE8L :: Authorization is incorrect, please login again!");
        }

        Client client = clientDao.findClientByClientIdAndActual(clientId, true);

        if (client == null) {
            throw new RestException("kB98jvQr1g :: Authorization is incorrect, please login again!");
        }

        FileCreateRequest fileCreateRequest = new FileCreateRequest();
        fileCreateRequest.name = file.getName();
        fileCreateRequest.type = file.getContentType();

        Base64.Encoder encoder = Base64.getEncoder();

        try {
            fileCreateRequest.content = encoder.encodeToString(file.getBytes());
        } catch (Exception exception) {
            throw new RuntimeException("H8qVU3Fzw5 :: error during encoding byte[] to base64 string");
        }

        FileIdModel fileIdModel = fileServiceClient.saveFile(fileCreateRequest);

        if (fileIdModel == null) {
            throw new RuntimeException("w6gJF1whw4 :: file was not saved!");
        }

        if (Strings.isNullOrEmpty(fileIdModel.fileId)) {
            throw new RuntimeException("Di3xi1EnKy :: fileId to save is null!");
        }

        ClientDetail clientDetail = clientDetailDao.findClientDetailByClient(client.client);
        clientDetail.imgId = fileIdModel.fileId;
        clientDetailDao.save(clientDetail);

        return MessageResponse.of("Q17DzEWR7K :: client image was successfully set!");
    }

    private ClientResponse getClientResponse(ResultSet rs) throws SQLException {
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
        clientResponse.imgId = rs.getString("imgId");
        return clientResponse;
    }

}
