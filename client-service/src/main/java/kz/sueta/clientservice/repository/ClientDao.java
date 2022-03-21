package kz.sueta.clientservice.repository;

import kz.sueta.clientservice.entity.Client;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ClientDao extends CrudRepository<Client, Long> {

    Client findClientByPhoneAndActual(String phoneNumber, Boolean actual);

    Client findClientByClientIdAndActual(String clientId, Boolean actual);

    @Modifying
    @Query("update Client c set c.blocked = :blocked, c.blockedReason = :blockedReason where c.clientId = :clientId")
    void updateClientBlocked(@Param("blocked") Boolean blocked,
                            @Param("blockedReason") String blockedReason,
                            @Param("clientId") String clientId);
}
