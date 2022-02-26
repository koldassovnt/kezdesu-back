package kz.sueta.eventservice.dto.request;

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
    public Double latitude;
    public Double longitude;
}
