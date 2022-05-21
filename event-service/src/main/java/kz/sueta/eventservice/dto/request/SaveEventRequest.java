package kz.sueta.eventservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@NotNull(message = "model can not be null")
public class SaveEventRequest {
    @NotNull(message = "Label is required")
    public String label;
    public String description;
    @NotNull(message = "Event started time is required")
    public Date startedAt;
    @NotNull(message = "Event ended time is required")
    public Date endedAt;
    @NotNull(message = "Latitude is required")
    public Double latitude;
    @NotNull(message = "Longitude is required")
    public Double longitude;
    public String categoryId;
    @NotNull(message = "Creator id is required")
    public String creatorId;
    public String address;
}
