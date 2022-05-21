package kz.sueta.eventservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileCreateRequest {
    public byte[] byteArr;
    public String type;

    public static FileCreateRequest of(byte[] byteArr, String type) {
        return new FileCreateRequest(byteArr, type);
    }
}
