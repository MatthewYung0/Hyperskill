package client;

import com.beust.jcommander.JCommander;
import common.COMMANDS;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {

        String address = "127.0.0.1";
        int port = 23456;

        try (Socket socket = new Socket(InetAddress.getByName(address), port)) {
            System.out.println("Client started!");
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            if (args[0].toUpperCase().equals(COMMANDS.EXIT.toString())) {
                outputStream.writeUTF(COMMANDS.EXIT.toString());
                System.out.println(args[0]);
            } else {
                CommandLine commandLine = new CommandLine();
                JCommander.newBuilder().addObject(commandLine).build().parse(args);
                String command = commandLine.getInput();
                outputStream.writeUTF(command);
                printSentOutput(commandLine);
                printReceivedInput(inputStream.readUTF());
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            System.out.println("Could not connect to server!");
        }
    }

    public static void printSentOutput(CommandLine commandLine) {
        System.out.print("Sent: ");
        String command = commandLine.getType().toLowerCase();
        if (command.equals(COMMANDS.SET.toString().toLowerCase())) {
            System.out.println("{\"type\":\"" + COMMANDS.SET.toString().toLowerCase() + "\",\"key\":\"" + commandLine.getIndex() + "\"" + ",\"value\":\"" + commandLine.getData() + "\"}");
        } else if (command.equals(COMMANDS.GET.toString().toLowerCase())) {
            System.out.println("{\"type\":\"" + COMMANDS.GET.toString().toLowerCase() + "\",\"key\":\"" + commandLine.getIndex() + "\"}");
        } else if (command.equals(COMMANDS.DELETE.toString().toLowerCase())) {
            System.out.println("{\"type\":\"" + COMMANDS.DELETE.toString().toLowerCase() + "\",\"key\":\"" + commandLine.getIndex() + "\"}");
        } else {
            System.out.println("{\"type\":\"" + COMMANDS.EXIT.toString().toLowerCase() + "\"}");
        }
    }

    public static void printReceivedInput(String input) {
        System.out.print("Received: ");
        System.out.println(input);
    }

}
