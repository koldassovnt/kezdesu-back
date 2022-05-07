package kz.sueta.fileservice.register;

import kz.sueta.fileservice.dto.FileCreateRequest;
import kz.sueta.fileservice.dto.FileIdModel;
import kz.sueta.fileservice.dto.FileListRequest;
import kz.sueta.fileservice.dto.FileListResponse;

public interface FileRegister {

    FileIdModel saveFile(FileCreateRequest fileCreateRequest);

    FileListResponse getFiles(FileListRequest fileListRequest);

    String getFileType(String id);
}
