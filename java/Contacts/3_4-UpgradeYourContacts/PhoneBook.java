package contacts;

import contacts.organisation.Organisation;
import contacts.person.Person;
import contacts.types.ACTIONS;
import contacts.types.CONTACT_TYPE;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static contacts.ErrorHandler.isExit;

public class PhoneBook {

    public enum PERSON_FIELDS {
        NAME,
        SURNAME,
        BIRTH,
        GENDER,
        NUMBER;
    }

    public enum ORGANIZATION_FIELDS {
        ADDRESS,
        NUMBER;
    }

    private static final List<Contact> phonebook = new ArrayList<Contact>();
    private static Scanner scanner = new Scanner(System.in);

    public static int count() {
        return phonebook.size();
    }

    public static void add(Contact contact) {
        phonebook.add(contact);
        System.out.println("The record added.");
    }

    public static void remove() {
        list();
        System.out.print("Select a record: ");
        String input = scanner.nextLine();
        if (isCorrectInput(input, ACTIONS.REMOVE)) {
            phonebook.remove(Integer.parseInt(input) - 1);
        }
    }

    public static void editMenu() {
        list();
        System.out.print("Select a record: ");
        String input = scanner.nextLine();
        if (isCorrectInput(input, ACTIONS.EDIT)) {
            int index = Integer.parseInt(input) - 1;
            if (phonebook.get(index).getIsPerson()) {
                System.out.print("Select a field (name, surname, birth, gender, number): ");
                String fieldToChange = scanner.nextLine();
                if (fieldExists(CONTACT_TYPE.PERSON, fieldToChange.toUpperCase().trim())) {
                    editPerson(index, fieldToChange);
                }
            } else {
                System.out.print("Select a field (address, number): ");
                String fieldToChange = scanner.nextLine();
                if (fieldExists(CONTACT_TYPE.ORGANIZATION, fieldToChange.toUpperCase().trim())) {
                    editOrganisation(index, fieldToChange);
                }
            }
        }
    }

    public static void editOrganisation(int index, String fieldToChange) {
        ORGANIZATION_FIELDS orgFields = ORGANIZATION_FIELDS.valueOf(fieldToChange.toUpperCase().trim());
        switch (orgFields) {
            case ADDRESS -> {
                System.out.print("Enter address: ");
                ((Organisation) (phonebook.get(index))).setAddress(scanner.nextLine());
            }
            case NUMBER -> {
                System.out.print("Enter number: ");
                (phonebook.get(index)).setNumber(scanner.nextLine());
            }
        }
        phonebook.get(index).setLastEditedTime();
        System.out.println("The record updated!");
    }

    public static void editPerson(int index, String fieldToChange) {
        PERSON_FIELDS personFields = PERSON_FIELDS.valueOf(fieldToChange.toUpperCase().trim());
        switch (personFields) {
            case NAME -> {
                System.out.print("Enter name: ");
                (phonebook.get(index)).setName(scanner.nextLine());
            }
            case SURNAME -> {
                System.out.print("Enter surname: ");
                ((Person) (phonebook.get(index))).setSurname(scanner.nextLine());
            }
            case BIRTH -> {
                System.out.print("Enter birth date: ");
                ((Person) (phonebook.get(index))).setDOB(scanner.nextLine());
            }
            case GENDER -> {
                System.out.print("Enter gender: ");
                ((Person) (phonebook.get(index))).setGender(scanner.nextLine());
            }
            case NUMBER -> {
                System.out.print("Enter number: ");
                (phonebook.get(index)).setNumber(scanner.nextLine());
            }
        }
        phonebook.get(index).setLastEditedTime();
        System.out.println("The record updated!");
    }

    public static void list() {
        for (int i = 0; i < phonebook.size(); i++) {
            if (phonebook.get(i).getIsPerson()) {
                System.out.println(i + 1 + ". " + phonebook.get(i).getName() + " " + ((Person) phonebook.get(i)).getSurname());
            } else {
                System.out.println(i + 1 + ". " + phonebook.get(i).getName());
            }
        }
    }

    public static void info() {
        list();
        System.out.print("Select a record: ");
        String input = scanner.nextLine();
        if (isCorrectInput(input, ACTIONS.INFO)) {
            phonebook.get(Integer.parseInt(input) - 1).printInfo();
        }
    }

    private static boolean isCorrectRecord(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isCorrectInput(String input, ACTIONS action) {
        if (!isCorrectRecord(input)) {
            switch (action) {
                case EDIT -> {
                    System.out.println("No records to edit");
                    isExit(input);
                    return false;
                }
                case REMOVE -> {
                    System.out.println("No records to remove!");
                    isExit(input);
                    return false;
                }
                case INFO -> {
                    System.out.println("Incorrect input");
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean fieldExists(CONTACT_TYPE contactType, String fieldToChange) {
        try {
            if (contactType.equals(CONTACT_TYPE.PERSON)) {
                for (PERSON_FIELDS personField : PERSON_FIELDS.values()) {
                    if (personField.name().equals(fieldToChange)) {
                        return true;
                    }
                }
            } else {
                for (ORGANIZATION_FIELDS orgField : ORGANIZATION_FIELDS.values()) {
                    if (orgField.name().equals(fieldToChange)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid Field!");
        }
        return false;
    }


}
