package contacts;

import contacts.organisation.OrganisationFactory;
import contacts.person.PersonFactory;
import contacts.types.CONTACT_TYPE;

public class ContactDirector {

    public Contact createContact(CONTACT_TYPE contactType) {
        if (contactType.equals(CONTACT_TYPE.PERSON)) {
            PersonFactory personFactory = new PersonFactory();
            return personFactory.createContact();
        } else {
            OrganisationFactory organisationFactory = new OrganisationFactory();
            return organisationFactory.createContact();
        }
    }

}
