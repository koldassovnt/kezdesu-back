package kz.sueta.adminservice.register;

import kz.sueta.adminservice.dto.ui.request.LoginRequest;
import kz.sueta.adminservice.dto.ui.request.RegisterRequest;
import kz.sueta.adminservice.dto.ui.response.JwtResponse;

public interface AccountRegister {

    JwtResponse login(LoginRequest loginRequest);

    void register(RegisterRequest request);
}
