package kz.sueta.eventservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventResponse {
    public String eventId;
    public String label;
    public String description;
    public Date startedAt;
    public Date endedAt;
    public Double latitude;
    public Double longitude;
    public String categoryId;
    public Boolean business;
    public Boolean actual;
    public Boolean blocked;
    public String creatorId;
    public List<MultipartFile> images; // jpeg, png
    public List<MultipartFile> videos; // mp4
}
