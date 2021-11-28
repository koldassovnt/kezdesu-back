package kz.sueta.clientservice.bean.impl;

import kz.sueta.clientservice.bean.SmsSendConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource({"classpath:config.properties"})
public class SmsSendConfigReal implements SmsSendConfig {

    @Value("${smsSendServiceUseFake}")
    private boolean useFake;

    @Override
    public boolean useFake() {
        return useFake;
    }
}
