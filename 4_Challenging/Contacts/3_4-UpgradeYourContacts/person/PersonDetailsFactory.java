package contacts.person;

import contacts.Contact;
import contacts.ContactDetailsFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

import static contacts.ErrorHandler.*;

public class PersonDetailsFactory extends Contact implements ContactDetailsFactory {
    private Scanner scanner = new Scanner(System.in);
    private String surname;
    private LocalDate DOB = null;
    private String gender;

    public PersonDetailsFactory() {
        setName();
        setSurname();
        setDOB();
        setGender();
        setNumber();
        setCreationTime();
        setLastEditedTime();
    }
    @Override
    public void setName() {
        System.out.print("Enter the name: ");
        this.name = scanner.nextLine();
    }

    public void setSurname() {
        System.out.print("Enter the surname: ");
        this.surname = scanner.nextLine();
    }

    public void setDOB() {
        System.out.print("Enter the birth date: ");
        String dob = scanner.nextLine();
        if (isCorrectBirthdayFormat(dob)) {
            this.DOB = LocalDate.parse(dob);
        }
    }

    public void setGender() {
        System.out.print("Enter the gender (M, F): ");
        this.gender = scanner.nextLine();
        if (!isCorrectGender(gender)) {
            this.gender = "[no data]";
        }
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

    public String getSurname() { return this.surname; }

    public String getDOB() {
        if (this.DOB == null) {
            return "[no data]";
        } else {
            return DOB.toString();
        }
    }

    public String getGender() {
        return gender;
    }
}

