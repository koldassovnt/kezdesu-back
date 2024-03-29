package kz.sueta.fileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@NotNull(message = "Request body cannot be null")
public class FileCreateRequest {
    public String content;
    public String type;
    public String name;
}
