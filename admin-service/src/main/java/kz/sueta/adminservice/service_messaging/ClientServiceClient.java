package kz.sueta.adminservice.service_messaging;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import kz.sueta.adminservice.dto.services.request.ClientBlockRequest;
import kz.sueta.adminservice.dto.services.request.ClientListFilter;
import kz.sueta.adminservice.dto.services.response.ClientListResponse;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static kz.sueta.adminservice.util.ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE;

@FeignClient(name = "client-ws")
public interface ClientServiceClient {

    Logger log = LoggerFactory.getLogger(ClientServiceClient.class);

    @PostMapping("/client/forAdmin/blockClient")
    @Retry(name = "client-ws")
    @CircuitBreaker(name = "client-ws", fallbackMethod = "blockClientFallback")
    MessageResponse blockClient(@RequestBody ClientBlockRequest request);

    default MessageResponse blockClientFallback(ClientBlockRequest request, Throwable throwable) {
        log.info("RequestBody = " + request);
        log.info("Exception class=" + throwable.getClass().getName());
        log.info("Exception took place: " + throwable.getMessage());
        return MessageResponse.of(SERVICE_CALL_ERROR_MESSAGE);
    }

    @GetMapping("/client/forAdmin/listClient")
    @Retry(name = "client-ws")
    @CircuitBreaker(name = "client-ws", fallbackMethod = "listClientFallback")
    ClientListResponse listClient(@RequestParam(name = "limit") Integer limit,
                                 @RequestParam(name = "offset") Integer offset,
                                 @RequestParam(name = "actual") Boolean actual,
                                 @RequestParam(name = "blocked") Boolean blocked);

    default ClientListResponse listClientFallback(Integer limit,
                                                Integer offset,
                                                Boolean actual,
                                                Boolean blocked,
                                                Throwable throwable) {
        ClientListFilter filter =
                new ClientListFilter(limit, offset, actual, blocked);
        log.info("RequestParam = " + filter);
        log.info("Exception class=" + throwable.getClass().getName());
        log.info("Exception took place: " + throwable.getMessage());
        return new ClientListResponse();
    }
}
