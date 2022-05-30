package minesweeper;

import java.util.Scanner;
import java.util.Random;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    final static int maxColumns = 9;
    final static int maxRows = 9;
    static int currentColumn = 0;
    static int currentRow = 0;

    public static void main(String[] args) {
        run();
    }

    public static void run() {

        int numberOfMines = getNumberOfMines();

        char[][] minefield = createEmptyMinefield();
        printMinefield(minefield);

        System.out.println("Number of Mines: " + numberOfMines);

        String input = getUserInput();
        setColumnValue(input);
        setRowValue(input);

        System.out.print(currentColumn + " " + currentRow + " " + getCommand(input));

        addMinesToMinefield(minefield, numberOfMines);

        printMinefield(minefield);

    }

    public static void addMinesToMinefield(char[][] minefield, int numberOfMines) {

        // Variable for keeping track of how many mines are placed.
        int mineCounter = 0;

        // Creating random number generator object
        Random random = new Random();

        // Variables for placing a mine at the specific row and column (i.e. cell)
        int mineColumnPos = 0;
        int mineRowPos = 0;

        // Generating a random number for mineColumnPos and mineRowPos within the bounds of maxRows and maxColumns
        while (mineCounter < numberOfMines) {
            mineRowPos = random.nextInt(maxRows);
            mineColumnPos = random.nextInt(maxColumns);

            // Checking if cell is already mined, and is not on the initial row and column specified by user.
            if (minefield[mineRowPos][mineColumnPos] == STATES.HIDDEN.getState()
                    && (mineRowPos != currentRow || mineColumnPos != currentColumn)) {
                minefield[mineRowPos][mineColumnPos] = STATES.MINE.getState();
                mineCounter++;
            }
        }
    }

    public static String getCommand(String userInput) {
        return userInput.substring(4);
    }

    public static void setColumnValue(String userInput) {
        currentColumn = userInput.charAt(0) - 49;
    }

    public static void setRowValue(String userInput) {
        currentRow = userInput.charAt(2) - 49;
    }

    public static String getUserInput() {
        scanner.nextLine();
        System.out.print("Set/unset mines marks or claim a cell as free: ");
        return scanner.nextLine();
    }

    public static char[][] createEmptyMinefield() {
        // Creating uninitialized 2D char array
        char[][] minefield = new char[maxRows][maxColumns];
        // Setting 'HIDDEN' state to every element
        for (int rowPos = 0; rowPos < maxRows; rowPos++) {
            for (int columnPos = 0; columnPos < maxColumns; columnPos++) {
                minefield[rowPos][columnPos] = STATES.HIDDEN.getState();
            }
        }
        return minefield;
    }

    public static void printMinefield(char[][] minefield) {
        System.out.print("\n |");
        for (int columnNo = 1; columnNo <= maxColumns; columnNo++) {
            System.out.print(columnNo);
        }
        System.out.println("|");
        System.out.print("-|");
        for (int i = 1; i <= maxColumns; i++) {
            System.out.print("-");
        }
        System.out.println("|");
        for (int rowIndex = 0; rowIndex < maxRows; rowIndex++) {
            System.out.printf((rowIndex + 1) + "|");
            for (int columnIndex = 0; columnIndex < maxColumns; columnIndex++) {
                System.out.print(minefield[rowIndex][columnIndex]);
            }
            System.out.print("|" + '\n');
        }
        System.out.print("-|");
        for (int i = 1; i <= maxColumns; i++) {
            System.out.print("-");
        }
        System.out.println("|");
    }

    public static int getNumberOfMines() {
        System.out.print("How many mines do you want on the field? ");
        return scanner.nextInt();
    }

}
