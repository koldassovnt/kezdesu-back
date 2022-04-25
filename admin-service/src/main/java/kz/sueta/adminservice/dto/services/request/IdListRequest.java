package kz.sueta.adminservice.dto.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class IdListRequest {
    public List<String> idList = new ArrayList<>();

    public static IdListRequest of(List<String> idList) {
        return new IdListRequest(idList);
    }
}
