package kz.sueta.eventservice.repository;

import kz.sueta.eventservice.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDao extends CrudRepository<Event, Long> {
}
