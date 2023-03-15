package contacts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorHandler {

    public static String isRightNumberFormat(String number) {
        Pattern pattern = Pattern.compile("\\+?" +
                "((\\([0-9A-Za-z]{1,}\\)|[0-9A-Za-z]{1,})"
                +"|([0-9A-Za-z]{1,}[ -]\\([0-9A-Za-z]{2,}\\))|[0-9A-Za-z]{1,}[ -][0-9A-Za-z]{2,})"
                +"([ -][0-9A-Za-z]{2,}[ -]?)*");
        Matcher matcher = pattern.matcher(number);
        if (matcher.matches()) {
            return number;
        } else {
            System.out.println("Wrong number format!");
            return "[no number]";
        }
    }

}
