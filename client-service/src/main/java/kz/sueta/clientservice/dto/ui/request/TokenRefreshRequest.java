package kz.sueta.clientservice.dto.ui.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenRefreshRequest {
    public String refreshToken;
}
