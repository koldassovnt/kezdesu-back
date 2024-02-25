package kz.sueta.clientservice.beans.impl;

import kz.sueta.clientservice.beans.SmsSendConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SmsSendConfigReal implements SmsSendConfig {

    private final Environment environment;

    @Autowired
    public SmsSendConfigReal(Environment environment) {
        this.environment = environment;
    }

    @Override
    public boolean useFake() {
        String useFakeValue = environment.getProperty("smsSendServiceUseFake");
        return Objects.equals("true", useFakeValue);
    }
}
