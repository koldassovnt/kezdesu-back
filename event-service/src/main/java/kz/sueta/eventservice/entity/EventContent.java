package kz.sueta.eventservice.entity;

import kz.sueta.eventservice.entity.id_class.EventContentId;
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
@IdClass(EventContentId.class)
public class EventContent {
    @Id
    @Column(name = "event_id")
    public String eventId;
    @Id
    @Column(name = "file_id")
    public String fileId;
}
