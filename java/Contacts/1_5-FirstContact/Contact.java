package contacts;

import java.util.Scanner;

public class Contact {

    private String firstName;
    private String lastName;
    private String number;

    public Contact() {
        Scanner scanner = new Scanner(System.in);
        this.firstName = requestFirstName(scanner);
        this.lastName = requestLastName(scanner);
        this.number = requestNumber(scanner);
    }

    public String requestFirstName(Scanner scanner) {
        System.out.println("Enter the name of the person:");
        return scanner.nextLine().trim();
    }

    public String requestLastName(Scanner scanner) {
        System.out.println("Enter the surname of the person:");
        return scanner.nextLine().trim();
    }

    public String requestNumber(Scanner scanner) {
        System.out.println("Enter the number:");
        return scanner.nextLine().trim();
    }

}
