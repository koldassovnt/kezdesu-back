package kz.sueta.clientservice.register.impl;

import kz.sueta.clientservice.register.AuthenticationRegister;
import kz.sueta.clientservice.register.SmsRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationRegisterImpl implements AuthenticationRegister {

    @Autowired
    private SmsRegister smsRegister;
}
