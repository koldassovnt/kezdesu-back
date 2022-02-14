package kz.sueta.adminservice.dto.ui.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    public String email;
    public String password;
    public String phone;
    public String displayName;
}
