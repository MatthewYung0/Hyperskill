package minesweeper;

import java.util.Scanner;
import java.util.Random;


public class Main {

    final static String mine = "X";
    final static String notMine = ".";

    static int maxRows = 9;
    static int maxColumns = 9;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("How many mines do you want on the field?");

        int userInput = scanner.nextInt();

        String[][] minefield = addMines(userInput, generateEmptyMineField());

        printMineField(minefield);

    }

    public static String[][] generateEmptyMineField() {
        String[][] minefield = new String[maxRows][maxColumns];
        for (int rowPos = 0; rowPos < maxRows; rowPos++) {
            for (int columnPos = 0; columnPos < maxColumns; columnPos++) {
                minefield[rowPos][columnPos] = notMine;
            }
        }
        return minefield;
    }

    public static String[][] addMines(int numberOfMines, String[][] mineField) {

        int mineCounter = 0;

        Random random = new Random();
        int mineColumnPos = 0;
        int mineRowPos = 0;

        while (mineCounter < numberOfMines) {
            mineRowPos = random.nextInt(maxRows);
            mineColumnPos = random.nextInt(maxColumns);
            if (mineField[mineRowPos][mineColumnPos].equals(notMine)) {
                mineField[mineRowPos][mineColumnPos] = mine;
                mineCounter++;
            }
        }
        return mineField;
    }

    public static void printMineField(String[][] mineField) {
        for(int rowIndex = 0; rowIndex < maxRows; rowIndex++){
            for(int columnIndex = 0; columnIndex < maxColumns; columnIndex++){
                System.out.printf(mineField[rowIndex][columnIndex]);
            }
            System.out.print('\n');
        }
    }

}


