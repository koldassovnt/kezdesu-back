package kz.sueta.clientservice.dto.ui.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageResponse {
	public String message;
	public String id;

	public static MessageResponse of(String message) {
		return new MessageResponse(message, null);
	}

	public static MessageResponse of(String message, String id) {
		return new MessageResponse(message, id);
	}
}
