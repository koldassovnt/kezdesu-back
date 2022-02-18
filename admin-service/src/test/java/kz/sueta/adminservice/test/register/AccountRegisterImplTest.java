package kz.sueta.adminservice.test.register;

import kz.greetgo.util.RND;
import kz.sueta.adminservice.dto.ui.request.RegisterRequest;
import kz.sueta.adminservice.entity.Account;
import kz.sueta.adminservice.exception.ui.RegisterException;
import kz.sueta.adminservice.register.AccountRegister;
import kz.sueta.adminservice.repository.AccountDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountRegisterImplTest {

    @Autowired
    private AccountRegister accountRegister;

    @Autowired
    private AccountDao accountDao;

    @Test
    public void registerTest() {
        accountDao.deleteAll();

        RegisterRequest request = new RegisterRequest();
        request.displayName = RND.str(10);
        request.password = RND.str(15);
        request.email = RND.str(7) + "@mail.com";
        request.phone = RND.strInt(11);

        //
        //
        accountRegister.register(request);
        //
        //

        Account acc = accountDao.findAccountByEmailAndActual(request.email, true);

        Assertions.assertNotNull(acc);
        Assertions.assertEquals(acc.email, request.email);
        Assertions.assertEquals(acc.displayName, request.displayName);
        Assertions.assertEquals(acc.phone, request.phone);
    }

    @Test
    public void registerTest__Exception() {
        accountDao.deleteAll();

        RegisterRequest request = new RegisterRequest();
        request.displayName = RND.str(10);
        request.password = RND.str(15);
        request.email = RND.str(7) + "@mail.com";
        request.phone = RND.strInt(11);

        accountRegister.register(request);

        RegisterException thrown = Assertions.assertThrows(RegisterException.class, () -> {
            //
            //
            accountRegister.register(request);
            //
            //
        });

        Assertions.assertEquals("400 BAD_REQUEST \"hOsZ6cprj9 :: Account with that email already exists!\"",
                thrown.getMessage());
    }
}
