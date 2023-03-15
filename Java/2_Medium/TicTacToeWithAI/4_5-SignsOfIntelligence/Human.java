package tictactoe;

import java.util.List;
import java.util.Scanner;

public class Human extends User {

    private final char symbol;

    public Human(char symbol) {
        this.symbol = symbol;
    }

    public void run(List<Character> gameField) {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Enter the coordinates: ");
            String coordinates = scanner.nextLine();
            if (checkCoordinates(coordinates, gameField)) {
                playerMakesMove(coordinates, gameField);
                break;
            }
        } while (true);
    }

    public boolean checkCoordinates(String coordinates, List<Character> gameField) {
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

    public void playerMakesMove(String coordinates, List<Character> gameField) {
        int row = coordinates.charAt(0) - 49;
        int column = coordinates.charAt(2) - 49;
        gameField.set(getIndexFromCoordinates(row, column), symbol);
    }

    public int getIndexFromCoordinates(int row, int column) {
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
}
