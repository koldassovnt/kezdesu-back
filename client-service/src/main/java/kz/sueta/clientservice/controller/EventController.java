package kz.sueta.clientservice.controller;

import kz.sueta.clientservice.dto.services.request.DetailRequest;
import kz.sueta.clientservice.dto.services.request.EditEventRequest;
import kz.sueta.clientservice.dto.services.request.EventListFilter;
import kz.sueta.clientservice.dto.services.request.SaveEventRequest;
import kz.sueta.clientservice.dto.services.response.EventListResponse;
import kz.sueta.clientservice.dto.services.response.EventResponse;
import kz.sueta.clientservice.dto.ui.response.MessageResponse;
import kz.sueta.clientservice.register.EventRegister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/client")
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
    public ResponseEntity<?> editEvent(@Valid @RequestBody EditEventRequest editEventRequest,
                                       Authentication authentication) {
        MessageResponse response = eventRegister.editEvent(editEventRequest, authentication.getName());
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/action/list-event")
    public ResponseEntity<?> listEvents(@RequestParam(name = "categoryId", required = false) String categoryId,
                                        @RequestParam(name = "labelSearch", required = false) String labelSearch,
                                        @RequestParam(name = "clientId", required = false) String clientId,
                                        @RequestParam(name = "actual", required = false) Boolean actual,
                                        @RequestParam(name = "blocked", required = false) Boolean blocked) {

        EventListFilter filter = new EventListFilter(1000, 0, categoryId, labelSearch, clientId, actual, blocked);

        EventListResponse response = eventRegister.listEvents(filter);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/action/detail-event")
    public ResponseEntity<?> detailEvent(@RequestParam(name = "id", required = false) String id) {
        EventResponse response = eventRegister.detailEvent(DetailRequest.of(id));
        return ResponseEntity.status(200).body(response);
    }

    //todo join / qr scan
}
