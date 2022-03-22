package kz.sueta.clientservice.controller;

import kz.sueta.clientservice.dto.services.response.EventListResponse;
import kz.sueta.clientservice.register.EventRegister;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "*")
public class EventController {

    private final EventRegister eventRegister;

    public EventController(EventRegister eventRegister) {
        this.eventRegister = eventRegister;
    }

    @MessageMapping("/listEvents")
    @SendTo("/topic/listEvents")
    public EventListResponse listEvents() throws InterruptedException {
        Thread.sleep(10000);
        return eventRegister.listEvents();
    }
}
