package battleship;

import java.util.Scanner;

public class Main {

    final static int maxColumns = 10;
    final static int maxRows = 10;
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

        setUpBattlefield(ships, battlefield);
        fireShots(battlefield);
    }

    public static boolean shotIsValid(int row, int column) {
        if (row - 1 >= maxRows || row < 1 || column - 1 >= maxColumns || column < 1) {
            System.out.println("\nError! You entered the wrong coordinates! Try again: \n");
            return false;
        }
        return true;
    }

    public static void fireShots(char[][] battlefield) {
        String coordinates;
        int row ;
        int column;
        System.out.println("\nThe game starts!");
        while(!allShipsSunken(battlefield)) {
            printField(battlefield);
            System.out.println("\nFire a shot!\n");
            do {
                coordinates = scanner.nextLine();
                row = coordinates.charAt(0) - 64;
                column = Integer.parseInt(coordinates.substring(1));
            } while (!shotIsValid(row, column));
            if (battlefield[row - 1][column - 1] == STATES.SHIP.getState()) {
                battlefield[row - 1][column - 1] = STATES.HIT.getState();
                System.out.println("\nYou hit a ship!");
            } else {
                battlefield[row - 1][column - 1] = STATES.MISS.getState();
                System.out.println("\nYou missed!");
            }
        }
    }

    public static boolean allShipsSunken(char[][] battlefield) {
        for (int row = 0; row < maxRows; row++) {
            for (int column = 0; column < maxColumns; column++) {
                if (battlefield[row][column] == STATES.SHIP.getState()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void setUpBattlefield(SHIPS[] ships, char[][] battlefield) {
        // integer array to hold coordinates
        int[] coordinates = {0,0,0,0};

        int counter = 0;
        for(int i = 0; i < ships.length; i++) {
            System.out.println("\nEnter the coordinates of the " + ships[i].getShipName() + " (" + ships[i].getShipLength() + " cells): \n");
            do {
                setCoordinates(coordinates);
            } while (!checkValidPlacement(coordinates[0], coordinates[1], coordinates[2], coordinates[3], battlefield, ships[i]));
            if (coordinates[1] == coordinates[3]) {
                for (int j = Math.min(coordinates[0], coordinates[2]) - 1; counter < ships[i].getShipLength(); j++) {
                    battlefield[j][coordinates[1] - 1] = STATES.SHIP.getState();
                    counter++;
                }
                printField(battlefield);
            } else {
                for (int j = Math.min(coordinates[1], coordinates[3]) - 1; counter < ships[i].getShipLength(); j++) {
                    battlefield[coordinates[0] - 1][j] = STATES.SHIP.getState();
                    counter++;
                }
                printField(battlefield);
            }
            counter = 0;
        }

    }

    public static void setCoordinates(int[] coordinates) {
        String[] coordinatesTemporary;
        String userInput;
        userInput = scanner.nextLine();
        coordinatesTemporary = userInput.split(" ");
        // startPointRow
        coordinates[0] = coordinatesTemporary[0].charAt(0) - 64;
        // endPointRow
        coordinates[1] = Integer.parseInt(coordinatesTemporary[0].substring(1));
        // startColumnRow
        coordinates[2] = coordinatesTemporary[1].charAt(0) - 64;
        // endPointColumn
        coordinates[3] = Integer.parseInt(coordinatesTemporary[1].substring(1));
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
