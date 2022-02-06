package kz.sueta.clientservice.test.register;

import kz.sueta.clientservice.entity.SmsForAuth;
import kz.sueta.clientservice.register.AuthenticationRegister;
import kz.sueta.clientservice.repository.SmsForAuthDao;
import kz.sueta.clientservice.exceptions.ui.PhoneNumberInvalidException;
import kz.sueta.clientservice.test.bean.TestBeanFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestBeanFactory.class)
public class AuthenticationRegisterTest {

    @Autowired
    private AuthenticationRegister register;

    @Autowired
    private SmsForAuthDao smsForAuthDao;

    @Test
    public void getPhoneNumberForAuth_null() {
        PhoneNumberInvalidException thrown = Assertions.assertThrows(PhoneNumberInvalidException.class, () -> {
            //
            //
            register.postPhoneNumberForAuth(null);
            //
            //
        });

        Assertions.assertEquals("Не корректный мобильный номер!", thrown.getMessage());
    }

    @Test
    public void getPhoneNumberForAuth_empty() {
        PhoneNumberInvalidException thrown = Assertions.assertThrows(PhoneNumberInvalidException.class, () -> {
            //
            //
            register.postPhoneNumberForAuth("");
            //
            //
        });

        Assertions.assertEquals("Не корректный мобильный номер!", thrown.getMessage());
    }

    @Test
    public void getPhoneNumberForAuth_invalid() {
        PhoneNumberInvalidException thrown = Assertions.assertThrows(PhoneNumberInvalidException.class, () -> {
            //
            //
            register.postPhoneNumberForAuth("7777");
            //
            //
        });

        Assertions.assertEquals("Не корректный мобильный номер!", thrown.getMessage());
    }

    @Test
    public void getPhoneNumberForAuth_insIntoTable() {

        String phoneNumber = "+77005326898";

        //
        //
        register.postPhoneNumberForAuth(phoneNumber);
        //
        //

        Optional<SmsForAuth> test = smsForAuthDao.findById(phoneNumber);

        assertThat(test).isNotEmpty();
        assertThat(test.get()).isNotNull();
        assertThat(test.get().getPhone()).isEqualTo(phoneNumber);
        assertThat(test.get().getCode().length()).isEqualTo(4);
    }
}
