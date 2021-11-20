package kz.sueta.clientservice.bean;

import kz.sueta.clientservice.in_service.SmsSendService;
import kz.sueta.clientservice.in_service.fake.SmsSendServiceFake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsSendServiceFactory {

    @Bean
    public SmsSendService createSmsSendService() {
        return new SmsSendServiceFake();
    }
}
