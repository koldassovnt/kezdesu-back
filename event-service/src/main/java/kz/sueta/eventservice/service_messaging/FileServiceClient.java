package kz.sueta.eventservice.service_messaging;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static kz.sueta.eventservice.util.ServiceFallBackStatic.FILE_TYPE_ERROR;


@FeignClient(name = "file-ws")
public interface FileServiceClient {

    Logger log = LoggerFactory.getLogger(FileServiceClient.class);

    @GetMapping("/file/get-file-type")
    @Retry(name = "file-ws")
    @CircuitBreaker(name = "file-ws", fallbackMethod = "getFileTypeFallback")
    String getFileType(@RequestParam String id);

    default String getFileTypeFallback(String id, Throwable throwable) {
        log.info("RequestBody = " + id);
        log.info("Exception class=" + throwable.getClass().getName());
        log.info("Exception took place: " + throwable.getMessage());
        return FILE_TYPE_ERROR;
    }
}
