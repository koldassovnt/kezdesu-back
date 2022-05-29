package kz.sueta.fileservice.register.impl;

import com.google.common.base.Strings;
import kz.sueta.fileservice.dto.*;
import kz.sueta.fileservice.entity.File;
import kz.sueta.fileservice.exception.FileGetException;
import kz.sueta.fileservice.exception.FileSaveException;
import kz.sueta.fileservice.register.FileRegister;
import kz.sueta.fileservice.repository.FileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Component
public class FileRegisterImpl implements FileRegister {

    private final FileDao fileDao;

    @Autowired
    public FileRegisterImpl(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @Override
    public FileIdModel saveFile(FileCreateRequest request) {

        if (request.content == null) {
            throw new FileSaveException("C45oJd9tGo :: multipartFile is null");
        }

        if (Objects.equals(request.type, "image/png")
                || Objects.equals(request.type, "video/mp4")) {

            File file = new File();
            file.content = request.content;
            file.fileId = UUID.randomUUID().toString();
            file.mimeType = request.type;
            file.label = request.name;

            fileDao.save(file);

            return FileIdModel.of(file.fileId);

        } else {
            throw new FileSaveException("8VoDtNlCj4 :: file type is not appropriate!");
        }
    }

    @Override
    public FileListResponse getFiles(FileListRequest fileListRequest) {

        if (fileListRequest.fileIds == null || fileListRequest.fileIds.isEmpty()) {
            throw new FileGetException("fDuUTx2Ai5 :: fileId list is null or empty");
        }

        FileListResponse listResponse = new FileListResponse();

        for (String fileId : fileListRequest.fileIds) {

            File file = fileDao.findFileByFileId(fileId);

            if (file != null) {
                FileDetailResponse response = new FileDetailResponse();
                response.fileId = file.fileId;
                response.type = file.mimeType;
                response.base64Content = file.content;
                listResponse.addFileDetail(response);
            }
        }

        return listResponse;
    }

    @Override
    public String getFileType(String id) {
        if (Strings.isNullOrEmpty(id)) {
            throw new RuntimeException("DhIrd3lmEM :: file id is null or empty");
        }

        File file = fileDao.findFileByFileId(id);

        if (file == null) {
            throw new RuntimeException("T0m7FFcREg :: file by id = " + id + " is null");
        }

        return file.mimeType;
    }

    @Override
    public String getFileById(String id) {
        if (Strings.isNullOrEmpty(id)) {
            throw new RuntimeException("dvPN5QZpw4 :: file id is null or empty");
        }

        File file = fileDao.findFileByFileId(id);

        if (file == null) {
            throw new RuntimeException("80STv2pUaq :: file by id = " + id + " is null");
        }

        return file.content;
    }
}
