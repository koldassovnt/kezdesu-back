package kz.sueta.clientservice.controller;

import kz.sueta.clientservice.dto.PhoneSmsRequest;
import kz.sueta.clientservice.register.AuthenticationRegister;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void postPhoneNumberForAuth(@RequestBody(required = false) PhoneSmsRequest phoneSmsRequest) {
        authenticationRegister.postPhoneNumberForAuth(phoneSmsRequest.getPhoneNumber());
    }

    @PostMapping("/post-sms-for-auth")
    public void postSmsForAuth(@RequestBody(required = false) PhoneSmsRequest phoneSmsRequest) {
        authenticationRegister.postSmsForAuth(phoneSmsRequest);
    }
}
