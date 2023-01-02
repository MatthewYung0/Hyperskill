package tracker;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static HashMap<Integer, Student> database = new HashMap<>();

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
            } else if (COMMANDS.commandMap.containsKey(command)) {
                switch (COMMANDS.commandMap.get(command)) {
                    case ADD_STUDENT:
                        addStudents();
                        break;
                    case LIST:
                        listStudentIDs();
                        break;
                    case ADD_POINTS:
                        addPoints();
                        break;
                    case FIND:
                        findPoints();
                        break;
                }
            } else if (command.equals("back")) {
                System.out.println("Enter 'exit' to exit the program.");
            } else {
                System.out.println("Unknown command!");
            }
        } while (!isExit);
        System.out.println("Bye!");

    }

    public static void findPoints() {
        System.out.println("Enter an id or 'back' to return:");
        boolean isBack = false;
        String input;
        do {
            input = scanner.nextLine();
            if (input.equals("back")) {
                isBack = true;
            } else if (checkIfValidID(input)) {
                Student temp = database.get(Integer.parseInt(input));
                System.out.print(input + " points: ");
                temp.printPoints();
            }
        } while (!isBack);
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
                    if (checkIfValidID(input) && IsPointsCorrect(input)) {
                        int ID = Integer.parseInt(input.substring(0, input.indexOf(" ")));
                        String[] points = input.split(" ");
                        database.put(ID, database.get(ID).
                                setPoints(Integer.parseInt(points[1]),
                                        Integer.parseInt(points[2]),
                                        Integer.parseInt(points[3]),
                                        Integer.parseInt(points[4])));
                    } else {
                        throw new Exception() {};
                    }
                } catch (Exception e) {
                    System.out.println("Incorrect points format.");
                }
            }
        } while (!isBack);
    }

    public static boolean IsPointsCorrect(String input) {
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

    public static void listStudentIDs() {
        if (database.size() == 0) {
            System.out.println("No students found.");
        } else {
            System.out.println("Students:");
            for (Integer ID : database.keySet()) {
                System.out.println(ID);
            }
        }
    }

    public static void addStudents() {
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

    public static void addToDatabase(String firstName, String lastName, String email, int ID) {
        Student student = new Student(firstName, lastName, email);
        database.put(ID, student);
    }

    public static boolean isDuplicateEmail(String email) {
        for (Student student : database.values()) {
            if (student.getEmail().equals(email)) {
                System.out.println("This email is already taken.");
                return true;
            }
        }
        return false;
    }

    public static int generateID() {
        boolean isValidID = false;
        int ID = 0;
        while (!isValidID) {
            ID = (int) Math.floor(Math.random() * (10000 + 1));
            if (!database.containsKey(ID)) {
                isValidID = true;
            }
        }
        return ID;
    }

    public static boolean checkIfValidID(String input) {
        try {
            String[] inputs = input.split(" ");
            if (!database.containsKey(Integer.parseInt(inputs[0]))) {
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
