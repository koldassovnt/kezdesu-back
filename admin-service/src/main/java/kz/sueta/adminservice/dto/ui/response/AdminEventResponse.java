package kz.sueta.adminservice.dto.ui.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminEventResponse {
    public String eventId;
    public String label;
    public String description;
    public Date startedAt;
    public Date endedAt;
    public Double latitude;
    public Double longitude;
    public String categoryId;
    public Boolean actual;
    public Boolean blocked;

    public ClientInfoResponse creatorInfo;
    public List<ClientInfoResponse> participantList = new ArrayList<>();

    public String address;
}
