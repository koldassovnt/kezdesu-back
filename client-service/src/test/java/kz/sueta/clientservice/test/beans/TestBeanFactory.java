package kz.sueta.clientservice.test.beans;

import kz.sueta.clientservice.in_service.SmsSendService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestBeanFactory {

    @Bean
    public SmsSendService createSmsSendService() {
        return new SmsSendServiceTest();
    }
}
