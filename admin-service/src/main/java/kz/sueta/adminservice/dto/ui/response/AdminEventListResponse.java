package kz.sueta.adminservice.dto.ui.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminEventListResponse {
    public List<AdminEventResponse> eventResponses = new ArrayList<>();

    public static AdminEventListResponse of(List<AdminEventResponse> eventResponses) {
        return new AdminEventListResponse(eventResponses);
    }
}
