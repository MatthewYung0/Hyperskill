package minesweeper;

import java.util.Scanner;
import java.util.Random;

public class Main {

    final static String mine = "X";
    final static String notMine = ".";
    final static int maxRows = 9;
    final static int maxColumns = 9;

    public static void main(String[] args) {

        // Input for receiving number of mines
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? ");
        int numberOfMines = scanner.nextInt();
        scanner.nextLine();

        // Creating minefield with numbers
        String[][] minefield = addMines(numberOfMines, generateEmptyMineField());

        int minesFound = 0;
        int minesPlaced = 0;
        int columnPosInput = 0;
        int rowPosInput = 0;
        String coordinates = "";

        printMineField(minefield);

        while (minesFound < numberOfMines || minesPlaced != 0) {
            System.out.print("Set/delete mines marks (x and y coordinates): ");

            coordinates = scanner.nextLine();
            columnPosInput = coordinates.charAt(0) - 49;
            rowPosInput = coordinates.charAt(2) - 49;

            if (!minefield[rowPosInput][columnPosInput].equals(".") && !minefield[rowPosInput][columnPosInput].equals("*")) {
                System.out.println("There is a number here!");

            } else if (minefield[rowPosInput][columnPosInput].equals(".")) {
                boolean isAMine = true;
                out:
                for (int row = - 1; row < 2; row ++) {
                    for (int column = -1; column < 2; column++) {
                        try {
                            if (minefield[rowPosInput + row][columnPosInput + column].equals(".") && row != 0 && column != 0) {
                                minefield[rowPosInput][columnPosInput] = "*";
                                minesPlaced++;
                                System.out.println("MINES PLACED: " + minesFound);
                                isAMine = false;
                                break out;
                            }
                        } catch (ArrayIndexOutOfBoundsException ignored) { }
                    }
                }
                if (isAMine) {
                    if (minefield[rowPosInput][columnPosInput].equals("*")) {
                        minefield[rowPosInput][columnPosInput] = ".";
                        minesFound--;
                        System.out.println("MINES FOUND: " + minesFound);
                    } else {
                        minefield[rowPosInput][columnPosInput] = "*";
                        minesFound++;
                        System.out.println("MINES FOUND: " + minesFound);
                    }
                }
            } else if (minefield[rowPosInput][columnPosInput].equals("*")) {
                minefield[rowPosInput][columnPosInput] = ".";
                System.out.println("MINES PLACED: " + minesFound);
                minesPlaced--;
            }
            printMineField(minefield);
        }
        System.out.println("Congratulations! You found all mines!");
    }

    // Function for generating an empty minefield
    public static String[][] generateEmptyMineField() {
        String[][] minefield = new String[maxRows][maxColumns];
        for (int rowPos = 0; rowPos < maxRows; rowPos++) {
            for (int columnPos = 0; columnPos < maxColumns; columnPos++) {
                minefield[rowPos][columnPos] = notMine;
            }
        }
        return minefield;
    }

    // Function for randomly placing mines in the minefield
    public static String[][] addMines(int numberOfMines, String[][] minefield) {

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

            // Checking if cell is already mined. If not, it is mined.
            if (minefield[mineRowPos][mineColumnPos].equals(notMine)) {
                minefield[mineRowPos][mineColumnPos] = mine;
                mineCounter++;
            }
        }
        // Returning minefield with states of nearby cells
        return checkMineField(minefield);
    }

    // Function for printing minefield
    public static void printMineField(String[][] minefield) {
        System.out.println("\n |123456789|");
        System.out.println("-|---------|");
        for (int rowIndex = 0; rowIndex < maxRows; rowIndex++) {
            System.out.printf((rowIndex + 1) + "|");
            for (int columnIndex = 0; columnIndex < maxColumns; columnIndex++) {
                System.out.printf(minefield[rowIndex][columnIndex]);
            }
            System.out.print("|" + '\n');
        }
        System.out.println("-|---------|");
    }

    // Function for checking nearby cells if they have mines
    public static String[][] checkMineField(String[][] minefield) {

        // Nested for loop for checking through each cell
        for (int rowPos = 0; rowPos < maxRows; rowPos++) {
            for (int columnPos = 0; columnPos < maxColumns; columnPos++) {

                // if statement for checking all around the cell if it is mined.
                if (minefield[rowPos][columnPos].equals(mine)) {
                    for (int row = - 1; row < 2; row ++) {
                        for (int column = -1; column < 2; column++) {
                            // if cell is out of bounds, ignore and continue with loop
                            try {
                                minefield[rowPos + row][columnPos + column] = changeCellState(minefield[rowPos + row][columnPos + column]);
                            } catch (ArrayIndexOutOfBoundsException ignored) { }
                        }
                    }
                }
            }
        }
        return hideMines(minefield);
    }

    public static String[][] hideMines(String[][] minefield) {
        for (int column = 0; column < maxColumns; column++) {
            for (int row = 0; row < maxRows; row++) {
                if (minefield[row][column].equals("X")) {
                    minefield[row][column] = ".";
                }
            }
        }
        return minefield;
    }

    // Function for changing state of cells
    public static String changeCellState(String cell) {
        return switch (cell) {
            case "." -> "1";
            case "1" -> "2";
            case "2" -> "3";
            case "3" -> "4";
            case "4" -> "5";
            case "5" -> "6";
            case "6" -> "7";
            case "7" -> "8";
            default -> cell;
        };
    }
}
