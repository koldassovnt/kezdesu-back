package kz.sueta.clientservice.test.register;

import kz.greetgo.util.RND;
import kz.sueta.clientservice.dto.ui.request.PhoneSmsRequest;
import kz.sueta.clientservice.entity.Client;
import kz.sueta.clientservice.register.ClientRegister;
import kz.sueta.clientservice.repository.ClientDao;
import kz.sueta.clientservice.test.bean.TestBeanFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestBeanFactory.class)
public class ClientRegisterImplTest {

    @Autowired
    private ClientRegister clientRegister;

    @Autowired
    private ClientDao clientDao;

    @Test
    public void saveClient() {
        clientDao.deleteAll();

        PhoneSmsRequest phoneSmsRequest = new PhoneSmsRequest();
        phoneSmsRequest.phoneNumber = RND.strInt(11);
        phoneSmsRequest.smsCode = RND.strInt(4);

        //
        //
        Client client = clientRegister.saveClient(phoneSmsRequest);
        //
        //

        Client clientForTest = clientDao.findClientByClientIdAndActual(client.clientId, true);
        Assertions.assertNotNull(client);
        Assertions.assertEquals(clientForTest.client, client.client);
        Assertions.assertEquals(clientForTest.clientId, client.clientId);
        Assertions.assertEquals(clientForTest.phone, client.phone);
    }

    @Test
    public void getClient() {
        clientDao.deleteAll();

        PhoneSmsRequest phoneSmsRequest = new PhoneSmsRequest();
        phoneSmsRequest.phoneNumber = RND.strInt(11);
        phoneSmsRequest.smsCode = RND.strInt(4);
        Client client = clientRegister.saveClient(phoneSmsRequest);

        //
        //
        Client clientTest = clientRegister.getClient(phoneSmsRequest);
        //
        //

        Assertions.assertNotNull(clientTest);
        Assertions.assertEquals(clientTest.client, client.client);
        Assertions.assertEquals(clientTest.clientId, client.clientId);
        Assertions.assertEquals(clientTest.phone, client.phone);
    }
}
