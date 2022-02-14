package kz.sueta.adminservice.dto.ui.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtResponse {
	public String accessToken;
	public String type = "Bearer";
	public String email;
	public String displayName;

	public static JwtResponse of(String accessToken, String email, String displayName) {
		JwtResponse response = new JwtResponse();
		response.accessToken = accessToken;
		response.email = email;
		response.displayName = displayName;
		return response;
	}
}
