package kz.sueta.clientservice.controller;

import kz.sueta.clientservice.dto.services.response.ClientResponse;
import kz.sueta.clientservice.dto.ui.request.EditClientRequest;
import kz.sueta.clientservice.dto.ui.response.MessageResponse;
import kz.sueta.clientservice.register.ClientRegister;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @SneakyThrows
    @GetMapping("/action/client-detail")
    public ResponseEntity<?> getClient(Authentication authentication) {
        ClientResponse response = clientRegister.getClientById(authentication.getName());
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/action/edit-client")
    public ResponseEntity<?> editClient(@Valid @RequestBody EditClientRequest request,
                                        Authentication authentication) {
        MessageResponse response = clientRegister.editClient(request, authentication.getName());
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping(value = "/action/save-image",
            consumes = {"multipart/form-data"})
    public ResponseEntity<?> saveEventContent(@RequestParam("file") MultipartFile file,
                                              Authentication authentication) {
        MessageResponse response = clientRegister.saveClientImage(file, authentication.getName());
        return ResponseEntity.status(200).body(response);
    }
}
