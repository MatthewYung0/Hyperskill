package contacts.person;

import contacts.Contact;
import contacts.ContactFactory;

public class PersonFactory implements ContactFactory {
    @Override
    public Contact createContact() {
        return new Person(new PersonDetailsFactory());
    }
}
