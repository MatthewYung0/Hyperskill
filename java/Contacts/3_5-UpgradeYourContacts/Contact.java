package contacts;

import java.time.LocalDateTime;
import java.util.Scanner;

import static contacts.ErrorHandler.isCorrectNumberFormat;

public abstract class Contact {
    private Scanner scanner = new Scanner(System.in);
    protected boolean isPerson;
    protected String name;
    protected String number;
    protected LocalDateTime createdTime;
    protected LocalDateTime lastEditedTime;

    public boolean getIsPerson() { return isPerson; }

    public String getName() { return this.name; }

    public String getNumber() {
        return this.number;
    }

    public LocalDateTime getCreatedTime() {
        return this.createdTime;
    }

    public void setName(String name) { this.name = name; }

    public void setNumber(String number) {
        if (isCorrectNumberFormat(number)) {
            this.number = number;
        } else {
            this.number = "[no data]";
        }
    }

    public void setNumber() {
        System.out.print("Enter the number: ");
        this.number = scanner.nextLine();
        if (!isCorrectNumberFormat(this.number)) {
            this.number = "[no data]";
        }
    }

    public void setLastEditedTime() { this.lastEditedTime = LocalDateTime.now().withSecond(0).withNano(0); }

    public void printInfo() { };

}
