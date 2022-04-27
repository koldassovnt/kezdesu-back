package kz.sueta.clientservice.dto.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientEventRequest {

    public String clientId;
    public String eventId;

    public static ClientEventRequest of(String clientId, String eventId) {
        return new ClientEventRequest(clientId, eventId);
    }
}
