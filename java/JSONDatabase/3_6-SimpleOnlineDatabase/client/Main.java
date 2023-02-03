package client;

import com.beust.jcommander.JCommander;
import common.COMMANDS;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.beust.jcommander.Parameter;

public class Main {

    public static void main(String[] args) {

        String address = "127.0.0.1";
        int port = 23456;

        try (Socket socket = new Socket(InetAddress.getByName(address), port)) {
            System.out.println("Client started!");

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            System.out.print("Sent: ");
            if (args[0].toUpperCase().equals(COMMANDS.EXIT.toString())) {
                outputStream.writeUTF(COMMANDS.EXIT.toString());
                System.out.println(args[0]);
            } else {
                CommandLine commandLine = new CommandLine();
                JCommander.newBuilder().addObject(commandLine).build().parse(args);
                String command = commandLine.getInput();
                if (isCorrectInput(command)) {
                    outputStream.writeUTF(command);
                    System.out.println(command);

                    System.out.print("Received: ");
                    System.out.println(inputStream.readUTF());
                } else {
                    System.out.println("ERROR");
                }
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            System.out.println("Could not connect to server!");
        }
    }

    private static boolean isCorrectInput(String input) {
        try {
            return switch (input.substring(0, input.indexOf(" ")).toLowerCase()) {
                case "set" ->
                        isCorrectIndex(input) && isCorrectLength(input, COMMANDS.SET) && isCorrectCommand(input, COMMANDS.SET);
                case "get" ->
                        isCorrectIndex(input) && isCorrectLength(input, COMMANDS.GET) && isCorrectCommand(input, COMMANDS.GET);
                case "delete" ->
                        isCorrectIndex(input) && isCorrectLength(input, COMMANDS.DELETE) && isCorrectCommand(input, COMMANDS.DELETE);
                default -> false;
            };
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isCorrectIndex(String input) {
        try {
            String index = input.split(" ")[1];
            return Integer.parseInt(index) - 1 < 1000 && Integer.parseInt(index) >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isCorrectLength(String input, COMMANDS command) {
        try {
            String[] inputs = input.split(" ");
            if (Objects.requireNonNull(command) == COMMANDS.SET) {
                return inputs.length >= 3;
            } else {
                return inputs.length == 2;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isCorrectCommand(String input, COMMANDS command) {
        String inputCommand = input.substring(0, input.indexOf(' ')).toLowerCase();
        return inputCommand.equals(command.toString().toLowerCase());
    }

}
