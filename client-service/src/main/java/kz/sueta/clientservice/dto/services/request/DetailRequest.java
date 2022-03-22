package kz.sueta.clientservice.dto.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@NotNull(message = "model can not be null")
public class DetailRequest {
    @NotNull(message = "Id can not be null")
    public String id;

    public static DetailRequest of(String id) {
        return new DetailRequest(id);
    }
}
