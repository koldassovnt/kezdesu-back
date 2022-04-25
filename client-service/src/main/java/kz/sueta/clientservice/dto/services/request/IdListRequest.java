package kz.sueta.clientservice.dto.services.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NotNull(message = "Request body cannot be null")
public class IdListRequest {
    public List<String> idList = new ArrayList<>();
}
