package kz.sueta.eventservice.repository;

import kz.sueta.eventservice.entity.Complain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplainDao extends JpaRepository<Complain, Long> {
}
