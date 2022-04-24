package kz.sueta.adminservice.register;

import kz.sueta.adminservice.dto.ui.request.LoginRequest;
import kz.sueta.adminservice.dto.ui.request.RegisterRequest;
import kz.sueta.adminservice.dto.ui.request.ResetPasswordRequest;
import kz.sueta.adminservice.dto.ui.response.AdminDetail;
import kz.sueta.adminservice.dto.ui.response.JwtResponse;
import org.springframework.security.core.Authentication;

public interface AccountRegister {

    JwtResponse login(LoginRequest loginRequest);

    void register(RegisterRequest request);

    void resetPassword(ResetPasswordRequest passwordRequest, String email);

    AdminDetail getAdminDetail(String adminId);
}
