package battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    final static int maxColumns = 10;
    final static int maxRows = 10;

    static Player[] playerArray = {new Player(maxRows, maxColumns), new Player(maxRows, maxColumns)};
    static int currentPlayer = 0;
    static int otherPlayer = 1;
    static Scanner scanner = new Scanner(System.in);
    static boolean warIsOver = false;

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

        SHIPS[] ships = SHIPS.values();
        char[][] emptyBattlefield = emptyBattlefield();

        for (int player = 0; player < playerArray.length; player++) {
            initialiseBattlefieldAndShipPositions(playerArray[player]);
        }

        for (int player = 0; player < playerArray.length; player++) {
            System.out.println("Player " + (player + 1) + ", place your ships on the game field");
            printField(playerArray[player].getBattlefield());
            placeShips(ships, playerArray[player]);
            changePlayerTurn();
            System.out.println("\nPress Enter and pass the move to another player");
            scanner.nextLine();
        }

        while (!warIsOver) {
            printField(emptyBattlefield);
            beginWar(playerArray[currentPlayer], playerArray[otherPlayer]);
        }
    }

    public static void changePlayerTurn() {
        if (currentPlayer == 0) {
            currentPlayer = 1;
            otherPlayer = 0;
        } else {
            currentPlayer = 0;
            otherPlayer = 1;
        }
    }

    public static boolean checkShipPlacement(int startPointRow, int startPointColumn, int endPointRow, int endPointColumn, char[][] battlefield, SHIPS ship) {
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
        for (int row_index = Math.min(startPointRow, endPointRow) - 1; row_index <= Math.max(startPointRow, endPointRow) + 1; row_index++) {
            for (int column_index = Math.min(startPointColumn, endPointColumn) - 1; column_index <= Math.max(startPointColumn, endPointColumn) + 1; column_index++) {
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
    public static List<List<String>> createShipStatusArrayList() {
        List<List<String>> shipStatuses = new ArrayList<List<String>>();
        for (int i = 0; i < 5; i++) {
            shipStatuses.add(new ArrayList<String>());
        }
        return shipStatuses;
    }
    public static void placeShips(SHIPS[] ships, Player player) {
        // Integer array to hold coordinates
        int[] coordinates = {0,0,0,0};
        int shipLengthCounter = 0;
        int shipStatusCounter = 0;

        List<List<String>> shipStatuses = createShipStatusArrayList();
        for(int i = 0; i < ships.length; i++) {
            System.out.println("\nEnter the coordinates of the " + ships[i].getShipName() + " (" + ships[i].getShipLength() + " cells): \n");
            do {
                setCoordinates(coordinates);
            } while (!checkShipPlacement(coordinates[0], coordinates[1], coordinates[2], coordinates[3], player.getBattlefield(), ships[i]));
            if (coordinates[1] == coordinates[3]) {
                for (int j = Math.min(coordinates[0], coordinates[2]); shipLengthCounter < ships[i].getShipLength(); j++) {
                    player.setBattlefield(j, coordinates[1], STATES.SHIP.getState());
                    player.setShipPositions(j, coordinates[1], STATES.SHIP.getState());
                    initialiseShipStatus(coordinates[1], j, shipStatusCounter, shipStatuses);
                    shipLengthCounter++;
                }
            } else {
                for (int j = Math.min(coordinates[1], coordinates[3]); shipLengthCounter < ships[i].getShipLength(); j++) {
                    player.setBattlefield(coordinates[0], j, STATES.SHIP.getState());
                    player.setShipPositions(coordinates[0], j, STATES.SHIP.getState());
                    initialiseShipStatus(j, coordinates[0], shipStatusCounter, shipStatuses);
                    shipLengthCounter++;
                }
            }
            printField(player.getBattlefield());
            shipLengthCounter = 0;
            shipStatusCounter++;
        }
        player.setShipStatuses(shipStatuses);
        hideShips(player);
    }

    public static void hideShips(Player player) {
        for (int row = 0; row < maxRows; row++) {
            for (int column = 0; column < maxColumns; column++) {
                player.setBattlefield(row, column, STATES.HIDDEN.getState());
            }
        }
    }

    public static boolean shotIsValid(int row, int column) {
        if (row >= maxRows || row < 0 || column >= maxColumns || column < 0) {
            System.out.println("\nError! You entered the wrong coordinates! Try again: \n");
            return false;
        }
        return true;
    }

    public static void beginWar(Player currentPlayer, Player otherPlayer) {
        String coordinates;
        int row;
        int column;
        do {
            coordinates = scanner.nextLine();
            if (coordinates.isEmpty()) {
                return;
            } else {
                row = coordinates.charAt(0) - 65;
                column = Integer.parseInt(coordinates.substring(1)) - 1;
            }
        } while (!shotIsValid(row, column));
        if (otherPlayer.getShipPositions()[row][column] == STATES.SHIP.getState() || otherPlayer.getBattlefield()[row][column] == STATES.HIT.getState()) {
            currentPlayer.setBattlefield(row, column, STATES.HIT.getState());
            otherPlayer.setShipPositions(row, column, STATES.HIT.getState());
            if (isLastInList(coordinates, otherPlayer)) {
                if (allShipsSunken(otherPlayer.getShipPositions())) {
                    System.out.println("\nYou sank the last ship. You won. Congratulations!");
                    warIsOver = true;
                }
                System.out.println("\nYou sank a ship!");
            } else {
                System.out.println("\nYou hit a ship!\n");
            }
        } else if (otherPlayer.getShipPositions()[row][column] == STATES.HIDDEN.getState() || otherPlayer.getBattlefield()[row][column] == STATES.MISS.getState()) {
            currentPlayer.setBattlefield(row, column, STATES.MISS.getState());
            otherPlayer.setShipPositions(row, column, STATES.MISS.getState());
            System.out.println("\nYou missed!\n");
        }
        changePlayerTurn();
    }

    public static boolean isLastInList(String userSpecifiedCoordinate, Player player) {
        for (List<String> ship: player.getShipStatuses()) {
            for (int coordinates = 0; coordinates < ship.size(); coordinates++) {
                if (userSpecifiedCoordinate.equals(ship.get(coordinates)) && ship.size() == 1) {
                    ship.clear();
                    return true;
                } else if (userSpecifiedCoordinate.equals(ship.get(coordinates))) {
                    ship.remove(coordinates);
                    return false;
                }
            }
        }
        return false;
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

    public static void initialiseShipStatus(int character, int number, int shipStatusCounter, List<List<String>> shipStatuses) {
        String coordinate = ((char)(number + 65)) + String.valueOf((character + 1));
        shipStatuses.get(shipStatusCounter).add(coordinate);
    }

    public static void setCoordinates(int[] coordinates) {
        String[] coordinatesTemporary;
        String userInput;
        userInput = scanner.nextLine();
        coordinatesTemporary = userInput.split(" ");
        // startPointRow
        coordinates[0] = coordinatesTemporary[0].charAt(0) - 65;
        // endPointRow
        coordinates[1] = Integer.parseInt(coordinatesTemporary[0].substring(1)) - 1;
        // startColumnRow
        coordinates[2] = coordinatesTemporary[1].charAt(0) - 65;
        // endPointColumn
        coordinates[3] = Integer.parseInt(coordinatesTemporary[1].substring(1)) - 1;
    }

    public static void initialiseBattlefieldAndShipPositions(Player player) {
        for (int rowPos = 0; rowPos < maxRows; rowPos++) {
            for (int columnPos = 0; columnPos < maxColumns; columnPos++) {
                player.setBattlefield(rowPos, columnPos, STATES.HIDDEN.getState());
                player.setShipPositions(rowPos, columnPos, STATES.HIDDEN.getState());
            }
        }
    }

    public static char[][] emptyBattlefield() {
        char[][] battlefield = new char[maxRows][maxColumns];
        for (int rowPos = 0; rowPos < maxRows; rowPos++) {
            for (int columnPos = 0; columnPos < maxColumns; columnPos++) {
                battlefield[rowPos][columnPos] = STATES.HIDDEN.getState();
            }
        }
        return battlefield;
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
