package kz.sueta.clientservice.dto.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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
    public List<MultipartFile> images; // jpeg, png
    public List<MultipartFile> videos; // mp4

    public String address;
}
