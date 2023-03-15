package contacts;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static contacts.ErrorHandler.isRightNumberFormat;

public class PhoneBook {

    public enum PHONEBOOK_FIELDS {
        NAME,
        SURNAME,
        NUMBER;
    }

    List<Contact> phonebook = new ArrayList<Contact>();

    public int count() {
        return phonebook.size();
    }

    public void add(Contact contact) {
        phonebook.add(contact);
    }

    public void remove(String record) {
        phonebook.remove(Integer.parseInt(record)-1);
    }

    public void edit(String record, String field) {
        Scanner scanner = new Scanner(System.in);
        int index = Integer.parseInt(record) - 1;
        String change;
        try {
            switch (PHONEBOOK_FIELDS.valueOf(field.toUpperCase().trim())) {
                case NAME:
                    System.out.print("Enter name: ");
                    change = scanner.nextLine();
                    phonebook.get(index).setName(change);
                    break;
                case SURNAME:
                    System.out.print("Enter surname: ");
                    change = scanner.nextLine();
                    phonebook.get(index).setSurname(change);
                    break;
                case NUMBER:
                    System.out.print("Enter number: ");
                    change = scanner.nextLine();
                    phonebook.get(index).setNumber(isRightNumberFormat(change));
                    break;
            }
        } catch (Exception e) { }
    }


    public void list() {
        for (int i = 0; i < phonebook.size(); i++) {
            System.out.println(i + 1 + ". " + phonebook.get(i).getName() + " " + phonebook.get(i).getSurname() + ", " + phonebook.get(i).getNumber());
        }
    }

}
