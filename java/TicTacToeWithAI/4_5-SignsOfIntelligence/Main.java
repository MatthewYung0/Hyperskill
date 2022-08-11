package tictactoe;

import java.util.*;

public class Main {
    // ArrayList to hold board status
    static List<Character> gameField = new ArrayList<>(Collections.nCopies(9, ' '));
    // ArrayList to hold players
    static ArrayList<User> players = new ArrayList<>();
    // String to hold commands
    static String[] commands;
    static boolean xWins = false, oWins = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String commandInput;
        // Waiting for command input
        do {
            System.out.print("Input command: ");
            commandInput = scanner.nextLine();
            // If command input is okay, proceed with initialising the game
            if (!commandInput.equals("exit") && isValidCommand(commandInput)) {
                players.clear();
                Collections.fill(gameField, ' ');
                xWins = false; oWins = false;
                createPlayers();
                printGameField();
                System.out.print('\n');
                // Below code is actual gameplay.
                int index = 0;
                while (true) {
                    if (players.get(index) instanceof Computer) {
                        System.out.println("Making move level \"" + ((Computer) players.get(index)).getDifficulty() + "\"");
                    }
                    players.get(index).run(gameField);
                    printGameField();
                    if (isGameOver()) {
                        break;
                    }
                    if (index > 0) {
                        index = 0;
                    } else {
                        index++;
                    }
                }
            }
        } while (!commandInput.equals("exit"));

    }

    public static void createPlayers() {
        // Sets first symbol to X
        char symbol = 88;
        for (int i = 1; i <= 2; i++) {
            String command = commands[i];
            if (commands[i].equals("user")) {
                players.add(new Human(symbol));
            } else {
                players.add(new Computer(command, symbol));
            }
            // Sets next symbol to O
            symbol -= 9;
        }
    }

    public static boolean isValidCommand(String command) {
        boolean isValidCommand = true;
        commands = command.split(" ");
        ArrayList<String> commandsToArrayList = new ArrayList<>(Arrays.asList(commands));
        // Checking for correct length of input
        if (commands.length != 3
                || !commandsToArrayList.get(0).equals("start")
                        && (!commandsToArrayList.contains("easy") || commandsToArrayList.contains("user"))) {
            isValidCommand = false;
            System.out.println("Bad parameters!");
        }
        return isValidCommand;
    }

    public static boolean isGameOver() {
        // Checking rows & checking columns
        boolean isOver;
        checkRows();
        checkColumns();
        checkDiagonally();
        if (xWins) {
            System.out.println("\nX wins");
            isOver = true;
        } else if(oWins) {
            System.out.println("\nO wins");
            isOver = true;
        } else if (Collections.frequency(gameField, ' ') == 0) {
            System.out.println("\nDraw");
            isOver = true;
        } else {
            isOver = false;
        }
        System.out.print('\n');
        return isOver;
    }

    public static void checkWins(int score) {
        final int xWinCondition = 264;
        final int oWinCondition = 237;
        if (score == xWinCondition) {
            xWins = true;
        } else if (score == oWinCondition) {
            oWins = true;
        }
    }

    public static void checkDiagonally() {
        int sum;
        sum = gameField.get(0) + gameField.get(4) + gameField.get(8);
        checkWins(sum);
        sum = gameField.get(2) + gameField.get(4) + gameField.get(6);
        checkWins(sum);
    }
    public static void checkColumns() {
        int columnTotal = 0;
        for (int column = 0; column < 3; column ++) {
            for (int row = column; row < 9; row +=3) {
                columnTotal += gameField.get(row);
            }
            checkWins(columnTotal);
            columnTotal = 0;
        }
    }
    public static void checkRows() {
        int rowTotal = 0;
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                checkWins(rowTotal);
                rowTotal = 0;
            }
            rowTotal += gameField.get(i);
        }
        checkWins(rowTotal);
    }
    public static void printGameField() {
        System.out.println("---------");
        for (int i = 0; i < gameField.size(); i+=3) {
            System.out.print("| ");
            System.out.print(gameField.get(i) + " " + gameField.get(i + 1) + " " + gameField.get(i + 2));
            System.out.println(" |");
        }
        System.out.print("---------");
    }
}
