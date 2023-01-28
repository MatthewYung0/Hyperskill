package contacts;

public class ActionHandler extends PhoneBook {

    public void addPhoneBook(Contact newContact) {
        add(newContact);
    }

    public void removePhoneBook(String record) {
        remove(record);
    }

    public void editPhoneBook(String record, String field) {
        edit(record, field);
    }

    public int countPhoneBook() {
        return count();
    }

    public void listPhoneBook() {
        list();
    }

}
