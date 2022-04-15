package kz.sueta.adminservice.dto.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@NotNull(message = "model can not be null")
public class EditCityRequest {
    @NotNull(message = "City Id is required")
    public String cityId;
    public String cityLabel;
    public Double latitude;
    public Double longitude;
}
