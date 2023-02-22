package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import common.COMMANDS;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static String clientDataPath = System.getProperty("user.dir") + "\\JSON Database\\task\\src\\client\\data";

    public static void main(String[] args) {

        String address = "127.0.0.1";
        int port = 23456;

        try (Socket socket = new Socket(InetAddress.getByName(address), port)) {
            System.out.println("Client started!");
            CommandLine commandLine = new CommandLine();
            JCommander.newBuilder().addObject(commandLine).build().parse(args);
            String command = commandLine.getInput();

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            if (command.equalsIgnoreCase(COMMANDS.EXIT.toString())) {
                outputStream.writeUTF(COMMANDS.EXIT.toString());
            } else if (commandLine.getReadFromFile() != null) {
                String newCommand = gsonToString(command);
                outputStream.writeUTF(newCommand);
                printSentOutput(newCommand);
            } else {
                outputStream.writeUTF(command);
                printSentOutput(commandLine);
            }
            printReceivedInput(inputStream.readUTF());
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
//            System.out.println("Could not connect to server!");
        }
    }

    public static void printSentOutput(CommandLine commandLine) {
        System.out.print("Sent: ");
        String command = commandLine.getType().toLowerCase();
        if (command.equalsIgnoreCase(COMMANDS.SET.toString())) {
            System.out.println("{\"type\":\"" + COMMANDS.SET.toString().toLowerCase() + "\",\"key\":\"" + commandLine.getIndex() + "\"" + ",\"value\":\"" + commandLine.getData() + "\"}");
        } else if (command.equalsIgnoreCase(COMMANDS.GET.toString())) {
            System.out.println("{\"type\":\"" + COMMANDS.GET.toString().toLowerCase() + "\",\"key\":\"" + commandLine.getIndex() + "\"}");
        } else if (command.equalsIgnoreCase(COMMANDS.DELETE.toString())) {
            System.out.println("{\"type\":\"" + COMMANDS.DELETE.toString().toLowerCase() + "\",\"key\":\"" + commandLine.getIndex() + "\"}");
        } else if (command.equalsIgnoreCase(COMMANDS.EXIT.toString())) {
            System.out.println("{\"type\":\"" + COMMANDS.EXIT.toString().toLowerCase() + "\"}");
        }
    }

    public static void printSentOutput(String command) {
        System.out.print("Sent: ");
        String[] commands = command.split(" ", 3);
        if (commands.length == 3) {
            System.out.println("{\"type\":\"" + commands[0].toLowerCase() + "\",\"key\":\"" + commands[1].toLowerCase() + "\"" + ",\"value\":\"" + commands[2] + "\"}");
        } else {
            System.out.println("{\"type\":\"" + commands[0].toLowerCase() + "\",\"key\":\"" + commands[1].toLowerCase() + "\"}");
        }
    }

    public static void printReceivedInput(String input) {
        System.out.print("Received: ");
        System.out.println(input);
    }

    public static String gsonToString(String fileName) {
        try {
            String newString = Files.readString(Paths.get(clientDataPath + File.separator + fileName));
            JsonObject convertedObject = new Gson().fromJson(newString, JsonObject.class);
            String newOutput = convertedObject.get("type") + " " + convertedObject.get("key");
            if (convertedObject.has("value")) {
                newOutput += " " + convertedObject.get("value");
            }
            return newOutput.replace("\"", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
