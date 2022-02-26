package kz.sueta.eventservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileIdModel {
    public String fileId;

    public static FileIdModel of(String fileId) {
        return new FileIdModel(fileId);
    }
}
