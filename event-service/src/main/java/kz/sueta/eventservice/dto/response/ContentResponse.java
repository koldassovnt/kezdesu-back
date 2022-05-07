package kz.sueta.eventservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContentResponse {
    public String contentId;
    public String contentType;
}
