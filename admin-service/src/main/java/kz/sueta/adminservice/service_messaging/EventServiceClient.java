package kz.sueta.adminservice.service_messaging;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import kz.sueta.adminservice.dto.services.request.DetailRequest;
import kz.sueta.adminservice.dto.services.request.EditEventRequest;
import kz.sueta.adminservice.dto.services.request.EventListFilter;
import kz.sueta.adminservice.dto.services.request.SaveEventRequest;
import kz.sueta.adminservice.dto.services.response.EventListResponse;
import kz.sueta.adminservice.dto.services.response.EventResponse;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/event/edit")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "editEventFallback")
    MessageResponse editEvent(@RequestBody EditEventRequest editEventRequest);

    default MessageResponse editEventFallback(EditEventRequest editEventRequest, Throwable throwable) {
        System.out.println("RequestBody = " + editEventRequest);
        System.out.println("Exception class=" + throwable.getClass().getName());
        System.out.println("Exception took place: " + throwable.getMessage());
        return MessageResponse.of(SERVICE_CALL_ERROR_MESSAGE);
    }

    @PostMapping("/event/block")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "blockEventFallback")
    MessageResponse blockEvent(@RequestBody DetailRequest detailRequest);

    default MessageResponse blockEventFallback(DetailRequest detailRequest, Throwable throwable) {
        System.out.println("RequestBody = " + detailRequest);
        System.out.println("Exception class=" + throwable.getClass().getName());
        System.out.println("Exception took place: " + throwable.getMessage());
        return MessageResponse.of(SERVICE_CALL_ERROR_MESSAGE);
    }

    @PostMapping("/event/delete")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "deleteEventFallback")
    MessageResponse deleteEvent(@RequestBody DetailRequest eventDetailRequest);

    default MessageResponse deleteEventFallback(DetailRequest detailRequest, Throwable throwable) {
        System.out.println("RequestBody = " + detailRequest);
        System.out.println("Exception class=" + throwable.getClass().getName());
        System.out.println("Exception took place: " + throwable.getMessage());
        return MessageResponse.of(SERVICE_CALL_ERROR_MESSAGE);
    }

    @GetMapping("/event/list")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "listEventFallback")
    EventListResponse listEvent(@RequestParam(name = "limit") Integer limit,
                                @RequestParam(name = "offset") Integer offset,
                                @RequestParam(name = "categoryId") String categoryId,
                                @RequestParam(name = "labelSearch") String labelSearch,
                                @RequestParam(name = "clientId") String clientId,
                                @RequestParam(name = "actual") Boolean actual,
                                @RequestParam(name = "blocked") Boolean blocked);

    default EventListResponse listEventFallback(Integer limit,
                                                Integer offset,
                                                String categoryId,
                                                String labelSearch,
                                                String clientId,
                                                Boolean actual,
                                                Boolean blocked,
                                                Throwable throwable) {
        EventListFilter filter =
                new EventListFilter(limit, offset, categoryId, labelSearch, clientId, actual, blocked);
        System.out.println("RequestParam = " + filter);
        System.out.println("Exception class=" + throwable.getClass().getName());
        System.out.println("Exception took place: " + throwable.getMessage());
        return new EventListResponse();
    }

    @GetMapping("/event/detail")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "detailEventFallback")
    EventResponse detailEvent(@RequestParam String id);

    default EventResponse detailEventFallback(String id, Throwable throwable) {
        System.out.println("RequestParam = " + id);
        System.out.println("Exception class=" + throwable.getClass().getName());
        System.out.println("Exception took place: " + throwable.getMessage());
        return null;
    }
}
