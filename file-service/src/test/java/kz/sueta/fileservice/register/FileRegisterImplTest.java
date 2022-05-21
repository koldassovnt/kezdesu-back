package kz.sueta.fileservice.register;

import kz.greetgo.util.RND;
import kz.sueta.fileservice.repository.FileDao;
import kz.sueta.fileservice.dto.FileIdModel;
import kz.sueta.fileservice.dto.FileListRequest;
import kz.sueta.fileservice.dto.FileListResponse;
import kz.sueta.fileservice.entity.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileRegisterImplTest {

    @Autowired
    private FileRegister fileRegister;

    @Autowired
    private FileDao fileDao;

    @Test
    public void saveFile() {
        fileDao.deleteAll();

        MultipartFile multipartFile = new MockMultipartFile
                ("file", RND.strEng(5), "image/jpeg", RND.byteArray(10));

        //
        //
        FileIdModel fileIdModel = fileRegister.saveFile(multipartFile);
        //
        //

        Assertions.assertNotNull(fileIdModel);
        Assertions.assertNotNull(fileIdModel.fileId);
    }

    @Test
    public void getFiles() {
        fileDao.deleteAll();

        File file = new File();
        file.mimeType = "image/png";
        file.fileId = RND.strInt(20);
        file.label = RND.strEng(10);
        file.content = Base64.getEncoder().encodeToString(RND.byteArray(10));
        fileDao.save(file);

        List<String> fileIds = new ArrayList<>();
        fileIds.add(file.fileId);

        FileListRequest listRequest = new FileListRequest();
        listRequest.fileIds = fileIds;

        //
        //
        FileListResponse response = fileRegister.getFiles(listRequest);
        //
        //

        Assertions.assertEquals(response.fileDetailResponses.size(), 1);
        Assertions.assertEquals(response.fileDetailResponses.get(0).fileId, file.fileId);
        Assertions.assertEquals(response.fileDetailResponses.get(0).base64Content, file.content);
        Assertions.assertEquals(response.fileDetailResponses.get(0).type, file.mimeType);
    }
}
