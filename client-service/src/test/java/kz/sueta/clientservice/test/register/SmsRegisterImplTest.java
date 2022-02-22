package kz.sueta.clientservice.test.register;

import kz.greetgo.util.RND;
import kz.sueta.clientservice.dto.ui.request.PhoneSmsRequest;
import kz.sueta.clientservice.entity.SmsForAuth;
import kz.sueta.clientservice.in_service.model.SmsSendRequest;
import kz.sueta.clientservice.in_service.model.SmsSendResponse;
import kz.sueta.clientservice.register.SmsRegister;
import kz.sueta.clientservice.repository.SmsForAuthDao;
import kz.sueta.clientservice.test.bean.TestBeanFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestBeanFactory.class)
public class SmsRegisterImplTest {

    @Autowired
    private SmsRegister smsRegister;

    @Autowired
    private SmsForAuthDao smsForAuthDao;

    @Test
    public void testSmsSend() {
        SmsSendRequest request = new SmsSendRequest("+77002589484");

        //
        //
        SmsSendResponse response = smsRegister.sendSms(request);
        //
        //

        assertThat(response).isNotNull();
        assertThat(response.getResponseText()).isEqualTo("OK");
        assertThat(response.getCode()).isEqualTo("0000");
    }

    @Test
    public void validateSmsCode() {
        SmsForAuth smsForAuth = new SmsForAuth();
        smsForAuth.code = "0000";
        smsForAuth.isValidated = false;
        smsForAuth.phone = RND.strInt(11);
        smsForAuth.expiredAt = new Timestamp(new Date().getTime() + 600);

        PhoneSmsRequest phoneSmsRequest = new PhoneSmsRequest();
        phoneSmsRequest.smsCode = "0000";

        //
        //
        boolean valid = smsRegister.validateSmsCode(smsForAuth, phoneSmsRequest);
        //
        //

        Assertions.assertTrue(valid);
    }

    @Test
    public void getSmsForAuth() {
        smsForAuthDao.deleteAll();

        SmsForAuth smsForAuth = new SmsForAuth();
        smsForAuth.code = "0000";
        smsForAuth.isValidated = false;
        smsForAuth.phone = RND.strInt(11);
        smsForAuth.expiredAt = new Timestamp(new Date().getTime() + 600);
        smsForAuthDao.save(smsForAuth);

        PhoneSmsRequest phoneSmsRequest = new PhoneSmsRequest();
        phoneSmsRequest.phoneNumber = smsForAuth.phone;

        //
        //
        Optional<SmsForAuth> test = smsRegister.getSmsForAuth(phoneSmsRequest);
        //
        //

        Assertions.assertTrue(test.isPresent());
        Assertions.assertEquals(test.get().phone, smsForAuth.phone);
    }

    @Test
    public void saveSmsForAuth() {
        smsForAuthDao.deleteAll();

        String phoneNumber = RND.strInt(11);
        String code = RND.strInt(4);

        //
        //
        smsRegister.saveSmsForAuth(phoneNumber, code);
        //
        //

        Optional<SmsForAuth> smsForAuth = smsForAuthDao.findById(phoneNumber);
        Assertions.assertTrue(smsForAuth.isPresent());
        Assertions.assertEquals(smsForAuth.get().phone, phoneNumber);
        Assertions.assertEquals(smsForAuth.get().code, code);
    }

    @Test
    public void save() {
        smsForAuthDao.deleteAll();

        SmsForAuth smsForAuth = new SmsForAuth();
        smsForAuth.code = "0000";
        smsForAuth.isValidated = false;
        smsForAuth.phone = RND.strInt(11);
        smsForAuth.expiredAt = new Timestamp(new Date().getTime() + 600);

        //
        //
        smsRegister.save(smsForAuth);
        //
        //

        Optional<SmsForAuth> test = smsForAuthDao.findById(smsForAuth.phone);
        Assertions.assertTrue(test.isPresent());
        Assertions.assertEquals(test.get().phone, smsForAuth.phone);
        Assertions.assertEquals(test.get().code, smsForAuth.code);
    }
}
