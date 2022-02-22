package kz.sueta.eventservice.repository;

import kz.sueta.eventservice.entity.EventCreator;
import kz.sueta.eventservice.entity.id_class.EventCreatorId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventCreatorDao extends CrudRepository<EventCreator, EventCreatorId> {
}
