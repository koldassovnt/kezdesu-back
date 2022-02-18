package kz.sueta.clientservice.dto.ui.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@NotNull(message = "Request body cannot be null")
public class TokenRefreshRequest {
    @NotNull(message = "RefreshToken must be presented")
    public String refreshToken;
}
