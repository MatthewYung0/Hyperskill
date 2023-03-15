package contacts;

import java.util.Scanner;

import static contacts.ErrorHandler.isRightNumberFormat;

public class ContactDirector {

    public static Scanner scanner = new Scanner(System.in);

    public void buildContact(Builder builder) {
        String name = getName();
        String surname = getSurname();
        String number = getNumber();
        builder.setName(name);
        builder.setSurname(surname);
        builder.setNumber(number);
    }

    private String getName() {
        System.out.print("Enter the name: ");
        return scanner.nextLine().trim();
    }

    private String getSurname() {
        System.out.print("Enter the surname: ");
        return scanner.nextLine().trim();
    }

    private String getNumber() {
        System.out.print("Enter the number: ");
        return isRightNumberFormat(scanner.nextLine().trim());
    }

}
