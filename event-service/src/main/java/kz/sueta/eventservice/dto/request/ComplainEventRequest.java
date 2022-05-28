package kz.sueta.eventservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@NotNull(message = "model can not be null")
public class ComplainEventRequest {

    @NotNull(message = "client id must not be null")
    public String clientId;
    @NotNull(message = "event id must not be null")
    public String eventId;
    @NotNull(message = "complain must have text")
    public String complainText;
}
