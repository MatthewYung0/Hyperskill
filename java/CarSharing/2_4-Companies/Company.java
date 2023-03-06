package carsharing;

public record Company(int ID, String name) {

    @Override
    public String toString() {
        return ID + ". " + name;
    }

}
