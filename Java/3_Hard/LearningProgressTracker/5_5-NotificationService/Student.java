package tracker;

public class Student {

    private final String firstName;
    private final String lastName;
    private final String email;

    private boolean[] hasPassedCourse = {false, false, false, false};
    private int javaPoints = 0;
    private int DSAPoints = 0;
    private int databasePoints = 0;
    private int springPoints = 0;

    private int JAVA_MAX = 600;
    private int DSA_MAX = 400;
    private int DATABASES_MAX = 480;
    private int SPRING_MAX = 550;

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        System.out.println("The student has been added.");
    }

    public void printPoints() {
        System.out.print("Java=" + this.javaPoints + "; DSA=" + this.DSAPoints + "; Databases=" + this.databasePoints + "; Spring=" + this.springPoints + '\n');
    }

    public Student setPoints(int[] points) {
        if (this.javaPoints + points[0] <= JAVA_MAX) {
            this.javaPoints += points[0];
        } else {
            this.javaPoints = JAVA_MAX;
        }

        if (this.DSAPoints + points[1] <= DSA_MAX) {
            this.DSAPoints += points[1];
        } else {
            this.DSAPoints = DSA_MAX;
        }

        if (this.databasePoints + points[2] <= DATABASES_MAX) {
            this.databasePoints += points[2];
        } else {
            this.databasePoints = DATABASES_MAX;
        }

        if (this.springPoints + points[3] <= SPRING_MAX) {
            this.springPoints += points[3];
        } else {
            this.springPoints = SPRING_MAX;
        }
        System.out.println("Points updated.");
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public int getJavaPoints() {
        return this.javaPoints;
    }

    public int getDSAPoints() {
        return this.DSAPoints;
    }

    public int getDatabasePoints() {
        return this.databasePoints;
    }

    public int getSpringPoints() {
        return this.springPoints;
    }

    public boolean getHasPassedNotCourse(int course) {
        return !hasPassedCourse[course];
    }

    public void setHasPassedCourse(int course) {
        this.hasPassedCourse[course] = true;
    }

}
