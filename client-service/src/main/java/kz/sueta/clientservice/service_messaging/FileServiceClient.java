package kz.sueta.clientservice.service_messaging;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import kz.sueta.clientservice.dto.services.request.FileCreateRequest;
import kz.sueta.clientservice.dto.services.response.FileIdModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "file-ws")
public interface FileServiceClient {

    Logger log = LoggerFactory.getLogger(FileServiceClient.class);

    @PostMapping(value = "/file/save-file")
    @Retry(name = "file-ws")
    @CircuitBreaker(name = "file-ws", fallbackMethod = "saveFileFallback")
    FileIdModel saveFile(@RequestBody FileCreateRequest request);

    default FileIdModel saveFileFallback(FileCreateRequest request,
                                         Throwable throwable) {
        log.info("Request body=" + request);
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
