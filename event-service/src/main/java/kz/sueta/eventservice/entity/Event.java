package kz.sueta.eventservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long event;
    @Column(name = "event_id")
    public String eventId;
    public String label;
    public String description;
    @Column(name = "started_at")
    public Timestamp startedAt;
    @Column(name = "ended_at")
    public Timestamp endedAt;
    public Double latitude;
    public Double longitude;
    @Column(name = "category_id")
    public String categoryId;
    public Boolean business;
    public Boolean actual;
    public Boolean blocked;
    @Column(name = "created_at")
    public Timestamp createdAt;
}
