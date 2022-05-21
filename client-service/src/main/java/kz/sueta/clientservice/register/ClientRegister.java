package kz.sueta.clientservice.register;

import kz.sueta.clientservice.dto.services.request.ClientBlockRequest;
import kz.sueta.clientservice.dto.services.request.ClientListFilter;
import kz.sueta.clientservice.dto.services.request.DetailRequest;
import kz.sueta.clientservice.dto.services.request.IdListRequest;
import kz.sueta.clientservice.dto.services.response.ClientListResponse;
import kz.sueta.clientservice.dto.services.response.ClientResponse;
import kz.sueta.clientservice.dto.ui.request.EditClientRequest;
import kz.sueta.clientservice.dto.ui.request.PhoneSmsRequest;
import kz.sueta.clientservice.dto.ui.response.MessageResponse;
import kz.sueta.clientservice.entity.Client;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

public interface ClientRegister {

    Client saveClient(PhoneSmsRequest phoneSmsRequest);

    Client getClient(PhoneSmsRequest phoneSmsRequest);

    ClientListResponse listClient(ClientListFilter filter);

    void blockClient(ClientBlockRequest request);

    ClientResponse getClientById(String id) throws SQLException;

    ClientListResponse listClientById(IdListRequest idListRequest) throws SQLException;

    MessageResponse editClient(EditClientRequest request, String clientId);

    void unblockClient(DetailRequest request);

    MessageResponse saveClientImage(MultipartFile file, String clientId);
}
