package kz.sueta.clientservice.service_messaging;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import kz.sueta.clientservice.dto.services.response.FileIdModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@FeignClient(name = "file-ws")
public interface FileServiceClient {

    Logger log = LoggerFactory.getLogger(FileServiceClient.class);

    @GetMapping(value = "/file/save-file", consumes = {"multipart/form-data"})
    @Retry(name = "file-ws")
    @CircuitBreaker(name = "file-ws", fallbackMethod = "saveFileFallback")
    FileIdModel saveFile(@RequestParam("file") MultipartFile file);

    default FileIdModel saveFileFallback(MultipartFile file,
                                         Throwable throwable) {
        log.info("Exception class=" + throwable.getClass().getName());
        log.info("Exception took place: " + throwable.getMessage());
        return null;
    }

    @GetMapping(value = "/file/get-file")
    @Retry(name = "file-ws")
    @CircuitBreaker(name = "file-ws", fallbackMethod = "getFileByIdFallback")
    String getFileById(@RequestParam("id") String id);

    default String getFileByIdFallback(String id, Throwable throwable) {
        log.info("RequestParam=" + id);
        log.info("Exception class=" + throwable.getClass().getName());
        log.info("Exception took place: " + throwable.getMessage());
        return null;
    }
}
