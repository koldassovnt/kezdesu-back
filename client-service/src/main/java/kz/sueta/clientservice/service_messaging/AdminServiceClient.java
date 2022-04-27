package kz.sueta.clientservice.service_messaging;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import kz.sueta.clientservice.dto.services.response.AdminDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "admin-ws")
public interface AdminServiceClient {

    Logger log = LoggerFactory.getLogger(AdminServiceClient.class);

    @GetMapping("/admin/forClient/get-account")
    @Retry(name = "admin-ws")
    @CircuitBreaker(name = "admin-ws", fallbackMethod = "detailAdminFallback")
    AdminDetail getAdminDetail(@RequestParam String id);

    default AdminDetail detailAdminFallback(String id, Throwable throwable) {
        log.info("RequestParam = " + id);
        log.info("Exception class=" + throwable.getClass().getName());
        log.info("Exception took place: " + throwable.getMessage());
        return new AdminDetail();
    }
}
