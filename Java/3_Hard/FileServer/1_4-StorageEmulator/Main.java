package server;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static List<File> database = Arrays.asList(new File[10]);

    public static void main(String[] args) {

        String userInput;
        String command;

        do {
            userInput = scanner.nextLine();
            String[] inputs = userInput.split(" ");
            command = inputs[0];
            if (inputs.length > 1) {
                String fileName = inputs[1];
                if (!checkValidFileName(fileName)) {
                    printCannotAddFile(fileName);
                } else {
                    int index = Integer.parseInt(fileName.replaceAll("[^0-9]", "")) - 1;
                    switch (COMMANDS.forString(command)) {
                        case ADD -> addToDataBase(fileName, index);
                        case GET -> getFile(fileName, index);
                        case DELETE -> deleteFromDatabase(fileName, index);
                    }
                }

            }
        } while (!COMMANDS.forString(command).equals(COMMANDS.EXIT));
    }

    public static boolean checkValidFileName(String fileName) {
        boolean isValidName = true;
        // Getting first 4 characters from FileName
        String name = fileName.substring(0, 4);
        try {
            // Checking if any numbers in filename
            int index = Integer.parseInt(fileName.substring(4).replaceAll("[^0-9]", ""));
            // Checking if filename begins with "file" or if number is great than 10 or less than 1
            if (!name.equals("file") || index > 10 || index <= 0) {
                isValidName = false;
            }
        } catch (NumberFormatException invalidFileName){
            isValidName = false;
        }
        //fileName.matches("file[0-9]|file10");
        return isValidName;
    }
    public static void addToDataBase(String fileName, int index) {
        if (checkIfListIndexIsFree(index)) {
            File newFile = new File(fileName);
            database.set(index, newFile);
            System.out.println("The file " + fileName + " added successfully");
        } else {
            printCannotAddFile(fileName);
        }
    }

    public static void deleteFromDatabase(String fileName, int index) {
        if (!checkIfListIndexIsFree(index)) {
            database.set(index, null);
            System.out.println("The file " + fileName + " was deleted");
        } else {
            printFileNotFound(fileName);
        }
    }

    public static void getFile(String fileName, int index) {
        if (!checkIfListIndexIsFree(index)) {
            System.out.println("The file " + fileName + " was sent");
        } else {
            printFileNotFound(fileName);
        }
    }

    public static boolean checkIfListIndexIsFree(int index) {
        return database.get(index) == null;
    }

    public static void printFileNotFound(String fileName) {
        System.out.println("The file " + fileName + " not found");
    }

    public static void printCannotAddFile(String fileName) {
        System.out.println("Cannot add the file " + fileName);
    }


}
