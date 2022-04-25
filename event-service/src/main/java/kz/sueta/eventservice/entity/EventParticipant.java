package kz.sueta.eventservice.entity;

import kz.sueta.eventservice.entity.id_class.EventParticipantId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@IdClass(EventParticipantId.class)
public class EventParticipant {
    @Id
    @Column(name = "event_id")
    public String eventId;
    @Id
    @Column(name = "client_id")
    public String clientId;
    public Boolean approved;
}
