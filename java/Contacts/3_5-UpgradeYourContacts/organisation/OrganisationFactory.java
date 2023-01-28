package contacts.organisation;

import contacts.Contact;
import contacts.ContactFactory;

public class OrganisationFactory implements ContactFactory  {

    @Override
    public Contact createContact() {
        return new Organisation(new OrganisationDetailsFactory());
    }

}
