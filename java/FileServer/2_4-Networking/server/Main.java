package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static List<File> database = Arrays.asList(new File[10]);

    public static void main(String[] args) {

        String address = "127.0.0.1";
        int port = 23456;

        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))){
            System.out.println("Server started!");
            Socket socket = server.accept();

            DataInputStream input = new DataInputStream(socket.getInputStream());
            String receivedMessage = input.readUTF();
            System.out.println("Received: " + receivedMessage);

            DataOutputStream output  = new DataOutputStream(socket.getOutputStream());
            String outputMessage = "All files were sent!";
            output.writeUTF(outputMessage);
            System.out.println("Sent: " + outputMessage);

            socket.close();
            server.close();
            output.close();
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

/*        String userInput;
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
                        case ADD:
                            addToDataBase(fileName, index);
                            break;
                        case GET:
                            getFile(fileName, index);
                            break;
                        case DELETE:
                            deleteFromDatabase(fileName, index);
                    }
                }

            }
        } while (!COMMANDS.forString(command).equals(COMMANDS.EXIT));*/
    }

    public static boolean checkValidFileName(String fileName) {
        return fileName.matches("file[0-9]|file10");
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
