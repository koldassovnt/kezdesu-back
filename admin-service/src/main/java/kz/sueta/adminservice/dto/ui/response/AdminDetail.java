package kz.sueta.adminservice.dto.ui.response;

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

    public static AdminDetail of(String adminId, String displayName, String phone) {
        return new AdminDetail(adminId, displayName, phone);
    }
}
