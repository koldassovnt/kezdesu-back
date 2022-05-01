package kz.sueta.clientservice.dto.ui.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@NotNull(message = "Request body cannot be null")
public class EditClientRequest {
    public String displayName;
    public String email;
    public String name;
    public String surname;
    public Date birthDate;
}
