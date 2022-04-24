package kz.sueta.clientservice.dto.ui.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientEventListResponse {
    public List<ClientEventResponse> eventResponses = new ArrayList<>();

    public static ClientEventListResponse of(List<ClientEventResponse> eventResponses) {
        return new ClientEventListResponse(eventResponses);
    }
}
