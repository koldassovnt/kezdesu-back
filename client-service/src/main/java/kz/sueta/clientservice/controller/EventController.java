package kz.sueta.clientservice.controller;

import kz.sueta.clientservice.dto.services.request.*;
import kz.sueta.clientservice.dto.services.response.EventListResponse;
import kz.sueta.clientservice.dto.ui.response.ClientEventListResponse;
import kz.sueta.clientservice.dto.ui.response.ClientEventResponse;
import kz.sueta.clientservice.dto.ui.response.ClientInfoResponse;
import kz.sueta.clientservice.dto.ui.response.MessageResponse;
import kz.sueta.clientservice.register.EventRegister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

        ClientEventListResponse response = eventRegister.listEvents(filter);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/action/detail-event")
    public ResponseEntity<?> detailEvent(@RequestParam(name = "id", required = false) String id) {
        ClientEventResponse response = eventRegister.detailEvent(DetailRequest.of(id));
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/action/join-event")
    public ResponseEntity<?> joinEvent(@Valid @RequestBody DetailRequest detailRequest,
                                       Authentication authentication) {
        ClientInfoResponse response = eventRegister.joinEvent(detailRequest, authentication.getName());
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/action/qr-event")
    public ResponseEntity<?> qrEvent(@Valid @RequestBody DetailRequest detailRequest,
                                     Authentication authentication) {
        MessageResponse response = eventRegister.qrEvent(detailRequest, authentication.getName());
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/action/client-events")
    public ResponseEntity<?> clientEvents(Authentication authentication) {
        EventListResponse response = eventRegister.clientEvents(authentication.getName());
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/action/client-participated-events")
    public ResponseEntity<?> clientParticipatedEvents(Authentication authentication) {
        EventListResponse response = eventRegister.clientParticipatedEvents(authentication.getName());
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping(value = "/action/event/{id}/save-content",
            consumes = {"multipart/form-data"})
    public ResponseEntity<?> saveEventContent(@PathVariable String id,
                                              @RequestParam("file") MultipartFile file,
                                              Authentication authentication) {
        MessageResponse response = eventRegister.saveEventContent(file, id, authentication.getName());
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/action/complain-event")
    public ResponseEntity<?> complainEvent(@Valid @RequestBody ComplainEventRequest request,
                                     Authentication authentication) {
        MessageResponse response = eventRegister.complainEvent(request, authentication.getName());
        return ResponseEntity.status(200).body(response);
    }
}
