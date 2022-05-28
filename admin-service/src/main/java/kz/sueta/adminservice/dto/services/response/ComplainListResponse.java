package kz.sueta.adminservice.dto.services.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ComplainListResponse {
    public List<ComplainResponse> detailResponses = new ArrayList<>();

    public static ComplainListResponse of(List<ComplainResponse> detailResponses) {
        return new ComplainListResponse(detailResponses);
    }
}
