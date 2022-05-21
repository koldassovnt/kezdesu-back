package kz.sueta.clientservice.controller;

import kz.sueta.clientservice.register.ContentRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/client")
public class FileController {

    private final ContentRegister contentRegister;

    @Autowired
    public FileController(ContentRegister contentRegister) {
        this.contentRegister = contentRegister;
    }

    @GetMapping(value = "/action/get-content/{id}")
    public ResponseEntity<?> getContent(@PathVariable String id) {
        String response = contentRegister.getContentBase64ById(id);
        return ResponseEntity.status(200).body(response);
    }
}
