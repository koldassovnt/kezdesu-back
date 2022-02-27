package kz.sueta.clientservice.controller;

import kz.sueta.clientservice.dto.ui.request.PhoneSmsRequest;
import kz.sueta.clientservice.dto.ui.request.TokenRefreshRequest;
import kz.sueta.clientservice.dto.ui.response.JwtResponse;
import kz.sueta.clientservice.dto.ui.response.MessageResponse;
import kz.sueta.clientservice.dto.ui.response.TokenRefreshResponse;
import kz.sueta.clientservice.register.AuthenticationRegister;
import kz.sueta.clientservice.util.AuthStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationRegister authenticationRegister;

    @Autowired
    public AuthenticationController(AuthenticationRegister authenticationRegister) {
        this.authenticationRegister = authenticationRegister;
    }

    @PostMapping(value = "/post-phone-number",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> postPhoneNumberForAuth(
            @Valid @RequestBody(required = false) PhoneSmsRequest phoneSmsRequest) {
        authenticationRegister.postPhoneNumberForAuth(phoneSmsRequest.getPhoneNumber());
        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.of(AuthStatic.SMS_SEND_CODE));
    }

    @PostMapping(value = "/post-sms-for-auth",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> postSmsForAuth(
            @Valid @RequestBody(required = false) PhoneSmsRequest phoneSmsRequest) {
        JwtResponse response = authenticationRegister.postSmsForAuth(phoneSmsRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/refresh_token",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> refreshToken(
            @Valid @RequestBody TokenRefreshRequest request) {
        TokenRefreshResponse response = authenticationRegister.refreshToken(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
