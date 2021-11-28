package kz.sueta.clientservice.test.register;

import kz.sueta.clientservice.in_service.model.SmsSendRequest;
import kz.sueta.clientservice.in_service.model.SmsSendResponse;
import kz.sueta.clientservice.register.SmsRegister;
import kz.sueta.clientservice.test.bean.SmsSendServiceTest;
import kz.sueta.clientservice.test.bean.TestBeanFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestBeanFactory.class)
public class SmsRegisterTest {

    @Autowired
    private SmsRegister smsRegister;

    @Test
    public void testSmsSend() {
        SmsSendRequest request = new SmsSendRequest("+77002589484", "4536");

        //
        //
        SmsSendResponse response = smsRegister.sendSms(request);
        //
        //

        assertThat(response).isNotNull();
        assertThat(response.getResponseText()).isEqualTo("OK");
    }
}
