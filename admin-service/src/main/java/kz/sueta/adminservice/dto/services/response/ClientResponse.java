package kz.sueta.adminservice.dto.services.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientResponse {
    public String clientId;
    public String phone;
    public String email;
    public Boolean actual;
    public Boolean blocked;
    public String blockedReason;
    public String displayName;
    public String name;
    public String surname;
    public Date birthDate;
}
