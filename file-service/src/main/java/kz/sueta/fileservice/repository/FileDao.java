package kz.sueta.fileservice.repository;

import kz.sueta.fileservice.entity.File;
import org.springframework.data.repository.CrudRepository;

public interface FileDao extends CrudRepository<File, Long> {

    File findFileByFileId(String fileId);
}
