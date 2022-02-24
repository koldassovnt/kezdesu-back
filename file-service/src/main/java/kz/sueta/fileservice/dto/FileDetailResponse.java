package kz.sueta.fileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileDetailResponse {
    public String fileId;
    public byte[] fileByteArray;
    public String label;
    public String type;
}
