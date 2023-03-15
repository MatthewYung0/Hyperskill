package battleship;

import java.util.Scanner;

public class Main {

    final static int maxColumns = 10;
    final static int maxRows = 10;
    static char[][] shipPositions = new char[maxColumns][maxRows];
    static int startPointRow;
    static int startPointColumn;
    static int endPointRow;
    static int endPointColumn;
    static Scanner scanner = new Scanner(System.in);

    public enum STATES {
        HIDDEN('~'),
        SHIP('O'),
        HIT('X'),
        MISS('M');
        final char state;
        STATES(char state) {
            this.state = state;
        }
        public char getState() {
            return state;
        }
    }
    public enum SHIPS {
        AIRCRAFT_CARRIER(5, "Aircraft Carrier"),
        BATTLESHIP(4, "Battleship"),
        SUBMARINE(3, "Submarine"),
        CRUISER(3, "Cruiser"),
        DESTROYER(2, "Destroyer");
        final int shipLength;
        final String shipName;
        SHIPS(int shipLength, String shipName) {
            this.shipLength = shipLength;
            this.shipName = shipName;
        }
        public int getShipLength() {
            return shipLength;
        }
        public String getShipName() {
            return shipName;
        }
    }

    public static void main(String[] args) {
        run();
    }

    public static void run() {

        char[][] battlefield = createEmptyGameField();
        printField(battlefield);
        SHIPS[] ships = SHIPS.values();

        int counter = 0;
        for(int i = 0; i < ships.length; i++) {
            System.out.println("\nEnter the coordinates of the " + ships[i].getShipName() + " (" + ships[i].getShipLength() + " cells): \n");
            getUserInput();
            while (!checkValidPlacement(startPointRow, startPointColumn, endPointRow, endPointColumn, battlefield, ships[i])) {
                getUserInput();
            }
            if (startPointColumn == endPointColumn) {
                for (int j = Math.min(startPointRow, endPointRow) - 1; counter < ships[i].getShipLength(); j++) {
                    battlefield[j][startPointColumn - 1] = STATES.SHIP.getState();
                    counter++;
                }
                printField(battlefield);
            } else {
                for (int j = Math.min(startPointColumn, endPointColumn) - 1; counter < ships[i].getShipLength(); j++) {
                    battlefield[startPointRow - 1][j] = STATES.SHIP.getState();
                    counter++;
                }
                printField(battlefield);
            }
            counter = 0;
        }
    }

    public static void getUserInput() {
        String[] coordinatesTemporary;
        String userInput;
        userInput = scanner.nextLine();
        coordinatesTemporary = userInput.split(" ");
        setCoordinates(coordinatesTemporary[0].charAt(0) - 64,
                Integer.parseInt(coordinatesTemporary[0].substring(1)),
                coordinatesTemporary[1].charAt(0) - 64,
                Integer.parseInt(coordinatesTemporary[1].substring(1)));
    }

    public static void setCoordinates(int startPointRowInput, int startPointColumnInput, int endPointRowInput, int endPointColumnInput) {
        startPointRow = startPointRowInput;
        startPointColumn = startPointColumnInput;
        endPointRow = endPointRowInput;
        endPointColumn = endPointColumnInput;
    }

    public static boolean checkValidPlacement(int startPointRow, int startPointColumn, int endPointRow, int endPointColumn, char[][] battlefield, SHIPS ship) {

        // Checking if the ship is in a right location
        if (startPointRow != endPointRow && startPointRow != endPointColumn && startPointColumn != endPointColumn) {
            System.out.println("\nError! Wrong ship location! Try again: \n");
            return false;
        }
        // Checking if the length of the ship is correct
        if (Math.abs(startPointColumn - endPointColumn) + 1 != ship.getShipLength() && Math.abs(startPointRow - endPointRow) + 1 != ship.getShipLength()) {
            System.out.println("\nError! Wrong length of the " + ship.getShipName() + "! Try again: \n");
            return false;
        }
        // Checking if a ship is near the entered location
        for (int row_index = Math.min(startPointRow, endPointRow) - 1; row_index < Math.max(startPointRow, endPointRow) + 1; row_index++) {
            for (int column_index = Math.min(startPointColumn, endPointColumn) - 1; column_index < Math.max(startPointColumn, endPointColumn) + 1; column_index++) {
                try {
                    if (battlefield[row_index][column_index] == STATES.SHIP.getState()) {
                        System.out.println("\nError! You placed it too close to another one. Try again: \n");
                        return false;
                    }
                } catch (ArrayIndexOutOfBoundsException e) { }
            }
        }
        return true;
    }

    public static char[][] createEmptyGameField() {
        char[][] field = new char[maxRows][maxColumns];
        for (int rowPos = 0; rowPos < maxRows; rowPos++) {
            for (int columnPos = 0; columnPos < maxColumns; columnPos++) {
                field[rowPos][columnPos] = STATES.HIDDEN.getState();
            }
        }
        return field;
    }

    public static void printField(char[][] minefield) {
        int letter = 65;
        System.out.print("\n  ");
        for (int columnNo = 1; columnNo <= maxColumns; columnNo++) {
            System.out.print(columnNo + " ");
        }
        System.out.print('\n');
        for (int rowIndex = 0; rowIndex < maxRows; rowIndex++) {
            System.out.print((char) letter + " ");
            for (int columnIndex = 0; columnIndex < maxColumns; columnIndex++) {
                System.out.print(minefield[rowIndex][columnIndex] + " ");
            }
            System.out.print('\n');
            letter++;
        }
    }
}
