package kz.sueta.eventservice.repository;

import kz.sueta.eventservice.entity.Event;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface EventDao extends CrudRepository<Event, Long> {

    @Modifying
    @Query("update Event e set e.actual = :actual where e.eventId = :eventId")
    void updateEventActual(@Param("actual") Boolean actual, @Param("eventId") String eventId);

    @Modifying
    @Query("update Event e set e.blocked = :blocked where e.eventId = :eventId")
    void updateEventBlocked(@Param("blocked") Boolean blocked, @Param("eventId") String eventId);

    Event findEventByEventIdAndActual(String eventId, Boolean actual);
}
