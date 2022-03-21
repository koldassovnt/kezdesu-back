package kz.sueta.adminservice.controller;

import kz.sueta.adminservice.dto.services.request.ClientBlockRequest;
import kz.sueta.adminservice.dto.services.request.ClientListFilter;
import kz.sueta.adminservice.dto.services.request.DetailRequest;
import kz.sueta.adminservice.dto.services.request.EventListFilter;
import kz.sueta.adminservice.dto.services.response.ClientListResponse;
import kz.sueta.adminservice.dto.services.response.EventListResponse;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;
import kz.sueta.adminservice.register.ClientRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class ClientController {

    private final ClientRegister clientRegister;

    @Autowired
    public ClientController(ClientRegister clientRegister) {
        this.clientRegister = clientRegister;
    }

    @PostMapping(value = "/action/blockClient")
    public ResponseEntity<?> blockClient(@Valid @RequestBody ClientBlockRequest request) {
        MessageResponse response = clientRegister.blockClient(request);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping(value = "/action/listClient")
    public ResponseEntity<?> listClient(@RequestParam(name = "limit", required = false) Integer limit,
                                       @RequestParam(name = "offset", required = false) Integer offset,
                                       @RequestParam(name = "actual", required = false) Boolean actual,
                                       @RequestParam(name = "blocked", required = false) Boolean blocked
    ) {
        ClientListFilter filter = new ClientListFilter(limit, offset, actual, blocked);
        ClientListResponse response = clientRegister.listClient(filter);
        return ResponseEntity.status(200).body(response);
    }
}
