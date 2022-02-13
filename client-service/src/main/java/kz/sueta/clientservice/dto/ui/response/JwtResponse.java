package kz.sueta.clientservice.dto.ui.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtResponse {
	public String accessToken;
	public String type = "Bearer";
	public String refreshToken;
	public String clientId;
	public String phone;

	public static JwtResponse of(String accessToken, String refreshToken, String clientId, String phone) {
		JwtResponse response = new JwtResponse();
		response.accessToken = accessToken;
		response.refreshToken = refreshToken;
		response.clientId = clientId;
		response.phone = phone;
		return response;
	}
}
