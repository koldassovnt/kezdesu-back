package kz.sueta.eventservice.entity;

import kz.sueta.eventservice.entity.id_class.EventComplainId;
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
@IdClass(EventComplainId.class)
public class EventComplain {
    @Id
    @Column(name = "event_id")
    public String eventId;
    @Id
    @Column(name = "complain_id")
    public String complainId;
}
