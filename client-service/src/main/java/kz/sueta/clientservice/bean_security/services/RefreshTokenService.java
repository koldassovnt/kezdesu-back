package kz.sueta.clientservice.bean_security.services;

import com.google.common.base.Strings;
import kz.sueta.clientservice.entity.Client;
import kz.sueta.clientservice.entity.RefreshToken;
import kz.sueta.clientservice.exceptions.server.TokenRefreshException;
import kz.sueta.clientservice.repository.ClientDao;
import kz.sueta.clientservice.repository.RefreshTokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final Environment environment;
    private final RefreshTokenDao refreshTokenDao;
    private final ClientDao clientDao;

    @Autowired
    public RefreshTokenService(Environment environment, RefreshTokenDao refreshTokenDao, ClientDao clientDao) {
        this.environment = environment;
        this.refreshTokenDao = refreshTokenDao;
        this.clientDao = clientDao;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenDao.findByToken(token);
    }

    public RefreshToken createRefreshToken(String clientId) {

        RefreshToken refreshToken = new RefreshToken();

        Client client = clientDao.findClientByClientIdAndActual(clientId, true);

        if (client == null) {
            throw new RuntimeException("Client not found with clientId: " + clientId);
        }

        refreshToken.setClientId(client.clientId);
        refreshToken.setExpiryDate(Instant.now().plusMillis(gerRefreshToken()));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenDao.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenDao.delete(token);
            throw new TokenRefreshException(token.getToken(),
                    "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByClientId(String clientId) {

      Client client = clientDao.findClientByClientIdAndActual(clientId, true);

      if (client == null) {
        throw new RuntimeException("Client not found with clientId: " + clientId);
      }

        return refreshTokenDao.deleteByClientId(client.clientId);
    }

    private Long gerRefreshToken() {
      try {
        String refreshExpTime = environment.getProperty("refreshtoken.expiration_time");

        if (Strings.isNullOrEmpty(refreshExpTime)) {
          throw new RuntimeException("REFRESH TOKEN EXPIRATION TIME is null or empty");
        }

        return Long.valueOf(refreshExpTime);
      } catch (Exception e) {
        throw new RuntimeException("Exception while getting REFRESH TOKEN EXPIRATION TIME");
      }
    }
}
