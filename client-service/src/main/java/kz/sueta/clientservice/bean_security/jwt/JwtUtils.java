package kz.sueta.clientservice.bean_security.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import kz.sueta.clientservice.bean_security.services.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    public String generateJwtToken(CustomUserDetails userPrincipal) {
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

    public String getClientIdFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(getJwtToken()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(getJwtToken()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    private String getJwtToken() {
        String jwt = environment.getProperty("token.secret");

        if (Strings.isNullOrEmpty(jwt)) {
            throw new RuntimeException("JWT token is null or empty");
        }

        return jwt;
    }

    private Long getAccessExpTime() {
        try {
          String accessExpTime = environment.getProperty("acessetoken.expiration_time");

          if (Strings.isNullOrEmpty(accessExpTime)) {
            throw new RuntimeException("ACCESS TOKEN EXPIRATION TIME is null or empty");
          }

          return Long.valueOf(accessExpTime);
        } catch (Exception e) {
            throw new RuntimeException("Exception while getting ACCESS TOKEN EXPIRATION TIME");
        }
    }

}
