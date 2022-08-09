package tictactoe;

import java.util.*;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static List<Character> gameField = new ArrayList<Character>(9);
    static int row, column, xWins = 0, oWins = 0;

    public static void main(String[] args) {
        setGameField();
        printGameField();
        runGame();
    }

    public static void runGame() {
        boolean isGameOver = false;
        boolean isX;
        // Checking which symbol begins first
        isX = Collections.frequency(gameField, 'O') >= Collections.frequency(gameField, 'X');
        do {
            System.out.print("Enter the coordinates: ");
            String coordinates = scanner.nextLine();
            if (checkCoordinates(coordinates)) {
                setRowAndColumn(coordinates);
                if (isX) {
                    gameField.set(getIndexFromCoordinates(row, column), 'X');
                    isX = false;
                } else {
                    gameField.set(getIndexFromCoordinates(row, column), 'O');
                    isX = true;
                }
                printGameField();
                isGameOver = isGameOver();
            }
        } while(!isGameOver);
    }

    public static boolean isGameOver() {
        // Checking rows & checking columns
        checkRows();
        checkColumns();
        checkDiagonally();
        if (xWins > oWins) {
            System.out.println("X wins");
            return true;
        } else if(xWins < oWins) {
            System.out.println("O wins");
            return true;
        } else if (Collections.frequency(gameField, ' ') == 0) {
            System.out.println("Draw");
            return true;
        } else {
            System.out.println("Game not finished");
            return true;
        }
    }

    public static void checkWins(int score) {
        final int xWinCondition = 264;
        final int oWinCondition = 237;
        if (score == xWinCondition) {
            xWins++;
        } else if (score == oWinCondition) {
            oWins++;
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

    public static void setRowAndColumn(String coordinates) {
        row = coordinates.charAt(0) - 49;
        column = coordinates.charAt(2) - 49;
    }

    public static boolean checkCoordinates(String coordinates) {
        // Checks if input are numbers
        try {
            Integer.parseInt(String.valueOf(coordinates.charAt(0)));
            Integer.parseInt(String.valueOf(coordinates.charAt(2)));
        } catch (Exception e) {
            System.out.println("You should enter numbers!");
            return false;
        }
        // Checks if coordinates are within 1 - 3
        if (coordinates.charAt(0) - 49 > 2 || coordinates.charAt(2) - 49 > 2 || coordinates.charAt(0) - 49 < 0 || coordinates.charAt(2) - 49 < 0) {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }
        // Checks if cell is occupied
        if (!gameField.get(getIndexFromCoordinates(coordinates.charAt(0) - 49, coordinates.charAt(2) - 49)).equals(' ')) {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }
        return true;
    }

    public static int getIndexFromCoordinates(int row, int column) {
        int arrayListIndex;
        if (row == 0 && column == 0) {
            arrayListIndex = 0;
        } else if (row == 0 && column == 1) {
            arrayListIndex = 1;
        } else if (row == 0 && column == 2) {
            arrayListIndex = 2;
        } else if (row == 1 && column == 0) {
            arrayListIndex = 3;
        } else if (row == 1 && column == 1) {
            arrayListIndex = 4;
        } else if (row == 1 && column == 2) {
            arrayListIndex = 5;
        } else if (row == 2 && column == 0) {
            arrayListIndex = 6;
        } else if (row == 2 && column == 1) {
            arrayListIndex = 7;
        } else {
            arrayListIndex = 8;
        }
        return arrayListIndex;
    }

    public static void printGameField() {
        System.out.println("---------");
        for (int i = 0; i < gameField.size(); i+=3) {
            System.out.print("| ");
            System.out.print(gameField.get(i) + " " + gameField.get(i + 1) + " " + gameField.get(i + 2));
            System.out.println(" |");
        }
        System.out.println("---------");
    }

    public static void setGameField() {
        // Getting initial status of gameField from user
        System.out.print("Enter the cells: ");
        String initialStatus = scanner.nextLine();
        // Setting gameField with the initial status
        for (int i = 0; i < initialStatus.length(); i++) {
            if (initialStatus.charAt(i) == '_') {
                gameField.add(' ');
            } else {
                gameField.add(initialStatus.charAt(i));
            }
        }

    }

}
