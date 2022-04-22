package kz.sueta.eventservice.scheduler;

import kz.sueta.eventservice.register.EventCrudRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class CloseEventTask {

    private final EventCrudRegister eventCrudRegister;

    @Autowired
    public CloseEventTask(EventCrudRegister eventCrudRegister) {
        this.eventCrudRegister = eventCrudRegister;
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void closeEvent() throws SQLException {
        eventCrudRegister.closeEvent();
    }
}
