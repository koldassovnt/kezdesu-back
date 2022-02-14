package kz.sueta.clientservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final Environment env;

    @Autowired
    public TestController(Environment env) {
        this.env = env;
    }

    @GetMapping("/status")
    public String status() {
        return "WORKING CLIENT SERVICE ON PORT="
                + env.getProperty("local.server.port")
                + ", token=" + env.getProperty("token.secret");
    }
}