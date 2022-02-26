package kz.sueta.eventservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventListResponse {
    public List<EventResponse> events = new ArrayList<>();

    public static EventListResponse of(List<EventResponse> events) {
        return new EventListResponse(events);
    }
}
