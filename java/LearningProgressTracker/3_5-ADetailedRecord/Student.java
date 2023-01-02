package tracker;

public class Student {

    private String firstName;
    private String lastName;
    private String email;
    private int javaPoints = 0;
    private int DSAPoints = 0;
    private int databasePoints = 0;
    private int springPoints = 0;

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        System.out.println("The student has been added.");
    }

    public void printPoints() {
        System.out.print("Java=" + this.javaPoints + "; DSA=" + this.DSAPoints + "; Databases=" + this.databasePoints + "; Spring=" + this.springPoints);
    }

    public Student setPoints(int javaPoints, int DSAPoints, int databasePoints, int springPoints) {
            this.javaPoints = javaPoints;
            this.DSAPoints = DSAPoints;
            this.databasePoints = databasePoints;
            this.springPoints = springPoints;
            System.out.println("Points updated.");
            return this;
    }

    public String getEmail() {
        return email;
    }
}
