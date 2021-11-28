package kz.sueta.clientservice.bean;

import org.springframework.context.annotation.Description;

@Description("Configurations for sms send service")
public interface SmsSendConfig {

    @Description("Use Fake Param")
    boolean useFake();
}
