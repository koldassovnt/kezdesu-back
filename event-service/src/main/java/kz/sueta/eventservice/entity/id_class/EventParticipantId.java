package kz.sueta.eventservice.entity.id_class;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventParticipantId implements Serializable {
    public String eventId;
    public String clientId;

    public static EventParticipantId of(String eventId, String clientId) {
        return new EventParticipantId(eventId, clientId);
    }
}
