package kz.sueta.eventservice.repository;

import kz.sueta.eventservice.entity.EventComplain;
import kz.sueta.eventservice.entity.id_class.EventComplainId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventComplainDao extends JpaRepository<EventComplain, EventComplainId> {
}
