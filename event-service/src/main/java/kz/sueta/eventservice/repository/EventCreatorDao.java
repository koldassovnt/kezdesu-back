package kz.sueta.eventservice.repository;

import kz.sueta.eventservice.entity.EventCreator;
import kz.sueta.eventservice.entity.id_class.EventCreatorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventCreatorDao extends JpaRepository<EventCreator, EventCreatorId> {
}
