package kz.sueta.pushservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PushServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PushServiceApplication.class, args);
	}

}
