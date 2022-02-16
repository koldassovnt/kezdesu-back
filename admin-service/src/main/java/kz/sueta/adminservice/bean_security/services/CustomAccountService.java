package kz.sueta.adminservice.bean_security.services;

import kz.sueta.adminservice.entity.Account;
import kz.sueta.adminservice.repository.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomAccountService implements UserDetailsService {

    private final AccountDao accountDao;

    @Autowired
    public CustomAccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountDao.findAccountByEmailAndActual(email, true);

        if (account == null) {
            throw new UsernameNotFoundException("XIlBrxeux8 :: Account not found with email: " + email);
        }

        return CustomAccountDetails.of(account);
    }
}
