package kz.sueta.eventservice.repository;

import kz.sueta.eventservice.entity.EventContent;
import kz.sueta.eventservice.entity.id_class.EventContentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventContentDao extends JpaRepository<EventContent, EventContentId> {
}
