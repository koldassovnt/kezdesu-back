package kz.sueta.fileservice.register.impl;

import com.google.common.base.Strings;
import kz.sueta.fileservice.repository.FileDao;
import kz.sueta.fileservice.dto.*;
import kz.sueta.fileservice.entity.File;
import kz.sueta.fileservice.exception.FileGetException;
import kz.sueta.fileservice.exception.FileSaveException;
import kz.sueta.fileservice.register.FileRegister;
import kz.sueta.fileservice.util.FileStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
    public FileIdModel saveFile(FileCreateRequest fileCreateRequest) {

        if (fileCreateRequest.file == null) {
            throw new FileSaveException("oahBIHVyOj :: request model received as null");
        }

        if (fileCreateRequest.file.isEmpty()) {
            throw new FileSaveException("C45oJd9tGo :: byte array is null");
        }

        if (Objects.equals(fileCreateRequest.file.getContentType(), "image/jpeg")
                || Objects.equals(fileCreateRequest.file.getContentType(), "image/png")
                || Objects.equals(fileCreateRequest.file.getContentType(), "video/mp4")) {

            Base64.Encoder encoder = Base64.getEncoder();
            File file = new File();

            try {
                 file.content = encoder.encodeToString(fileCreateRequest.file.getBytes());
            } catch (IOException exception) {
                throw new RuntimeException("H8qVU3Fzw5 :: error during encoding byte[] to base64 string");
            }

            file.fileId = UUID.randomUUID().toString();
            file.mimeType = fileCreateRequest.file.getContentType();

            if (Strings.isNullOrEmpty(fileCreateRequest.file.getName())) {
                file.label = FileStatic.UNNAMED_NAME;
            } else {
                file.label = fileCreateRequest.file.getName();
            }

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
}
