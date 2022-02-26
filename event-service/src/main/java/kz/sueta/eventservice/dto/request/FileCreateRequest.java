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
    public MultipartFile file;

    public static FileCreateRequest of(MultipartFile file) {
        return new FileCreateRequest(file);
    }
}
