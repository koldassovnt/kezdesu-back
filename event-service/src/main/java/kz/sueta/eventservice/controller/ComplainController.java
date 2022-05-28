package kz.sueta.eventservice.controller;

import kz.sueta.eventservice.dto.response.ComplainListResponse;
import kz.sueta.eventservice.register.ComplainRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/complain")
public class ComplainController {

    private final ComplainRegister complainRegister;

    @Autowired
    public ComplainController(ComplainRegister complainRegister) {
        this.complainRegister = complainRegister;
    }

    @GetMapping("/list")
    public ResponseEntity<?> complainList() throws SQLException {
        ComplainListResponse response = complainRegister.complainList();
        return ResponseEntity.status(200).body(response);
    }
}
