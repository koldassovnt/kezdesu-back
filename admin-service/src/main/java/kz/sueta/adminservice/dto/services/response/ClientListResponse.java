package kz.sueta.adminservice.dto.services.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientListResponse {
    public List<ClientResponse> clients = new ArrayList<>();

    public static ClientListResponse of(List<ClientResponse> clients) {
        return new ClientListResponse(clients);
    }
}
