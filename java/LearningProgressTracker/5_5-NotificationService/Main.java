package tracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Database database = new Database();

    public static void main(String[] args) {
        System.out.println("Learning Progress Tracker");
        boolean isExit = false;
        String command;
        do {
            command = scanner.nextLine();
            if (command.equals("exit")) {
                isExit = true;
            } else if (command.isBlank()) {
                System.out.println("No input.");
            } else if (command.equals("back")) {
                System.out.println("Enter 'exit' to exit the program.");
            } else if (MAIN_COMMANDS.findByName(command) != null) {
                switch (MAIN_COMMANDS.mainCommandMap.get(command)) {
                    case ADD_STUDENTS:
                        createStudent();
                        break;
                    case LIST:
                        listStudentIDs();
                        break;
                    case ADD_POINTS:
                        addPoints();
                        break;
                    case FIND:
                        findStudent();
                        break;
                    case STATISTICS:
                        statistics();
                        break;
                    case NOTIFY:
                        notify_student();
                        break;
                }
            } else {
                System.out.println("Unknown command!");
            }
        } while (!isExit);
        System.out.println("Bye!");
    }

    public static void notify_student() {
        int noOfStudentsNotified = 0;
        for (Map.Entry<Integer, Student> entry : database.getDatabase().entrySet()) {
            boolean hasPassed = false;
            if (entry.getValue().getJavaPoints() == 600 && entry.getValue().getHasPassedNotCourse(0)) {
                printNotification(entry.getValue().getEmail(), entry.getValue().getFirstName(), entry.getValue().getLastName(), "Java");
                entry.getValue().setHasPassedCourse(0);
                hasPassed = true;
            }
            if (entry.getValue().getDSAPoints() == 400 && entry.getValue().getHasPassedNotCourse(1)) {
                printNotification(entry.getValue().getEmail(), entry.getValue().getFirstName(), entry.getValue().getLastName(), "DSA");
                entry.getValue().setHasPassedCourse(1);
                hasPassed = true;
            }
            if (entry.getValue().getDatabasePoints() == 480 && entry.getValue().getHasPassedNotCourse(2)) {
                printNotification(entry.getValue().getEmail(), entry.getValue().getFirstName(), entry.getValue().getLastName(), "Databases");
                entry.getValue().setHasPassedCourse(2);
                hasPassed = true;
            }
            if (entry.getValue().getSpringPoints() == 550 && entry.getValue().getHasPassedNotCourse(3)) {
                printNotification(entry.getValue().getEmail(), entry.getValue().getFirstName(), entry.getValue().getLastName(), "Spring");
                entry.getValue().setHasPassedCourse(3);
                hasPassed = true;
            }
            if (hasPassed) {
                noOfStudentsNotified++;
            }
        }
        System.out.println("Total " + noOfStudentsNotified + " have been notified.");
    }

    public static void printNotification(String email, String firstName, String lastName, String course) {
        System.out.println("To: " + email);
        System.out.println("Re: Your Learning Progress");
        System.out.println("Hello, " + firstName + " " + lastName + "! You have accomplished our " + course + " course!");
    }

    public static void statistics() {
        boolean isBack = false;
        String input;
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        database.printStatistics();
        do {
            input = scanner.nextLine();
            if (input.equals("back")) {
                isBack = true;
            } else if (STATISTIC_COMMANDS.findByName(input.toUpperCase()) != null){
                Map<Integer, Integer> topStudents;
                if (STATISTIC_COMMANDS.commandMap.get(input.toUpperCase()).equals(STATISTIC_COMMANDS.JAVA)) {
                    System.out.println("Java");
                    System.out.println("id" + "     " + "points" + "      " + "completed");
                    topStudents = database.getTopStudents(Database.COURSES.JAVA);
                    for (Map.Entry<Integer, Integer> entry : topStudents.entrySet()) {
                        BigDecimal bd = BigDecimal.valueOf(((double) entry.getValue() /(double) 600)*100);
                        bd = bd.setScale(1, RoundingMode.HALF_UP);
                        String output = String.format("%d %7d %8s", entry.getKey(), entry.getValue(), bd);
                        System.out.println(output + "%");
                    }
                } else if (STATISTIC_COMMANDS.commandMap.get(input.toUpperCase()).equals(STATISTIC_COMMANDS.DSA)) {
                    System.out.println("DSA");
                    System.out.println("id" + "     " + "points" + "      " + "completed");
                    topStudents = database.getTopStudents(Database.COURSES.DSA);
                    for (Map.Entry<Integer, Integer> entry : topStudents.entrySet()) {
                        BigDecimal bd = BigDecimal.valueOf(((double) entry.getValue() /(double) 400)*100);
                        bd = bd.setScale(1, RoundingMode.HALF_UP);
                        String output = String.format("%d %7d %8s", entry.getKey(), entry.getValue(), bd);
                        System.out.println(output + "%");
                    }
                } else if (STATISTIC_COMMANDS.commandMap.get(input.toUpperCase()).equals(STATISTIC_COMMANDS.DATABASES)) {
                    System.out.println("Databases");
                    System.out.println("id" + "     " + "points" + "      " + "completed");
                    topStudents = database.getTopStudents(Database.COURSES.DATABASES);
                    for (Map.Entry<Integer, Integer> entry : topStudents.entrySet()) {
                        BigDecimal bd = BigDecimal.valueOf(((double) entry.getValue() /(double) 480)*100);
                        bd = bd.setScale(1, RoundingMode.HALF_UP);
                        String output = String.format("%d %7d %8s", entry.getKey(), entry.getValue(), bd);
                        System.out.println(output + "%");
                    }
                } else if (STATISTIC_COMMANDS.commandMap.get(input.toUpperCase()).equals(STATISTIC_COMMANDS.SPRING)) {
                    System.out.println("Spring");
                    System.out.println("id" + "     " + "points" + "      " + "completed");
                    topStudents = database.getTopStudents(Database.COURSES.SPRING);
                    for (Map.Entry<Integer, Integer> entry : topStudents.entrySet()) {
                        BigDecimal bd = BigDecimal.valueOf(((double) entry.getValue() /(double) 550)*100);
                        bd = bd.setScale(1, RoundingMode.HALF_UP);
                        String output = String.format("%d %7d %8s", entry.getKey(), entry.getValue(), bd);
                        System.out.println(output + "%");
                    }
                }
            } else {
                System.out.println("Unknown course.");
            }
        } while(!isBack);
    }

    public static void createStudent() {
        int studentsAdded = 0;
        boolean isBack = false;
        String input;
        System.out.println("Enter student credentials or 'back' to return:");
        do {
            input = scanner.nextLine();
            if (input.equals("back")) {
                isBack = true;
            } else {
                if (isValidCredentials(input.split(" "))) {
                    int firstIndex = input.indexOf(" ");
                    int lastIndex = input.lastIndexOf(" ");
                    String firstName = input.substring(0, firstIndex).trim();
                    String lastName = input.substring(firstIndex, lastIndex).trim();
                    String email = input.substring(lastIndex).trim();
                    if (isValidFirstName(firstName) && isValidLastName(lastName) && isValidEmailName(email) && !isDuplicateEmail(email)) {
                        addToDatabase(firstName, lastName, email, generateID());
                        studentsAdded++;
                    }
                }
            }
        } while (!isBack);
        System.out.println("Total " + studentsAdded + " students have been added.");
    }

    public static void addPoints() {
        boolean isBack = false;
        String input;
        System.out.println("Enter an id and points or 'back' to return:");
        do {
            input = scanner.nextLine();
            if (input.equals("back")) {
                isBack = true;
            } else {
                try {
                    if (isValidID(input) && arePointsCorrect(input)) {
                        String[] pointsAsString = input.split(" ");
                        int ID = Integer.parseInt(pointsAsString[0]);
                        int[] pointsOnly = new int[]{Integer.parseInt(pointsAsString[1]), Integer.parseInt(pointsAsString[2]), Integer.parseInt(pointsAsString[3]), Integer.parseInt(pointsAsString[4])};
                        database.updateEnrollment(ID, pointsOnly);
                        database.getDatabase().put(ID, database.getDatabase().get(ID).setPoints(pointsOnly));
                        database.updateActivity(pointsOnly);
                        database.updateAveragePoints();
                    } else {
                        throw new Exception() {};
                    }
                } catch (Exception e) {
                    System.out.println("Incorrect points format.");
                }
            }
        } while (!isBack);
    }

    public static void addToDatabase(String firstName, String lastName, String email, int ID) {
        Student student = new Student(firstName, lastName, email);
        database.getDatabase().put(ID, student);
    }

    public static void findStudent() {
        System.out.println("Enter an id or 'back' to return:");
        boolean isBack = false;
        String input;
        do {
            input = scanner.nextLine();
            if (input.equals("back")) {
                isBack = true;
            } else if (isValidID(input)) {
                Student temp = database.getDatabase().get(Integer.parseInt(input));
                System.out.print(input + " points: ");
                temp.printPoints();
            }
        } while (!isBack);
    }

    public static void listStudentIDs() {
        if (database.getDatabase().size() == 0) {
            System.out.println("No students found.");
        } else {
            System.out.println("Students:");
            for (Integer ID : database.getDatabase().keySet()) {
                System.out.println(ID);
            }
        }
    }

    public static int generateID() {
        boolean isValidID = false;
        int ID = 0;
        while (!isValidID) {
            ID = (int) Math.floor((Math.random() * (99999 - 10000)) + 10000);
            if (!database.getDatabase().containsKey(ID)) {
                isValidID = true;
            }
        }
        return ID;
    }

    public static boolean arePointsCorrect(String input) {
        String[] points = input.split(" ");
        try {
            if (points.length == 5 &&
                    Integer.parseInt(points[1]) >= 0 &&
                    Integer.parseInt(points[2]) >= 0 &&
                    Integer.parseInt(points[3]) >= 0 &&
                    Integer.parseInt(points[4]) >= 0
            ) {
                return true;
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDuplicateEmail(String email) {
        for (Student student : database.getDatabase().values()) {
            if (student.getEmail().equals(email)) {
                System.out.println("This email is already taken.");
                return true;
            }
        }
        return false;
    }

    public static boolean isValidID(String input) {
        try {
            String[] inputs = input.split(" ");
            if (!database.getDatabase().containsKey(Integer.parseInt(inputs[0]))) {
                throw new Exception() {};
            }
            return true;
        } catch (Exception incorrectIDType) {
            System.out.println("No student is found for id=" + input);
            return false;
        }
    }

    public static boolean isValidFirstName(String firstName) {
        if (firstName.matches("^[a-zA-Z]([a-zA-Z]|[-'](?=[^-']))*[a-zA-Z]$")) {
            return true;
        } else {
            System.out.println("Incorrect first name.");
            return false;
        }
    }

    public static boolean isValidLastName(String lastName) {
        if (lastName.matches("[a-zA-Z]([a-zA-Z]|[-' ](?=[^-' ]))*[a-zA-Z]$")) {
            return true;
        } else {
            System.out.println("Incorrect last name.");
            return false;
        }
    }

    public static boolean isValidEmailName(String email) {
        if (email != null && email.matches("^[a-zA-Z0-9.!#$%&'*+/=? ^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)+$")) {
            return true;
        } else {
            System.out.println("Incorrect email.");
            return false;
        }
    }

    public static boolean isValidCredentials(String[] details) {
        if (details.length > 2) {
            return true;
        } else {
            System.out.println("Incorrect credentials.");
            return false;
        }
    }

}
