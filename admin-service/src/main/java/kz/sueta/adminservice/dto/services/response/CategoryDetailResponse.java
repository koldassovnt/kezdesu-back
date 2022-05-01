package kz.sueta.adminservice.dto.services.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDetailResponse {
    public String categoryId;
    public String label;
    public Boolean actual;
    public String img;
    public String color;
}
