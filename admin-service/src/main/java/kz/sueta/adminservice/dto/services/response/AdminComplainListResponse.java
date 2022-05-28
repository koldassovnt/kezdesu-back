package kz.sueta.adminservice.dto.services.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminComplainListResponse {
    public List<AdminComplainResponse> detailResponses = new ArrayList<>();

    public static AdminComplainListResponse of(List<AdminComplainResponse> detailResponses) {
        return new AdminComplainListResponse(detailResponses);
    }
}
