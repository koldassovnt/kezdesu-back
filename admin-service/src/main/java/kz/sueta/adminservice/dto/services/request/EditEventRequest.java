package kz.sueta.adminservice.dto.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@NotNull(message = "model can not be null")
public class EditEventRequest {
    @NotNull(message = "Event id can not be null")
    public String eventId;
    public String label;
    public String description;
    public Date startedAt;
    public Date endedAt;
    public Double latitude;
    public Double longitude;
    public String categoryId;
    public String clientId;
    public String address;
}
