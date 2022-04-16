package kz.sueta.eventservice.util;

import com.google.common.base.Strings;
import lombok.SneakyThrows;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {

    @SneakyThrows
    public static Connection getConnection(Environment environment) {
        String url = environment.getProperty("spring.datasource.url");
        String user = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");

        if (Strings.isNullOrEmpty(url) || Strings.isNullOrEmpty(user) || Strings.isNullOrEmpty(password)) {
            throw new RuntimeException("WlF8tnr2KZ :: database properties is not specified!");
        }

        return DriverManager.getConnection(url, user, password);
    }
}
