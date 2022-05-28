package kz.sueta.adminservice.dto.services.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminComplainResponse {
    public String complainId;
    public String complainText;

    public String clientId;
    public String clientPhone;

    public String eventId;
    public String eventName;
}
