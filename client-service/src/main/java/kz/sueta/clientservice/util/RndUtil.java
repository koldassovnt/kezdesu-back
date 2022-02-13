package kz.sueta.clientservice.util;

import org.apache.commons.lang3.RandomStringUtils;

public class RndUtil {

    public static String getRndCodeForAuth() {
        return RandomStringUtils.randomNumeric(4);
    }
}
