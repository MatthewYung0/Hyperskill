package minesweeper;

import java.util.Scanner;
import java.util.Random;

public class Main {

    final static int maxColumns = 9;
    final static int maxRows = 9;
    static int currentColumn = 0;
    static int currentRow = 0;
    static char[][] revealedMinefield = new char[maxColumns][maxRows];
    static int foundMines = 0;
    static int cellCount = maxColumns * maxRows;

    public static void main(String[] args) {
        run();
    }

    public static void run() {

        // Code to create a copy of the mines
        int numberOfMines = getNumberOfMines();
        revealedMinefield = createEmptyMinefield();
        addMinesToMinefield(revealedMinefield, numberOfMines);
        scanAndChangeMineField(revealedMinefield);
        //printMinefield(revealedMinefield); // Code to print positions of mines

        char[][] userMinefield = createEmptyMinefield();

        while (foundMines < numberOfMines && cellCount != numberOfMines) {

            printMinefield(userMinefield);
            String input = getUserInput();
            String command = getCommand(input);
            setColumnValue(input);
            setRowValue(input);

            if (command.equals("free")) {
                if (revealedMinefield[currentRow][currentColumn] == STATES.MINE.getState()) {
                    revealMines(userMinefield);
                    printMinefield(userMinefield);
                    System.out.println("You stepped on a mine and failed!");
                    break;
                } else if (revealedMinefield[currentRow][currentColumn] == STATES.HIDDEN.getState()){
                    floodFill(userMinefield, new boolean[maxRows][maxColumns], currentRow, currentColumn);
                } else {
                    userMinefield[currentRow][currentColumn] = revealedMinefield[currentRow][currentColumn];
                    cellCount--;
                }
            } else if (command.equals("mine")) {
                if (revealedMinefield[currentRow][currentColumn] == STATES.MINE.getState()) {
                    if (userMinefield[currentRow][currentColumn] != STATES.FLAGGED.getState()) {
                        userMinefield[currentRow][currentColumn] = STATES.FLAGGED.getState();
                        foundMines++;
                    } else if (userMinefield[currentRow][currentColumn] == STATES.FLAGGED.getState()) {
                        userMinefield[currentRow][currentColumn] = STATES.HIDDEN.getState();
                        foundMines--;
                    }
                    // When cell is not mined...
                } else if (revealedMinefield[currentRow][currentColumn] != STATES.MINE.getState()) {
                    // ...and userMinefield is not
                    if (userMinefield[currentRow][currentColumn] != STATES.FLAGGED.getState()) {
                        userMinefield[currentRow][currentColumn] = STATES.FLAGGED.getState();
                        foundMines--;
                        cellCount++;
                    } else if (userMinefield[currentRow][currentColumn] == STATES.FLAGGED.getState()) {
                        userMinefield[currentRow][currentColumn] = STATES.HIDDEN.getState();
                        foundMines++;
                        cellCount--;
                    }
                }
            }
        }
        printMinefield(userMinefield);
        System.out.println("Congratulations! You found all the mines!");
    }

    public static void floodFill(char[][] userMinefield, boolean[][] visited, int currentRow, int currentColumn) {
        // quit if off the grid:
        if(currentRow < 0 || currentRow >= revealedMinefield.length || currentColumn < 0 || currentColumn >= revealedMinefield[0].length) return;

        // quit if visited:
        if(visited[currentRow][currentColumn]) return;
        visited[currentRow][currentColumn] = true;

        // quit if hit boundaries:
        if((revealedMinefield[currentRow][currentColumn] == STATES.NEARBY_1.getState() && userMinefield[currentRow][currentColumn] != STATES.NEARBY_1.getState()) ||
                (revealedMinefield[currentRow][currentColumn] == STATES.NEARBY_2.getState() && userMinefield[currentRow][currentColumn] != STATES.NEARBY_2.getState()) ||
                (revealedMinefield[currentRow][currentColumn] == STATES.NEARBY_3.getState() && userMinefield[currentRow][currentColumn] != STATES.NEARBY_3.getState()) ||
                (revealedMinefield[currentRow][currentColumn] == STATES.NEARBY_4.getState() && userMinefield[currentRow][currentColumn] != STATES.NEARBY_4.getState()) ||
                (revealedMinefield[currentRow][currentColumn] == STATES.NEARBY_5.getState() && userMinefield[currentRow][currentColumn] != STATES.NEARBY_5.getState()) ||
                (revealedMinefield[currentRow][currentColumn] == STATES.NEARBY_6.getState() && userMinefield[currentRow][currentColumn] != STATES.NEARBY_6.getState()) ||
                (revealedMinefield[currentRow][currentColumn] == STATES.NEARBY_7.getState() && userMinefield[currentRow][currentColumn] != STATES.NEARBY_7.getState()) ||
                (revealedMinefield[currentRow][currentColumn] == STATES.NEARBY_8.getState() && userMinefield[currentRow][currentColumn] != STATES.NEARBY_8.getState()) ||
                (revealedMinefield[currentRow][currentColumn] == STATES.UNHIDDEN.getState() && userMinefield[currentRow][currentColumn] != STATES.UNHIDDEN.getState()))
        {
            userMinefield[currentRow][currentColumn] = revealedMinefield[currentRow][currentColumn];
            cellCount--;

            return;
        } else if (revealedMinefield[currentRow][currentColumn] == STATES.FLAGGED.getState()) {
            return;
        }

        // we want to visit places with periods in them:
        if(revealedMinefield[currentRow][currentColumn] == STATES.HIDDEN.getState() && userMinefield[currentRow][currentColumn] != STATES.UNHIDDEN.getState()) {
            userMinefield[currentRow][currentColumn] = STATES.UNHIDDEN.getState();
            cellCount--;
        }

        // recursively fill in all directions
        floodFill(userMinefield,visited,currentRow+1,currentColumn);
        floodFill(userMinefield,visited,currentRow-1,currentColumn);
        floodFill(userMinefield,visited,currentRow,currentColumn+1);
        floodFill(userMinefield,visited,currentRow,currentColumn-1);
        floodFill(userMinefield,visited,currentRow-1,currentColumn-1);
        floodFill(userMinefield,visited,currentRow+1,currentColumn+1);
        floodFill(userMinefield,visited,currentRow-1,currentColumn+1);
        floodFill(userMinefield,visited,currentRow+1,currentColumn-1);
    }

    public static void revealMines(char[][] minefield) {
        for (int column = 0; column < maxColumns; column++) {
            for (int row = 0; row < maxRows; row++) {
                if (revealedMinefield[row][column] == STATES.MINE.getState()) {
                    minefield[row][column] = STATES.MINE.getState();
                }
            }
        }
    }

    public static void scanAndChangeMineField(char[][] minefield) {
        for (int rowPos = 0; rowPos < maxRows; rowPos++) {
            for (int columnPos = 0; columnPos < maxColumns; columnPos++) {
                // if statement for checking all around the cell if it is mined.
                if (minefield[rowPos][columnPos] == STATES.MINE.getState()) {
                    for (int row = - 1; row < 2; row ++) {
                        for (int column = -1; column < 2; column++) {
                            // if cell is out of bounds, ignore and continue with loop
                            try {
                                // change the state of the points in the points
                                minefield[rowPos + row][columnPos + column] = changeCellState(minefield[rowPos + row][columnPos + column]);
                            } catch (ArrayIndexOutOfBoundsException ignored) { }
                        }
                    }
                }
            }
        }
    }

    public static char changeCellState(char state) {
        switch(state) {
            case '.':
                return '1';
            case '1':
                return '2';
            case '2':
                return '3';
            case '3':
                return '4';
            case '4':
                return '5';
            case '5':
                return '6';
            case '6':
                return '7';
            case '7':
                return '8';
            default:
                return state;
        }
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
        Scanner scanner = new Scanner(System.in);
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
        printLines();
        for (int rowIndex = 0; rowIndex < maxRows; rowIndex++) {
            System.out.print((rowIndex + 1) + "|");
            for (int columnIndex = 0; columnIndex < maxColumns; columnIndex++) {
                System.out.print(minefield[rowIndex][columnIndex]);
            }
            System.out.print("|" + '\n');
        }
        printLines();
    }

    public static int getNumberOfMines() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? ");
        return scanner.nextInt();
    }

    public static void printLines() {
        System.out.print("-|");
        for (int rowNo = 1; rowNo <= maxColumns; rowNo++) {
            System.out.print("-");
        }
        System.out.println("|");
    }

}
