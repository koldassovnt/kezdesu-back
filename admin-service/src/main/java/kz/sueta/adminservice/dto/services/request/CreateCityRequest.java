package kz.sueta.adminservice.dto.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@NotNull(message = "model can not be null")
public class CreateCityRequest {
    @NotNull(message = "City label is required")
    public String cityLabel;
    @NotNull(message = "City latitude is required")
    public Double latitude;
    @NotNull(message = "City longitude is required")
    public Double longitude;
}
