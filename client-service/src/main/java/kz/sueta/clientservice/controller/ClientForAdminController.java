package kz.sueta.clientservice.controller;

import kz.sueta.clientservice.dto.services.request.ClientBlockRequest;
import kz.sueta.clientservice.dto.services.request.ClientListFilter;
import kz.sueta.clientservice.dto.services.request.IdListRequest;
import kz.sueta.clientservice.dto.services.response.ClientListResponse;
import kz.sueta.clientservice.dto.services.response.ClientResponse;
import kz.sueta.clientservice.dto.ui.response.MessageResponse;
import kz.sueta.clientservice.register.ClientRegister;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*")
public class ClientForAdminController {

    private final ClientRegister clientRegister;

    @Autowired
    public ClientForAdminController(ClientRegister clientRegister) {
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

    @SneakyThrows
    @GetMapping("/forAdmin/client-detail")
    public ResponseEntity<?> getClient(@RequestParam(value = "id") String id) {
        ClientResponse response = clientRegister.getClientById(id);
        return ResponseEntity.status(200).body(response);
    }

    @SneakyThrows
    @PostMapping("/forAdmin/listClientById")
    public ResponseEntity<?> listClientById(@Valid @RequestBody IdListRequest idListRequest) {
        ClientListResponse response = clientRegister.listClientById(idListRequest);
        return ResponseEntity.status(200).body(response);
    }
}
