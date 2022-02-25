package kz.sueta.fileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileDetailResponse {
    public String fileId;
    public String base64Content;
    public String type;
}
