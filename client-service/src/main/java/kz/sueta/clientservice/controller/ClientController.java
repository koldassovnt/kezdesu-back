package kz.sueta.clientservice.controller;

import kz.sueta.clientservice.dto.services.request.ClientBlockRequest;
import kz.sueta.clientservice.dto.services.request.ClientListFilter;
import kz.sueta.clientservice.dto.services.response.ClientListResponse;
import kz.sueta.clientservice.dto.ui.response.MessageResponse;
import kz.sueta.clientservice.register.ClientRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*")
public class ClientController {

    private final ClientRegister clientRegister;

    @Autowired
    public ClientController(ClientRegister clientRegister) {
        this.clientRegister = clientRegister;
    }

    @GetMapping("/forAdmin/listClient")
    public ResponseEntity<?> listClient(@RequestParam(name = "limit", required = false) Integer limit,
                                        @RequestParam(name = "offset", required = false) Integer offset,
                                        @RequestParam(name = "actual", required = false) Boolean actual,
                                        @RequestParam(name = "blocked", required = false) Boolean blocked) {

        ClientListFilter filter = new ClientListFilter(limit, offset, actual, blocked);
        ClientListResponse response = clientRegister.listClient(filter);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/forAdmin/blockClient")
    public ResponseEntity<?> blockClient(@Valid @RequestBody ClientBlockRequest request) {
        clientRegister.blockClient(request);
        return ResponseEntity.status(200).body(MessageResponse.of("Client successfully blocked!"));
    }

}