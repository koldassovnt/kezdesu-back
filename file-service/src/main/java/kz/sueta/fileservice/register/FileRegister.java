package kz.sueta.fileservice.register;

import kz.sueta.fileservice.dto.FileIdModel;
import kz.sueta.fileservice.dto.FileListRequest;
import kz.sueta.fileservice.dto.FileListResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileRegister {

    FileIdModel saveFile(MultipartFile file);

    FileListResponse getFiles(FileListRequest fileListRequest);

    String getFileType(String id);
}
