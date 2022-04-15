package kz.sueta.adminservice.dto.services.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CityDetailResponse {
    public String cityId;
    public String label;
    public Boolean actual;
    public Double latitude;
    public Double longitude;
}
