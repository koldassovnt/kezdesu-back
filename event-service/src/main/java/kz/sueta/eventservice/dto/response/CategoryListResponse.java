package kz.sueta.eventservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryListResponse {
    public List<CategoryDetailResponse> detailResponses = new ArrayList<>();

    public static CategoryListResponse of(List<CategoryDetailResponse> detailResponses) {
        return new CategoryListResponse(detailResponses);
    }
}
