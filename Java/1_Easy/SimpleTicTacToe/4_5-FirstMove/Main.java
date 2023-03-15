package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int[][] matrix;
        System.out.print("Enter cells: ");
        String input = scanner.nextLine();
        //input is : X_X_O____

        printBoard(input);
        matrix = convertToArray(input);

        boolean isValidInput = false;
        String newCoordinates = "";

        while(!isValidInput) {
            System.out.print("Enter the coordinates: ");
            newCoordinates = scanner.nextLine().replaceAll("\\s+","");
            isValidInput = checkMatrix(matrix, newCoordinates);
        }
        matrix = updateMatrix(matrix, newCoordinates);
        input = convertMatrixToString(matrix);
        printBoard(input);

    }

    public static void printBoard(String input) {
        System.out.println("---------");
        System.out.println("| " + input.charAt(0) + " " + input.charAt(1) + " " + input.charAt(2) + " |");
        System.out.println("| " + input.charAt(3) + " " + input.charAt(4) + " " + input.charAt(5) + " |");
        System.out.println("| " + input.charAt(6) + " " + input.charAt(7) + " " + input.charAt(8) + " |");
        System.out.println("---------");
    }

    public static int[][] convertToArray(String input) {
        int[][] matrix = new int[3][3];
        int counter = 0;
        for(int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                matrix[row][column] = input.charAt(counter);
                counter++;
            }
        }
        return matrix;
    }

    public static void checkBoard(int[][] matrix) {

        int columnTotal = 0;
        int rowTotal = 0;
        int xWins = 0;
        int oWins = 0;

        //Checking rows
        for(int row = 0; row < 3; row ++) {
            for (int column = 0; column < 3; column++) {
                columnTotal += matrix[row][column];
                if (columnTotal == 237) {
                    oWins++;
                } else if (columnTotal == 264) {
                    xWins++;
                }
            }
            columnTotal = 0;
        }
        //Checking columns
        for(int column = 0; column < 3; column ++) {
            for (int row = 0; row < 3; row++) {
                rowTotal += matrix[row][column];
                if (rowTotal == 237) {
                    oWins++;
                } else if (rowTotal == 264) {
                    xWins++;
                }
            }
            rowTotal = 0;
        }
        //Checking diagonals
        if (matrix[0][0] + matrix[1][1] + matrix[2][2] == 237) {
            oWins++;
        } else if (matrix[0][0] + matrix[1][1] + matrix[2][2] == 264) {
            xWins++;
        }
        //Checking anti-diagonals
        if (matrix[0][2] + matrix[1][1] + matrix[2][0] == 237) {
            oWins++;
        } else if (matrix[0][2] + matrix[1][1] + matrix[2][0] == 264) {
            xWins++;
        }

        if(checkErrors(matrix, xWins, oWins)) {
            if (oWins == 1) {
                System.out.println("O wins");
            } else {
                System.out.println("X wins");
            }
        }
    }

    public static boolean checkErrors(int[][] matrix, int xWins, int oWins) {
        int xSum = 0;
        int oSum = 0;
        int otherSum = 0;
        int element;
        for(int row = 0; row < 3; row ++) {
            for (int column = 0; column < 3; column++) {
                element = matrix[row][column];
                if (element == 88) {
                    xSum++;
                } else if (element == 79) {
                    oSum++;
                } else {
                    otherSum ++;
                }
            }
        }
        int impossibleChecker = Math.abs(xSum - oSum);
        if (impossibleChecker == 2 || (xWins == 1 && oWins == 1)) {
            System.out.println("Impossible");
            return false;
        } else if (xSum == oSum && otherSum > 0 && xWins == 0 && oWins == 0) {
            System.out.println("Game not finished");
            return false;
        } else if (xWins == 0 && oWins == 0 && otherSum == 0) {
            System.out.println("Draw");
            return false;
        }
        return true;
    }

    public static int[][] updateMatrix(int[][] matrix, String newCoordinates) {
        int row = Integer.parseInt(String.valueOf(newCoordinates.charAt(0)));
        int column = Integer.parseInt(String.valueOf(newCoordinates.charAt(1)));
        matrix[row-1][column-1] = 'X';
        return matrix;
    }

    public static boolean checkMatrix(int[][] matrix, String coordinates) {
        try {
            int row = Integer.parseInt(String.valueOf(coordinates.charAt(0)));
            int column = Integer.parseInt(String.valueOf(coordinates.charAt(1)));
            if (row > 3 || column > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                return false;
            } else if (matrix[row-1][column-1] == 79 || matrix[row-1][column-1] == 88) {
                System.out.println("This cell is occupied! Choose another one!");
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException ex) {
            System.out.println("You should enter numbers!");
            return false;
        }
    }

    public static String convertMatrixToString(int[][] matrix) {
        int counter = 0;
        char[] newCharString = new char[9];
        for(int row = 0; row < 3; row ++) {
            for (int column = 0; column < 3; column++) {
                newCharString[counter] = (char) matrix[row][column];
                counter++;
                }
            }
        return String.valueOf(newCharString);
    }
}
