package kz.sueta.clientservice.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class RndUtil {

    public static String getRndCodeForAuth() {
        return RandomStringUtils.randomNumeric(4);
    }

    public static String generateClientId() {
        return UUID.randomUUID().toString();
    }
}
