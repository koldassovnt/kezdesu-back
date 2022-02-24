package kz.sueta.eventservice.controller;

import kz.sueta.eventservice.dto.request.EventDetailRequest;
import kz.sueta.eventservice.dto.request.EditEventRequest;
import kz.sueta.eventservice.dto.request.EventListFilter;
import kz.sueta.eventservice.dto.request.SaveEventRequest;
import kz.sueta.eventservice.dto.response.EventListResponse;
import kz.sueta.eventservice.dto.response.EventResponse;
import kz.sueta.eventservice.dto.response.MessageResponse;
import kz.sueta.eventservice.register.EventCrudRegister;
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
    public ResponseEntity<?> blockEvent(@Valid @RequestBody EventDetailRequest eventDetailRequest) {
        eventCrudRegister.blockEvent(eventDetailRequest);
        return ResponseEntity.status(200).body(MessageResponse.of("Event successfully blocked!"));
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteEvent(@Valid @RequestBody EventDetailRequest eventDetailRequest) {
        eventCrudRegister.deleteEvent(eventDetailRequest);
        return ResponseEntity.status(200).body(MessageResponse.of("Event successfully deleted!"));
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> eventList(@Valid @RequestBody EventListFilter filter) {
        EventListResponse listResponse = eventCrudRegister.eventList(filter);
        return ResponseEntity.status(200).body(listResponse);
    }

    @GetMapping(value = "/detail")
    public ResponseEntity<?> eventDetail(@Valid @RequestBody EventDetailRequest eventDetailRequest) {
        EventResponse response = eventCrudRegister.eventDetail(eventDetailRequest);
        return ResponseEntity.status(200).body(response);
    }
}
