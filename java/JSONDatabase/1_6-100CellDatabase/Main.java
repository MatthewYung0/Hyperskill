package server;

import common.COMMANDS;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static final String[] database = new String[100];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean isExit = false;
        do {
            input = scanner.nextLine();
            if (input.toUpperCase().equals(COMMANDS.EXIT.toString())) {
                isExit = true;
            } else if (isCorrectInput(input)) {
                String[] messages = input.split(" ", 3);
                int index = Integer.parseInt(messages[1]) - 1;
                if (messages[0].equals("get") && !elementExists(index)) {
                    System.out.println("ERROR");
                } else {
                    switch (messages[0]) {
                        case "set" -> {
                            database[index] = messages[2];
                            System.out.println("OK");
                        }
                        case "delete" -> {
                            database[index] = null;
                            System.out.println("OK");
                        }
                        case "get" -> System.out.println(database[index]);
                    }
                }
            } else {
                System.out.println("ERROR");
            }
        } while (!isExit);
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
            return Integer.parseInt(index) < 101 && Integer.parseInt(index) > 0;
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

    private static boolean elementExists(int index) {
        return database[index] != null;
    }
}
