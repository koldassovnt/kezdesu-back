package kz.sueta.clientservice.dto.ui.response;

import lombok.Data;

@Data
public class TokenRefreshResponse {
  public String accessToken;
  public String refreshToken;
  public String tokenType = "Bearer";

  public TokenRefreshResponse(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

}
