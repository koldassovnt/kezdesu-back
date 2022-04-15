package kz.sueta.adminservice.dto.services.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CityListResponse {
    public List<CityDetailResponse> detailResponses = new ArrayList<>();

    public static CityListResponse of(List<CityDetailResponse> detailResponses) {
        return new CityListResponse(detailResponses);
    }
}
