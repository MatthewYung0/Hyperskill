package contacts.organisation;

import contacts.Contact;

public class Organisation extends Contact {

    public final boolean isPerson = false;
    private String address;
    private String DOB;
    private String gender;

    public Organisation(OrganisationDetailsFactory detailsFactory) {
        super.isPerson = detailsFactory.getIsPerson();
        super.name = detailsFactory.getName();
        super.number = detailsFactory.getNumber();
        this.address = detailsFactory.getAddress();
        this.createdTime = detailsFactory.getCreatedTime();
        this.lastEditedTime = detailsFactory.getCreatedTime();
    }

    @Override
    public boolean getIsPerson() {
        return isPerson;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getNumber() {
        return super.getNumber();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void printInfo() {
        System.out.println("Organization name: " + this.name);
        System.out.println("Address: " + this.address);
        System.out.println("Number: " + this.number);
        System.out.println("Time created: " + this.createdTime);
        System.out.println("Time last edit: " + this.lastEditedTime);
    }

}
