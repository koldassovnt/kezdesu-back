package kz.sueta.adminservice.register.impl;

import com.google.common.base.Strings;
import kz.sueta.adminservice.bean_security.jwt.JwtUtils;
import kz.sueta.adminservice.bean_security.services.CustomAccountDetails;
import kz.sueta.adminservice.dto.ui.request.LoginRequest;
import kz.sueta.adminservice.dto.ui.request.RegisterRequest;
import kz.sueta.adminservice.dto.ui.response.JwtResponse;
import kz.sueta.adminservice.entity.Account;
import kz.sueta.adminservice.exception.ui.RegisterException;
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

}
