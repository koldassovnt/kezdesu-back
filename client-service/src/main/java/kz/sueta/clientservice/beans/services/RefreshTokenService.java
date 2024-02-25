package kz.sueta.clientservice.beans.services;

import com.google.common.base.Strings;
import kz.sueta.clientservice.entity.Client;
import kz.sueta.clientservice.entity.RefreshToken;
import kz.sueta.clientservice.exception.ui.TokenRefreshException;
import kz.sueta.clientservice.repository.ClientDao;
import kz.sueta.clientservice.repository.RefreshTokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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
            throw new RuntimeException("Fj8R68f9uU :: Client not found with clientId: " + clientId);
        }

        refreshToken.setClientId(client.clientId);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() + gerRefreshToken());
        refreshToken.setExpiredAt(timestamp);
        refreshToken.setToken(UUID.randomUUID().toString());

        checkIfTokenExistsByClientId(clientId);
        refreshToken = refreshTokenDao.save(refreshToken);

        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiredAt().before(new Timestamp(System.currentTimeMillis()))) {
            refreshTokenDao.delete(token);
            throw new TokenRefreshException(token.getToken(),
                    "D8kPWTysMU :: Refresh token was expired. Please make a new request");
        }

        return token;
    }

    private void checkIfTokenExistsByClientId(String clientId) {
        RefreshToken existedRefreshToken = refreshTokenDao.findByClientId(clientId);

        if (existedRefreshToken != null) {
            refreshTokenDao.delete(existedRefreshToken);
        }
    }

    @Transactional
    public int deleteByClientId(String clientId) {

        Client client = clientDao.findClientByClientIdAndActual(clientId, true);

        if (client == null) {
            throw new RuntimeException("yBgJQa12t6 :: Client not found with clientId: " + clientId);
        }

        return refreshTokenDao.deleteByClientId(client.clientId);
    }

    private Long gerRefreshToken() {
        try {
            String refreshExpTime = environment.getProperty("refreshtoken.expiration_time");

            if (Strings.isNullOrEmpty(refreshExpTime)) {
                throw new RuntimeException("c303XUgNox :: REFRESH TOKEN EXPIRATION TIME is null or empty");
            }

            return Long.valueOf(refreshExpTime);
        } catch (Exception e) {
            throw new RuntimeException("A6o3mwitd5 :: Exception while getting REFRESH TOKEN EXPIRATION TIME");
        }
    }
}
