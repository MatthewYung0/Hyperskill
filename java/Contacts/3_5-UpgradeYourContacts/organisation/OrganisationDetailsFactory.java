package contacts.organisation;

import contacts.Contact;
import contacts.ContactDetailsFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

import static contacts.ErrorHandler.*;

public class OrganisationDetailsFactory extends Contact implements ContactDetailsFactory {

    public final boolean isPerson = false;
    private Scanner scanner = new Scanner(System.in);
    private String address;

    public OrganisationDetailsFactory() {
        setName();
        setAddress();
        setNumber();
        setCreationTime();
        setLastEditedTime();
    }

    @Override
    public void setName() {
        System.out.print("Enter the organization name: ");
        this.name = scanner.nextLine();
    }


    @Override
    public void setNumber() {
        System.out.print("Enter the number: ");
        this.number = scanner.nextLine();
        if (!isCorrectNumberFormat(this.number)) {
            this.number = "[no data]";
        }
    }

    public void setAddress() {
        System.out.print("Enter the address: ");
        this.address = scanner.nextLine();
    }

    @Override
    public void setCreationTime() {
        this.createdTime = LocalDateTime.now().withSecond(0).withNano(0);
    }

    @Override
    public void setLastEditedTime() {
        this.lastEditedTime = LocalDateTime.now().withSecond(0).withNano(0);
    }

    public boolean getIsPerson() { return this.isPerson; }

    public String getAddress() { return this.address; }
}

