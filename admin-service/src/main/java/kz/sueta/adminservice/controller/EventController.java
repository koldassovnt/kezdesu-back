package kz.sueta.adminservice.controller;

import kz.sueta.adminservice.dto.services.request.DetailRequest;
import kz.sueta.adminservice.dto.services.request.EditEventRequest;
import kz.sueta.adminservice.dto.services.request.EventListFilter;
import kz.sueta.adminservice.dto.services.request.SaveEventRequest;
import kz.sueta.adminservice.dto.services.response.EventListResponse;
import kz.sueta.adminservice.dto.services.response.EventResponse;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;
import kz.sueta.adminservice.register.EventRegister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class EventController {

    private final EventRegister eventRegister;

    public EventController(EventRegister eventRegister) {
        this.eventRegister = eventRegister;
    }

    @PostMapping(value = "/action/save-event")
    public ResponseEntity<?> saveEvent(@Valid @RequestBody SaveEventRequest saveRequest,
                                       Authentication authentication) {
        MessageResponse response = eventRegister.saveEvent(saveRequest, authentication.getName());
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping(value = "/action/edit-event")
    public ResponseEntity<?> editEvent(@Valid @RequestBody EditEventRequest editEventRequest) {
        MessageResponse response = eventRegister.editEvent(editEventRequest);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping(value = "/action/block-event")
    public ResponseEntity<?> blockEvent(@Valid @RequestBody DetailRequest eventDetailRequest) {
        MessageResponse response = eventRegister.blockEvent(eventDetailRequest);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping(value = "/action/delete-event")
    public ResponseEntity<?> deleteEvent(@Valid @RequestBody DetailRequest eventDetailRequest) {
        MessageResponse response = eventRegister.deleteEvent(eventDetailRequest);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping(value = "/action/list-event")
    public ResponseEntity<?> listEvent(@RequestParam(name = "limit", required = false) Integer limit,
                                       @RequestParam(name = "offset", required = false) Integer offset,
                                       @RequestParam(name = "categoryId", required = false) String categoryId,
                                       @RequestParam(name = "labelSearch", required = false) String labelSearch,
                                       @RequestParam(name = "clientId", required = false) String clientId,
                                       @RequestParam(name = "actual", required = false) Boolean actual,
                                       @RequestParam(name = "blocked", required = false) Boolean blocked
    ) {
        EventListFilter filter =
                new EventListFilter(limit, offset, categoryId, labelSearch, clientId, actual, blocked);
        EventListResponse response = eventRegister.listEvent(filter);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping(value = "/action/detail-event")
    public ResponseEntity<?> detailEvent(@RequestParam(name = "id") String id) {
        EventResponse response = eventRegister.detailEvent(DetailRequest.of(id));
        return ResponseEntity.status(200).body(response);
    }
}
