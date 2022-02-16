package kz.sueta.adminservice.exception.ui;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public TokenRefreshException(String token, String message) {
    super(String.format("sNFAzzD0X6 :: Failed for [%s]: %s", token, message));
  }
}
