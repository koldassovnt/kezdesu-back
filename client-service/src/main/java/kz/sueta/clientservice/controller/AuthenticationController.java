package kz.sueta.clientservice.controller;

import kz.sueta.clientservice.dto.ui.PhoneSmsRequest;
import kz.sueta.clientservice.register.AuthenticationRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationRegister authenticationRegister;

    @PostMapping("/post-phone-number")
    public ResponseEntity<PhoneSmsRequest> postPhoneNumberForAuth(
            @RequestBody(required = false) PhoneSmsRequest phoneSmsRequest) {
        authenticationRegister.postPhoneNumberForAuth(phoneSmsRequest.getPhoneNumber());
        return ResponseEntity.status(HttpStatus.OK).body(phoneSmsRequest);
    }

    @PostMapping("/post-sms-for-auth")
    public ResponseEntity<PhoneSmsRequest> postSmsForAuth(
            @RequestBody(required = false) PhoneSmsRequest phoneSmsRequest) {
        authenticationRegister.postSmsForAuth(phoneSmsRequest);
        return ResponseEntity.status(HttpStatus.OK).body(phoneSmsRequest);
    }
}
