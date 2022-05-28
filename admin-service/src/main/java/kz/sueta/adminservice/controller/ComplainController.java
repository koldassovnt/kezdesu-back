package kz.sueta.adminservice.controller;

import kz.sueta.adminservice.dto.services.response.AdminComplainListResponse;
import kz.sueta.adminservice.register.EventRegister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class ComplainController {

    private final EventRegister eventRegister;

    public ComplainController(EventRegister eventRegister) {
        this.eventRegister = eventRegister;
    }

    @GetMapping(value = "/action/list-complain")
    public ResponseEntity<?> listEvent() {
        AdminComplainListResponse response = eventRegister.complainList();
        return ResponseEntity.status(200).body(response);
    }
}
