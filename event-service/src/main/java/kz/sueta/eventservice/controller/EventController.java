package kz.sueta.eventservice.controller;

import kz.sueta.eventservice.dto.request.ClientEventRequest;
import kz.sueta.eventservice.dto.request.ComplainEventRequest;
import kz.sueta.eventservice.dto.request.SaveEventContentRequest;
import kz.sueta.eventservice.dto.response.EventListResponse;
import kz.sueta.eventservice.dto.response.MessageResponse;
import kz.sueta.eventservice.register.EventRegister;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/event")
public class EventController {

    private final EventRegister eventRegister;

    @Autowired
    public EventController(EventRegister eventRegister) {
        this.eventRegister = eventRegister;
    }

    @PostMapping("/join-event")
    public ResponseEntity<?> joinEvent(@Valid @RequestBody ClientEventRequest request) {
        MessageResponse response = eventRegister.joinEvent(request);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/approve-event")
    public ResponseEntity<?> approveEvent(@Valid @RequestBody ClientEventRequest request) {
        MessageResponse response = eventRegister.approveEvent(request);
        return ResponseEntity.status(200).body(response);
    }

    @SneakyThrows
    @GetMapping("/client-events")
    public ResponseEntity<?> clientEvents(@RequestParam(value = "creator") Boolean creator,
                                          @RequestParam(value = "id") String id) {
        EventListResponse response = eventRegister.clientEvents(creator, id);
        return ResponseEntity.status(200).body(response);
    }

    @SneakyThrows
    @GetMapping("/is-event-exists")
    public ResponseEntity<?> isEventExists(@RequestParam(value = "id") String id) {
        Boolean response = eventRegister.isEventExists(id);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/save-content")
    public ResponseEntity<?> saveContent(@Valid @RequestBody SaveEventContentRequest request) {
        MessageResponse response = eventRegister.saveContent(request);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/complain-event")
    public ResponseEntity<?> complainEvent(@Valid @RequestBody ComplainEventRequest request) {
        MessageResponse response = eventRegister.complainEvent(request);
        return ResponseEntity.status(200).body(response);
    }
}
