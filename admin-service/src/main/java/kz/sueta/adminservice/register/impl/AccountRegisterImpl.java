package kz.sueta.adminservice.register.impl;

import com.google.common.base.Strings;
import kz.sueta.adminservice.beans.jwt.JwtUtils;
import kz.sueta.adminservice.beans.services.CustomAccountDetails;
import kz.sueta.adminservice.dto.ui.request.AccountEditRequest;
import kz.sueta.adminservice.dto.ui.request.LoginRequest;
import kz.sueta.adminservice.dto.ui.request.RegisterRequest;
import kz.sueta.adminservice.dto.ui.request.ResetPasswordRequest;
import kz.sueta.adminservice.dto.ui.response.AdminDetail;
import kz.sueta.adminservice.dto.ui.response.JwtResponse;
import kz.sueta.adminservice.entity.Account;
import kz.sueta.adminservice.exception.ui.RegisterException;
import kz.sueta.adminservice.exception.ui.RestException;
import kz.sueta.adminservice.register.AccountRegister;
import kz.sueta.adminservice.repository.AccountDao;
import kz.sueta.adminservice.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountRegisterImpl implements AccountRegister {

    private final AccountDao accountDao;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountRegisterImpl(AccountDao accountDao,
                               AuthenticationManager authenticationManager,
                               JwtUtils jwtUtils,
                               PasswordEncoder passwordEncoder) {
        this.accountDao = accountDao;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.email, loginRequest.password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomAccountDetails accountDetails = (CustomAccountDetails) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(accountDetails);

        return JwtResponse.of(jwt, accountDetails.getUsername());
    }

    @Override
    public void register(RegisterRequest request) {
        if (accountDao.findAccountByEmailAndActual(request.email, true) != null) {
            throw new RegisterException(HttpStatus.valueOf(400),
                    "hOsZ6cprj9 :: Account with that email already exists!");
        }

        Account account = new Account();
        account.accountId = UUID.randomUUID().toString();
        account.email = request.email;
        account.password = passwordEncoder.encode(request.password);
        account.phone = request.phone;

        if (Strings.isNullOrEmpty(request.displayName)) {
            request.displayName = AuthUtil.UNNAMED_DISPLAY_NAME;
        }

        account.displayName = request.displayName;
        account.actual = true;

        accountDao.save(account);
    }

    @Override
    public void resetPassword(ResetPasswordRequest passwordRequest, String email) {
        if (Strings.isNullOrEmpty(email)) {
            throw new RestException("0jI7SZzFxc :: Authorization is incorrect, please login again!");
        }

        Account account = accountDao.findAccountByEmailAndActual(email, true);

        if (account == null) {
            throw new RuntimeException("Wts0r5oyUC :: account is null by email!");
        }

        if (!passwordEncoder.matches(passwordRequest.oldPassword, account.password)) {
            throw new RestException("5rJTM6lmK4 :: Old password does not match actual password!");
        }

        account.password = passwordEncoder.encode(passwordRequest.newPassword);
        accountDao.save(account);
    }

    @Override
    public AdminDetail getAdminDetailById(String adminId) {

        Account account = accountDao.findAccountByAccountIdAndActual(adminId, true);

        if (account == null) {
            throw new RuntimeException("utOpTh4sGy :: no account by Id = " + adminId);
        }

        return AdminDetail.of(account.accountId, account.displayName, account.phone);
    }

    @Override
    public void editAccount(AccountEditRequest editRequest, String email) {
        if (Strings.isNullOrEmpty(email)) {
            throw new RestException("VlZS57g4dy :: Authorization is incorrect, please login again!");
        }

        Account account = accountDao.findAccountByEmailAndActual(email, true);

        if (account == null) {
            throw new RuntimeException("Wts0r5oyUC :: account is null by email!");
        }

        if (!Strings.isNullOrEmpty(editRequest.displayName)) {
            account.displayName = editRequest.displayName;
        }

        if (!Strings.isNullOrEmpty(editRequest.phone)) {
            account.phone = editRequest.phone;
        }

        accountDao.save(account);
    }

    @Override
    public AdminDetail getAdminDetail(String email) {
        if (Strings.isNullOrEmpty(email)) {
            throw new RestException("Anj40PaL3E :: Authorization is incorrect, please login again!");
        }

        Account account = accountDao.findAccountByEmailAndActual(email, true);

        if (account == null) {
            throw new RuntimeException("ENZ462UAQ8 :: account is null by email!");
        }

        return AdminDetail.of(account.accountId, account.displayName, account.phone);
    }

}
