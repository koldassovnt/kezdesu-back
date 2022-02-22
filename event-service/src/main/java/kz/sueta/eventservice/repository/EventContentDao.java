package kz.sueta.eventservice.repository;

import kz.sueta.eventservice.entity.EventContent;
import kz.sueta.eventservice.entity.id_class.EventContentId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventContentDao extends CrudRepository<EventContent, EventContentId> {
}
