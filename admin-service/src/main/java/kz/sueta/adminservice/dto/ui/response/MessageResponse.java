package kz.sueta.adminservice.dto.ui.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageResponse {
	public String message;

	public static MessageResponse of(String message) {
		return new MessageResponse(message);
	}
}
