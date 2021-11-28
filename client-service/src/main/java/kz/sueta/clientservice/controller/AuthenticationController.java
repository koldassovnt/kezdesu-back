package kz.sueta.clientservice.controller;

import kz.sueta.clientservice.register.AuthenticationRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationRegister authenticationRegister;

    @PostMapping("/auth")
    public void getPhoneNumberForAuth(@RequestBody(required = false) String phoneNumber) {
        authenticationRegister.getPhoneNumberForAuth(phoneNumber);
    }
}
