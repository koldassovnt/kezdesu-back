package kz.sueta.adminservice.service_messaging;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import kz.sueta.adminservice.dto.services.request.*;
import kz.sueta.adminservice.dto.services.response.*;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static kz.sueta.adminservice.util.ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE;

@FeignClient(name = "event-ws")
public interface EventServiceClient {

    /**
     * Event region
     */

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

    /**
     * City region
     */

    @PostMapping("/city/save")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "saveCityFallback")
    MessageResponse saveCity(@RequestBody CreateCityRequest request);

    default MessageResponse saveCityFallback(CreateCityRequest request, Throwable throwable) {
        System.out.println("RequestBody = " + request);
        System.out.println("Exception class=" + throwable.getClass().getName());
        System.out.println("Exception took place: " + throwable.getMessage());
        return MessageResponse.of(SERVICE_CALL_ERROR_MESSAGE);
    }

    @PostMapping("/city/edit")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "editCityFallback")
    MessageResponse editCity(@RequestBody EditCityRequest request);

    default MessageResponse editCityFallback(EditCityRequest request, Throwable throwable) {
        System.out.println("RequestBody = " + request);
        System.out.println("Exception class=" + throwable.getClass().getName());
        System.out.println("Exception took place: " + throwable.getMessage());
        return MessageResponse.of(SERVICE_CALL_ERROR_MESSAGE);
    }

    @PostMapping("/city/delete")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "deleteCityFallback")
    MessageResponse deleteCity(@RequestBody DetailRequest request);

    default MessageResponse deleteCityFallback(DetailRequest request, Throwable throwable) {
        System.out.println("RequestBody = " + request);
        System.out.println("Exception class=" + throwable.getClass().getName());
        System.out.println("Exception took place: " + throwable.getMessage());
        return MessageResponse.of(SERVICE_CALL_ERROR_MESSAGE);
    }

    @GetMapping("/city/list")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "listCityFallback")
    CityListResponse listCity(@RequestParam(name = "limit", required = false) Integer limit,
                              @RequestParam(name = "offset", required = false) Integer offset,
                              @RequestParam(name = "actual", required = false) Boolean actual);

    default CityListResponse listCityFallback(Integer limit,
                                              Integer offset,
                                              Boolean actual,
                                              Throwable throwable) {

        DictionaryFilter request = new DictionaryFilter(limit, offset, actual);

        System.out.println("RequestParam = " + request);
        System.out.println("Exception class=" + throwable.getClass().getName());
        System.out.println("Exception took place: " + throwable.getMessage());
        return new CityListResponse();
    }

    @GetMapping("/city/detail")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "detailCityFallback")
    CityDetailResponse detailCity(@RequestParam String id);

    default CityDetailResponse detailCityFallback(String id, Throwable throwable) {
        System.out.println("RequestParam = " + id);
        System.out.println("Exception class=" + throwable.getClass().getName());
        System.out.println("Exception took place: " + throwable.getMessage());
        return null;
    }

    /**
     * Category region
     */

    @PostMapping("/category/save")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "saveCategoryFallback")
    MessageResponse saveCategory(@RequestBody CreateCategoryRequest request);

    default MessageResponse saveCategoryFallback(CreateCategoryRequest request, Throwable throwable) {
        System.out.println("RequestBody = " + request);
        System.out.println("Exception class=" + throwable.getClass().getName());
        System.out.println("Exception took place: " + throwable.getMessage());
        return MessageResponse.of(SERVICE_CALL_ERROR_MESSAGE);
    }

    @PostMapping("/category/edit")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "editCategoryFallback")
    MessageResponse editCategory(@RequestBody EditCategoryRequest request);

    default MessageResponse editCategoryFallback(EditCategoryRequest request, Throwable throwable) {
        System.out.println("RequestBody = " + request);
        System.out.println("Exception class=" + throwable.getClass().getName());
        System.out.println("Exception took place: " + throwable.getMessage());
        return MessageResponse.of(SERVICE_CALL_ERROR_MESSAGE);
    }

    @PostMapping("/category/delete")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "deleteCategoryFallback")
    MessageResponse deleteCategory(@RequestBody DetailRequest request);

    default MessageResponse deleteCategoryFallback(DetailRequest request, Throwable throwable) {
        System.out.println("RequestBody = " + request);
        System.out.println("Exception class=" + throwable.getClass().getName());
        System.out.println("Exception took place: " + throwable.getMessage());
        return MessageResponse.of(SERVICE_CALL_ERROR_MESSAGE);
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

        DictionaryFilter request = new DictionaryFilter(limit, offset, actual);

        System.out.println("RequestParam = " + request);
        System.out.println("Exception class=" + throwable.getClass().getName());
        System.out.println("Exception took place: " + throwable.getMessage());
        return new CategoryListResponse();
    }

    @GetMapping("/category/detail")
    @Retry(name = "event-ws")
    @CircuitBreaker(name = "event-ws", fallbackMethod = "detailCategoryFallback")
    CategoryDetailResponse detailCategory(@RequestParam String id);

    default CategoryDetailResponse detailCategoryFallback(String id, Throwable throwable) {
        System.out.println("RequestParam = " + id);
        System.out.println("Exception class=" + throwable.getClass().getName());
        System.out.println("Exception took place: " + throwable.getMessage());
        return null;
    }
}
