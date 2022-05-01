package kz.sueta.adminservice.dto.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@NotNull(message = "model can not be null")
public class CreateCategoryRequest {
    @NotNull(message = "Category label is required")
    public String categoryLabel;
    public String img;
    public String color;
}
