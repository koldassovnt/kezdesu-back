package kz.sueta.eventservice.repository;

import kz.sueta.eventservice.entity.EventParticipant;
import kz.sueta.eventservice.entity.id_class.EventParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventParticipantDao extends JpaRepository<EventParticipant, EventParticipantId> {
}
