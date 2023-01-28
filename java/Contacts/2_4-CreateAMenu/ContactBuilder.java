package contacts;

public class ContactBuilder implements Builder {

    private String name;
    private String surname;
    private String number;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public void setNumber(String number) {
        this.number = number;
    }

    public Contact getResult() {
        return new Contact(name,surname,number);
    }
}
