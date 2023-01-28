package contacts;

import contacts.types.ACTIONS;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorHandler {

    public static boolean isCorrectNumberFormat(String number) {
        Pattern pattern = Pattern.compile("\\+?" +
                "((\\([0-9A-Za-z]{1,}\\)|[0-9A-Za-z]{1,})"
                +"|([0-9A-Za-z]{1,}[ -]\\([0-9A-Za-z]{2,}\\))|[0-9A-Za-z]{1,}[ -][0-9A-Za-z]{2,})"
                +"([ -][0-9A-Za-z]{2,}[ -]?)*");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            System.out.println("Wrong number format!");
            return false;
        }
        return true;
    }

    public static boolean isCorrectBirthdayFormat(String birthday) {
        try {
            LocalDate.parse(birthday);
            return true;
        } catch (Exception e) {
            System.out.println("Bad birth date!");
            return false;
        }
    }

    public static boolean isCorrectGender(String gender) {
        if (!gender.equals("M") && !gender.equals("F")) {
            System.out.println("Bad gender!");
            return false;
        }
        return true;
    }

    public static void isExit(String input) {
        if (input.toUpperCase().equals(ACTIONS.EXIT.toString())) {
            System.exit(0);
        }
    }

}
