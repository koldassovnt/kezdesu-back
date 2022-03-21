package kz.sueta.clientservice.dto.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@NotNull(message = "model can not be null")
public class ClientBlockRequest {
    @NotNull(message = "Id can not be null")
    public String id;
    public String blockReason;

    public static ClientBlockRequest of(String id) {
        ClientBlockRequest request = new ClientBlockRequest();
        request.id = id;
        return request;
    }

    public static ClientBlockRequest of(String id, String blockReason) {
        return new ClientBlockRequest(id, blockReason);
    }

}
