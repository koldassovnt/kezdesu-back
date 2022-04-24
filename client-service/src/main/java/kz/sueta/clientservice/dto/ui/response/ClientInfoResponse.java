package kz.sueta.clientservice.dto.ui.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientInfoResponse {

    public String clientId;
    public String phone;
    public String displayName;
    public String imgId;
}
