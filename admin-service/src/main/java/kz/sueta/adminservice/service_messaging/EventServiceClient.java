package kz.sueta.adminservice.service_messaging;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import kz.sueta.adminservice.dto.services.request.SaveEventRequest;
import kz.sueta.adminservice.dto.services.response.MessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static kz.sueta.adminservice.util.ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE;

@FeignClient(name = "event-ws")
public interface EventServiceClient {

    @PostMapping("/event/save")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "saveEventFallback")
    MessageResponse saveEvent(@RequestBody SaveEventRequest saveEventRequest);

    default MessageResponse saveEventFallback(SaveEventRequest saveEventRequest, Throwable throwable) {
        System.out.println("RequestBody = " + saveEventRequest);
        System.out.println("Exception class=" + throwable.getClass().getName());
        System.out.println("Exception took place: " + throwable.getMessage());
        return MessageResponse.of(SERVICE_CALL_ERROR_MESSAGE);
    }
}
