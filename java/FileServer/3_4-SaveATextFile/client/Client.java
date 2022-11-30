package client;

import common.HTTP_CODES;
import common.HTTP_COMMANDS;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client {
    static final String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "server" + File.separator + "data" + File.separator;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {

        // Sleep used because it seems JetBrains is executing the client main first instead of the server main.
        // Delayed startup to ensure that server will be running first instead of the client
        TimeUnit.SECONDS.sleep(1);
        String address = "127.0.0.1";
        int port = 23456;

        String fileName;

        try (Socket socket = new Socket(InetAddress.getByName(address), port)) {
            //boolean endServer = false;
            //do {
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            System.out.print("\nEnter action (1 - get a file, 2 - create a file, 3 - delete a file): ");
            String command = scanner.nextLine();

            if (HTTP_COMMANDS.forString(command).equals(HTTP_COMMANDS.EXIT)) {
                output.writeUTF(command);
                System.out.println("The request was sent.");
                //endServer = true;
            } else {
                System.out.print("Enter filename: ");
                fileName = scanner.nextLine();
                switch (HTTP_COMMANDS.forString(command)) {
                    case GET:
                        output.writeUTF(get(fileName));
                        break;
                    case PUT:
                        String message = add(fileName);
                        output.writeUTF(message);
                        break;
                    case DELETE:
                        output.writeUTF(delete(fileName));
                        break;
                }
                System.out.println("The request was sent.");
                String code = input.readUTF();
                printMessage(HTTP_COMMANDS.forString(command), code, fileName);
            }
            closeInputAndOutput(input, output);
            //} while (!endServer);
        } catch (IOException e) {
            System.out.println("Could not connect to server!");
        }

    }

    public static void closeInputAndOutput(DataInputStream input, DataOutputStream output) throws IOException {
        input.close();
        output.close();
    }

    public static void printMessage(HTTP_COMMANDS request, String code, String fileName) {
        int number = Integer.parseInt(code);
        if (number == HTTP_CODES.SUCCESS.getCode()) {
            if (request.equals(HTTP_COMMANDS.PUT)) {
                System.out.print("The response says that the file was created!");
            } else if (request.equals(HTTP_COMMANDS.GET)) {
                System.out.print("The content of the file is: ");
                printFromFile(fileName);
            } else if (request.equals(HTTP_COMMANDS.DELETE)) {
                System.out.print("The response says that the file was successfully deleted!");
            }
        } else {
            if (request.equals(HTTP_COMMANDS.PUT)) {
                System.out.print("The response says that creating the file was forbidden!");
            } else if (request.equals(HTTP_COMMANDS.DELETE) || request.equals(HTTP_COMMANDS.GET)) {
                System.out.print("The response says that the file was not found!");
            }
        }
        System.out.print('\n');
    }

    public static void printFromFile(String fileName) {
        try {
            File file = new File(path, fileName);
            Scanner scannerGet = new Scanner(file);
            while (scannerGet.hasNextLine()) {
                System.out.print(scannerGet.nextLine() + " ");
            }
            scannerGet.close();
        } catch (FileNotFoundException e) {
            System.out.println(fileName + " not found!");
        }
    }

    public static String add(String fileName) {
        System.out.print("Enter file content: ");
        String fileContent = scanner.nextLine();
        return HTTP_COMMANDS.PUT + " " + fileName + " " + fileContent;
    }

    public static String get(String fileName) {
        return HTTP_COMMANDS.GET + " " + fileName;
    }

    public static String delete(String fileName) {
        return HTTP_COMMANDS.DELETE + " " + fileName;
    }

}
