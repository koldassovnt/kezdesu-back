package kz.sueta.clientservice.controller;

import kz.sueta.clientservice.dto.services.response.ClientResponse;
import kz.sueta.clientservice.register.ClientRegister;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*")
public class ClientController { //todo edit client info

    private final ClientRegister clientRegister;

    @Autowired
    public ClientController(ClientRegister clientRegister) {
        this.clientRegister = clientRegister;
    }

    @SneakyThrows
    @GetMapping("/action/client-detail")
    public ResponseEntity<?> getClient(Authentication authentication) {
        ClientResponse response = clientRegister.getClientById(authentication.getName());
        return ResponseEntity.status(200).body(response);
    }
}
