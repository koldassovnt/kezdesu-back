package kz.sueta.clientservice.repository;

import kz.sueta.clientservice.entity.RefreshToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenDao extends CrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByClientId(String clientId);

    RefreshToken findByClientId(String clientId);
}
