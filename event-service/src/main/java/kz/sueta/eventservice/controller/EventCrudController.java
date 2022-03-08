package kz.sueta.eventservice.controller;

import kz.sueta.eventservice.dto.request.DetailRequest;
import kz.sueta.eventservice.dto.request.EditEventRequest;
import kz.sueta.eventservice.dto.request.EventListFilter;
import kz.sueta.eventservice.dto.request.SaveEventRequest;
import kz.sueta.eventservice.dto.response.EventListResponse;
import kz.sueta.eventservice.dto.response.EventResponse;
import kz.sueta.eventservice.dto.response.MessageResponse;
import kz.sueta.eventservice.register.EventCrudRegister;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/event")
public class EventCrudController {

    private final EventCrudRegister eventCrudRegister;

    @Autowired
    public EventCrudController(EventCrudRegister eventCrudRegister) {
        this.eventCrudRegister = eventCrudRegister;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveEvent(@Valid @RequestBody SaveEventRequest saveRequest) {
        eventCrudRegister.saveEvent(saveRequest);
        return ResponseEntity.status(200).body(MessageResponse.of("Event successfully created!"));
    }

    @PostMapping(value = "/edit")
    public ResponseEntity<?> editEvent(@Valid @RequestBody EditEventRequest editEventRequest) {
        eventCrudRegister.editEvent(editEventRequest);
        return ResponseEntity.status(200).body(MessageResponse.of("Event successfully edited!"));
    }

    @PostMapping(value = "/block")
    public ResponseEntity<?> blockEvent(@Valid @RequestBody DetailRequest eventDetailRequest) {
        eventCrudRegister.blockEvent(eventDetailRequest);
        return ResponseEntity.status(200).body(MessageResponse.of("Event successfully blocked!"));
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteEvent(@Valid @RequestBody DetailRequest eventDetailRequest) {
        eventCrudRegister.deleteEvent(eventDetailRequest);
        return ResponseEntity.status(200).body(MessageResponse.of("Event successfully deleted!"));
    }

    @SneakyThrows
    @GetMapping(value = "/list")
    public ResponseEntity<?> eventList(@RequestParam(name = "limit", required = false) Integer limit,
                                       @RequestParam(name = "offset", required = false) Integer offset,
                                       @RequestParam(name = "categoryId", required = false) String categoryId,
                                       @RequestParam(name = "labelSearch", required = false) String labelSearch,
                                       @RequestParam(name = "clientId", required = false) String clientId,
                                       @RequestParam(name = "actual", required = false) Boolean actual,
                                       @RequestParam(name = "blocked", required = false) Boolean blocked
    ) {
        EventListFilter filter =
                new EventListFilter(limit, offset, categoryId, labelSearch, clientId, actual, blocked);
        EventListResponse listResponse = eventCrudRegister.eventList(filter);
        return ResponseEntity.status(200).body(listResponse);
    }

    @SneakyThrows
    @GetMapping(value = "/detail")
    public ResponseEntity<?> eventDetail(@RequestParam(name = "id") String id) {
        EventResponse response = eventCrudRegister.eventDetail(DetailRequest.of(id));
        return ResponseEntity.status(200).body(response);
    }
}
