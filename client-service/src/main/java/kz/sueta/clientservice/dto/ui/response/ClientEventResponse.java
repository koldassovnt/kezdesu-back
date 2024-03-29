package kz.sueta.clientservice.dto.ui.response;

import kz.sueta.clientservice.dto.services.response.ContentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientEventResponse {
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
    public List<ContentResponse> contentList = new ArrayList<>();
}
