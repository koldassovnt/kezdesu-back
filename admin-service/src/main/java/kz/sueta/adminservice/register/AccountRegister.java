package kz.sueta.adminservice.register;

import kz.sueta.adminservice.dto.ui.request.LoginRequest;
import kz.sueta.adminservice.dto.ui.request.RegisterRequest;
import kz.sueta.adminservice.dto.ui.response.JwtResponse;

public interface AccountRegister {

    JwtResponse login(LoginRequest loginRequest); //todo TEST

    void register(RegisterRequest request); //todo TEST
}
