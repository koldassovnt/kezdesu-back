package kz.sueta.clientservice.controller;

import kz.sueta.clientservice.in_service.model.SmsSendRequest;
import kz.sueta.clientservice.register.SmsRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private SmsRegister smsRegister;

    @GetMapping("/test")
    public String test() {
        SmsSendRequest request = new SmsSendRequest("751156465", "code");
        return smsRegister.sendSms(request).getResponseText();
    }
}
