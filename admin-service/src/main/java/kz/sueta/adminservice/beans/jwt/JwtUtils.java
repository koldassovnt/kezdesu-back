package kz.sueta.adminservice.beans.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import kz.sueta.adminservice.beans.services.CustomAccountDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private final Environment environment;

    @Autowired
    public JwtUtils(Environment environment) {
        this.environment = environment;
    }

    public String generateJwtToken(CustomAccountDetails userPrincipal) {
        return generateTokenFromClientId(userPrincipal.getUsername());
    }

    public String generateTokenFromClientId(String clientId) {
        return Jwts.builder()
                .setSubject(clientId)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + getAccessExpTime()))
                .signWith(SignatureAlgorithm.HS512, getJwtToken())
                .compact();
    }

    public String getAccountEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(getJwtToken()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(getJwtToken()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("LaNIyE4dpp :: Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("P0ZsW0Hpr8 :: Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("613UTTRwkj :: JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("d06LjPiW8E :: JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("eUL2s8Hkzd :: JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    private String getJwtToken() {
        String jwt = environment.getProperty("token.secret");

        if (Strings.isNullOrEmpty(jwt)) {
            throw new RuntimeException("TcIaV4UibX :: JWT token is null or empty");
        }

        return jwt;
    }

    private Long getAccessExpTime() {
        try {
          String accessExpTime = environment.getProperty("account.expiration_time");

          if (Strings.isNullOrEmpty(accessExpTime)) {
            throw new RuntimeException("C4wQ2s0Zh3 :: ACCESS TOKEN EXPIRATION TIME is null or empty");
          }

          return Long.valueOf(accessExpTime);
        } catch (Exception e) {
            throw new RuntimeException("sDq6F3HnEY :: Exception while getting ACCESS TOKEN EXPIRATION TIME");
        }
    }

}
