package kz.sueta.clientservice.repository;

import kz.sueta.clientservice.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDao extends CrudRepository<Client, Long> {

    Client findClientByPhoneAndActual(String phoneNumber, Boolean actual);

    Client findClientByClientIdAndActual(String clientId, Boolean actual);
}
