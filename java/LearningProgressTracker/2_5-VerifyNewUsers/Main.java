package tracker;

import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

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
                        int studentsAdded;
                        studentsAdded = addStudents();
                        System.out.println("Total " + studentsAdded + " students have been added.");
                        break;
                }
            } else if (command.equals("back")){
                System.out.println("Enter 'exit' to exit the program.");
            } else {
                System.out.println("Unknown command!");
            }
        } while(!isExit);
        System.out.println("Bye!");

    }
    public static int addStudents() {
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

                    if (isValidFirstName(firstName) && isValidLastName(lastName) && isValidEmail(email)) {
                        System.out.println("The student has been added.");
                        studentsAdded++;
                    }
                }
            }
        } while (!isBack);
        return studentsAdded;
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

    public static boolean isValidEmail(String email) {
        if (email != null && email.matches("^[a-zA-Z0-9.!#$%&'*+/=? ^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)+$")) {
            return true;
        } else {
            System.out.println("Incorrect email.");
            return false;
        }
    }

    public static boolean isValidCredentials(String[] details) {
        int length = details.length;
        String email = details[details.length - 1];
        //&& email.matches("^[a-zA-Z0-9_\\-.]+@{1}\\w+\\.{1}\\w+")
        if (length > 2) {
            return true;
        } else {
            System.out.println("Incorrect credentials.");
            return false;
        }
    }

}
