package kz.sueta.eventservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@NotNull(message = "model can not be null")
public class ClientEventRequest {

    @NotNull(message = "clientId ca not be null")
    public String clientId;
    @NotNull(message = "eventId ca not be null")
    public String eventId;
}
