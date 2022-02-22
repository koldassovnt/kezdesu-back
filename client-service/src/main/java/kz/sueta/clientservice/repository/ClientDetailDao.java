package kz.sueta.clientservice.repository;

import kz.sueta.clientservice.entity.ClientDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDetailDao extends CrudRepository<ClientDetail, Long> {
}
