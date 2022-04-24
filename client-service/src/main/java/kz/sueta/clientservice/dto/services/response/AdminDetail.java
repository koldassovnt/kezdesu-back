package kz.sueta.clientservice.dto.services.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminDetail {

    public String adminId;
    public String displayName;
    public String phone;

}
