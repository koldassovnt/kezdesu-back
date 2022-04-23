package kz.sueta.eventservice.entity.id_class;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventCreatorId implements Serializable {
    public String eventId;
    public String clientId;

    public static EventCreatorId of(String eventId, String clientId) {
        return new EventCreatorId(eventId, clientId);
    }
}
