package kz.sueta.clientservice.dto.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileCreateRequest {
    public String content;
    public String type;
    public String name;
}
