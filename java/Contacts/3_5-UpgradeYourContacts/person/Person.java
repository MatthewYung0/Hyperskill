package contacts.person;

import contacts.Contact;

import static contacts.ErrorHandler.isCorrectBirthdayFormat;
import static contacts.ErrorHandler.isCorrectGender;

public class Person extends Contact {

    public final boolean isPerson = true;
    private String surname;
    private String DOB;
    private String gender;

    public Person(PersonDetailsFactory detailsFactory) {
        super.name = detailsFactory.getName();
        super.number = detailsFactory.getNumber();
        this.surname = detailsFactory.getSurname();
        this.DOB = detailsFactory.getDOB();
        this.gender = detailsFactory.getGender();
        this.createdTime = detailsFactory.getCreatedTime();
        this.lastEditedTime = detailsFactory.getCreatedTime();
    }

    @Override
    public boolean getIsPerson() {
        return isPerson;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getNumber() {
        return super.getNumber();
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDOB(String DOB) {
        if (isCorrectBirthdayFormat(DOB)) {
            this.DOB = DOB;
        } else {
            this.DOB = "[no data]";
        }
    }

    public void setGender(String gender) {
        if (isCorrectGender(gender)) {
            this.gender = gender;
        } else {
            this.gender = "[no data]";
        }
    }

    @Override
    public void printInfo() {
        System.out.println("Name: " + this.name);
        System.out.println("Surname: " + this.surname);
        System.out.println("Birth date: " + this.DOB);
        System.out.println("Gender: " + this.gender);
        System.out.println("Number: " + this.number);
        System.out.println("Time created: " + this.createdTime);
        System.out.println("Time last edit: " + this.lastEditedTime);
    }

}
