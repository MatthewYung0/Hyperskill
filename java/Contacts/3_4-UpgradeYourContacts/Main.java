package contacts;

import contacts.types.ACTIONS;
import contacts.types.CONTACT_TYPE;

import java.util.Scanner;

public class Main extends PhoneBook {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ACTIONS action = null;
        CONTACT_TYPE type = null;
        do {
            try {
                System.out.print("\nEnter action (add, remove, edit, count, info, exit): ");
                action = ACTIONS.valueOf(scanner.nextLine().toUpperCase().trim());
                switch(action) {
                    case ADD:
                        System.out.print("Enter the type (person, organization): ");
                        type = CONTACT_TYPE.valueOf(scanner.nextLine().toUpperCase().trim());
                        ContactDirector contactDirector = new ContactDirector();
                        add(contactDirector.createContact(type));
                        break;
                    case REMOVE:
                        remove();
                        break;
                    case EDIT:
                        editMenu();
                        break;
                    case COUNT:
                        System.out.println("The Phone Book has " + count() + " records");
                        break;
                    case INFO:
                        info();
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid action");
            }
        } while (action != ACTIONS.EXIT);
    }

}
