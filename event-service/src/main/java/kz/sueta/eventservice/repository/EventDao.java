package kz.sueta.eventservice.repository;

import kz.sueta.eventservice.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDao extends JpaRepository<Event, Long> {

    Event findEventByEventIdAndActual(String eventId, Boolean actual);

    Event findEventByEventId(String eventId);
}
