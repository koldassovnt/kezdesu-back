package kz.sueta.adminservice.controller;

import kz.sueta.adminservice.dto.ui.request.LoginRequest;
import kz.sueta.adminservice.dto.ui.request.RegisterRequest;
import kz.sueta.adminservice.dto.ui.request.ResetPasswordRequest;
import kz.sueta.adminservice.dto.ui.response.AdminDetail;
import kz.sueta.adminservice.dto.ui.response.JwtResponse;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;
import kz.sueta.adminservice.register.AccountRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AccountController {

    private final AccountRegister accountRegister;

    @Autowired
    public AccountController(AccountRegister accountRegister) {
        this.accountRegister = accountRegister;
    }

    @PostMapping(value = "/auth/login",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = accountRegister.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }

    @PostMapping(value = "/auth/register",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        accountRegister.register(request);
        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.of("Account registered successfully!"));
    }

    @PostMapping(value = "/action/reset-password",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest passwordRequest,
                                           Authentication authentication) {
        accountRegister.resetPassword(passwordRequest, authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(
                MessageResponse.of("Account password reset was done successfully!"));
    }

    @GetMapping("/action/get-account")
    public ResponseEntity<?> getAccount(@RequestParam(value = "id") String id) {
        AdminDetail detail = accountRegister.getAdminDetail(id);
        return ResponseEntity.status(200).body(detail);
    }
}
