package kz.sueta.eventservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
//    public Boolean business; // еще нет такого
    public Boolean actual;
    public Boolean blocked;
    public String creatorId;
}
