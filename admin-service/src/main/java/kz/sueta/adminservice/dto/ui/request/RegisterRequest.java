package kz.sueta.adminservice.dto.ui.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NotNull(message = "Request body cannot be null")
public class RegisterRequest {

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email must be well-formed")
    public String email;

    @NotNull(message = "Password cannot be null")
    public String password;

    public String phone;
    public String displayName;
}
