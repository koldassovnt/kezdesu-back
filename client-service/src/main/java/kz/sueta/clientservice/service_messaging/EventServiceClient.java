package kz.sueta.clientservice.service_messaging;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import kz.sueta.clientservice.dto.services.request.*;
import kz.sueta.clientservice.dto.services.response.CategoryListResponse;
import kz.sueta.clientservice.dto.services.response.EventListResponse;
import kz.sueta.clientservice.dto.services.response.EventResponse;
import kz.sueta.clientservice.dto.ui.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static kz.sueta.clientservice.util.ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE;

@FeignClient(name = "event-ws")
public interface EventServiceClient {

    Logger log = LoggerFactory.getLogger(EventServiceClient.class);

    @PostMapping("/event/save")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "saveEventFallback")
    MessageResponse saveEvent(@RequestBody SaveEventRequest saveEventRequest);

    default MessageResponse saveEventFallback(SaveEventRequest saveEventRequest, Throwable throwable) {
        log.info("RequestBody = " + saveEventRequest);
        log.info("Exception class=" + throwable.getClass().getName());
        log.info("Exception took place: " + throwable.getMessage());
        return MessageResponse.of(SERVICE_CALL_ERROR_MESSAGE);
    }

    @PostMapping("/event/edit")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "editEventFallback")
    MessageResponse editEvent(@RequestBody EditEventRequest editEventRequest);

    default MessageResponse editEventFallback(EditEventRequest editEventRequest, Throwable throwable) {
        log.info("RequestBody = " + editEventRequest);
        log.info("Exception class=" + throwable.getClass().getName());
        log.info("Exception took place: " + throwable.getMessage());
        return MessageResponse.of(SERVICE_CALL_ERROR_MESSAGE);
    }

    @PostMapping("/event/block")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "blockEventFallback")
    MessageResponse blockEvent(@RequestBody DetailRequest detailRequest);

    default MessageResponse blockEventFallback(DetailRequest detailRequest, Throwable throwable) {
        log.info("RequestBody = " + detailRequest);
        log.info("Exception class=" + throwable.getClass().getName());
        log.info("Exception took place: " + throwable.getMessage());
        return MessageResponse.of(SERVICE_CALL_ERROR_MESSAGE);
    }

    @PostMapping("/event/delete")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "deleteEventFallback")
    MessageResponse deleteEvent(@RequestBody DetailRequest eventDetailRequest);

    default MessageResponse deleteEventFallback(DetailRequest detailRequest, Throwable throwable) {
        log.info("RequestBody = " + detailRequest);
        log.info("Exception class=" + throwable.getClass().getName());
        log.info("Exception took place: " + throwable.getMessage());
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
        log.info("RequestParam = " + filter);
        log.info("Exception class=" + throwable.getClass().getName());
        log.info("Exception took place: " + throwable.getMessage());
        return new EventListResponse();
    }

    @GetMapping("/event/detail")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "detailEventFallback")
    EventResponse detailEvent(@RequestParam String id);

    default EventResponse detailEventFallback(String id, Throwable throwable) {
        log.info("RequestParam = " + id);
        log.info("Exception class=" + throwable.getClass().getName());
        log.info("Exception took place: " + throwable.getMessage());
        return null;
    }

    @PostMapping("/event/join-event")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "joinEventFallback")
    MessageResponse joinEvent(@RequestBody ClientEventRequest request);

    default MessageResponse joinEventFallback(ClientEventRequest request, Throwable throwable) {
        log.info("RequestBody = " + request);
        log.info("Exception class=" + throwable.getClass().getName());
        log.info("Exception took place: " + throwable.getMessage());
        return MessageResponse.of(SERVICE_CALL_ERROR_MESSAGE);
    }

    @PostMapping("/event/approve-event")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "approveEventFallback")
    MessageResponse approveEvent(@RequestBody ClientEventRequest request);

    default MessageResponse approveEventFallback(ClientEventRequest request, Throwable throwable) {
        log.info("RequestBody = " + request);
        log.info("Exception class=" + throwable.getClass().getName());
        log.info("Exception took place: " + throwable.getMessage());
        return MessageResponse.of(SERVICE_CALL_ERROR_MESSAGE);
    }

    @GetMapping("/event/client-events")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "clientEventsFallback")
    EventListResponse clientEvents(@RequestParam("creator") Boolean creator,
                                   @RequestParam("id") String id);

    default EventListResponse clientEventsFallback(Boolean creator, String id, Throwable throwable) {
        log.info("RequestParam = " + creator + ", " + id);
        log.info("Exception class=" + throwable.getClass().getName());
        log.info("Exception took place: " + throwable.getMessage());
        return new EventListResponse();
    }

    @GetMapping("/category/list")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "listCategoryFallback")
    CategoryListResponse listCategory(@RequestParam(name = "limit", required = false) Integer limit,
                                      @RequestParam(name = "offset", required = false) Integer offset,
                                      @RequestParam(name = "actual", required = false) Boolean actual);

    default CategoryListResponse listCategoryFallback(Integer limit,
                                                      Integer offset,
                                                      Boolean actual,
                                                      Throwable throwable) {

        log.info("RequestParam = " + limit + ", " + offset + ", " + actual);
        log.info("Exception class=" + throwable.getClass().getName());
        log.info("Exception took place: " + throwable.getMessage());
        return new CategoryListResponse();
    }

    @GetMapping("/event/is-event-exists")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "isEventExistFallback")
    Boolean isEventExist(@RequestParam("id") String id);

    default Boolean isEventExistFallback(String id, Throwable throwable) {
        log.info("RequestParam = " + id);
        log.info("Exception class=" + throwable.getClass().getName());
        log.info("Exception took place: " + throwable.getMessage());
        return false;
    }

    @PostMapping("/event/save-content")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "saveContentFallback")
    MessageResponse saveContent(@RequestBody SaveEventContentRequest request);

    default MessageResponse saveContentFallback(SaveEventContentRequest request,
                                                Throwable throwable) {
        log.info("RequestBody = " + request);
        log.info("Exception class=" + throwable.getClass().getName());
        log.info("Exception took place: " + throwable.getMessage());
        return MessageResponse.of(SERVICE_CALL_ERROR_MESSAGE);
    }
}
