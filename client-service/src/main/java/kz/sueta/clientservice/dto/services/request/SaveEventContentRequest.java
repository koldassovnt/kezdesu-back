package kz.sueta.clientservice.dto.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@NotNull(message = "model can not be null")
public class SaveEventContentRequest {

    @NotNull(message = "eventId can not be null")
    public String eventId;

    @NotNull(message = "contentId can not be null")
    public String contentId;

    public static SaveEventContentRequest of(String eventId, String contentId) {
        return new SaveEventContentRequest(eventId, contentId);
    }
}
