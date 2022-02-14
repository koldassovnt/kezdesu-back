package kz.sueta.clientservice.bean;

import kz.sueta.clientservice.in_service.SmsSendService;
import kz.sueta.clientservice.in_service.fake.SmsSendServiceFake;
import kz.sueta.clientservice.in_service.real.SmsSendServiceReal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsSendServiceFactory {

    private final SmsSendConfig smsSendConfig;

    @Autowired
    public SmsSendServiceFactory(SmsSendConfig smsSendConfig) {
        this.smsSendConfig = smsSendConfig;
    }

    @Bean
    public SmsSendService createSmsSendService() {

        if (smsSendConfig.useFake()) {
            return new SmsSendServiceFake();
        }

        return new SmsSendServiceReal();
    }
}
