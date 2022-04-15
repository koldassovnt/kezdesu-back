package kz.sueta.adminservice.dto.ui.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NotNull(message = "Request body cannot be null")
public class ResetPasswordRequest {

    @NotNull(message = "Old password cannot be null")
    public String oldPassword;

    @NotNull(message = "New password cannot be null")
    public String newPassword;
}
