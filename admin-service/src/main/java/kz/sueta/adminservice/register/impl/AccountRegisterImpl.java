package kz.sueta.adminservice.register.impl;

import kz.sueta.adminservice.dto.ui.request.LoginRequest;
import kz.sueta.adminservice.dto.ui.request.RegisterRequest;
import kz.sueta.adminservice.dto.ui.response.JwtResponse;
import kz.sueta.adminservice.register.AccountRegister;
import kz.sueta.adminservice.repository.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountRegisterImpl implements AccountRegister {

    private final AccountDao accountDao;

    @Autowired
    public AccountRegisterImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest) {

        return null;
    }

    @Override
    public void register(RegisterRequest request) {

    }


}
