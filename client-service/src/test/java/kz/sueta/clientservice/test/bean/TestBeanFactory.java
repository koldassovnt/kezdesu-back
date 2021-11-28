package kz.sueta.clientservice.test.bean;

import kz.sueta.clientservice.in_service.SmsSendService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestBeanFactory {

    @Bean
    @Primary
    public SmsSendService createSmsSendServiceForTest() {
        return new SmsSendServiceTest();
    }
}
