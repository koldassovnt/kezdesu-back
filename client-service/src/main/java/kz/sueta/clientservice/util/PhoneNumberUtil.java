package kz.sueta.clientservice.util;

import org.apache.logging.log4j.util.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberUtil {

    public static boolean isValid(String phone) {

        if (!Strings.isNotEmpty(phone)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");
        Matcher match = pattern.matcher(phone);

        return match.find() && match.group().equals(phone);
    }
}
