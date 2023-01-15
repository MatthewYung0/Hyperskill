package contacts;

import java.util.Scanner;

public class Main extends ActionHandler {
    public static Scanner scanner = new Scanner(System.in);
    public static ActionHandler actionHandler = new ActionHandler();
    public static ContactDirector contactDirector = new ContactDirector();
    public static ContactBuilder contactBuilder = new ContactBuilder();

    public static void main(String[] args) {
        ACTIONS action = null;
        do {
            try {
                System.out.print("Enter action (add, remove, edit, count, list, exit): ");
                action = ACTIONS.valueOf(scanner.nextLine().toUpperCase().trim());
                switch (action) {
                    case ADD:
                        contactDirector.buildContact(contactBuilder);
                        Contact newContact = contactBuilder.getResult();
                        actionHandler.addPhoneBook(newContact);
                        System.out.println("The record added.");
                        break;
                    case REMOVE:
                        if (actionHandler.count() != 0) {
                            actionHandler.listPhoneBook();
                            System.out.print("Select a record: ");
                            String record = scanner.nextLine().trim();
                            actionHandler.removePhoneBook(record);
                            System.out.println("The record added.");
                        } else {
                            System.out.println("No records to remove!");
                        }
                        break;
                    case EDIT:
                        if (actionHandler.count() != 0) {
                            actionHandler.listPhoneBook();
                            System.out.print("Select a record: ");
                            String record = scanner.nextLine().trim();
                            System.out.print("Select a field: ");
                            String field = scanner.nextLine().trim();
                            actionHandler.editPhoneBook(record,field);
                            System.out.println("The record added.");
                        } else {
                            System.out.println("No records to edit!");
                        }
                        break;
                    case COUNT:
                        System.out.println("The Phone Book has " + actionHandler.countPhoneBook() + " records");
                        break;
                    case LIST:
                        actionHandler.listPhoneBook();
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid action");
            }
        } while (action != ACTIONS.EXIT);
    }

}
