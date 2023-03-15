package tictactoe;

import java.util.Scanner;

public class Main {

    public static int xTurn = 0;
    public static int xWins = 0;
    public static int oWins = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean gameIsWon = false;
        int[][] matrix;

        String input = "            ";
        printBoard(input);

        matrix = convertToArray(input);
        String newCoordinates = "";
        while (!gameIsWon) {
            System.out.print("Enter the coordinates: ");
            newCoordinates = scanner.nextLine().replaceAll("\\s+","");
            gameIsWon = checkForWin(matrix, newCoordinates, xTurn);
//            System.out.println(Arrays.deepToString(matrix));
        }
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

    public static boolean checkForWin(int[][] matrix, String newCoordinates, int xTurn) {

        int columnTotal = 0;
        int rowTotal = 0;

        //Checking rows
        for(int row = 0; row < 3; row ++) {
            for (int column = 0; column < 3; column++) {
                columnTotal += matrix[row][column];
                if (columnTotal == 237) {
                    oWins++;
                } else if (columnTotal == 264) {
                    xWins++;
                }
                columnTotal = 0;
            }
        }
        //Checking columns & for empty cells
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

        if (checkErrors(matrix, xWins, oWins, newCoordinates)) {

            matrix = updateMatrix(matrix, newCoordinates, xTurn);
            String newInput = convertMatrixToString(matrix);
            printBoard(newInput);

            //Checking rows
            for(int row = 0; row < 3; row ++) {
                for (int column = 0; column < 3; column++) {
                    columnTotal += matrix[row][column];
                    if (columnTotal == 237) {
                        oWins++;
                    } else if (columnTotal == 264) {
                        xWins++;
                    }
                    columnTotal = 0;
                }
            }
            //Checking columns & for empty cells
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

            boolean isEmpty = false;

            for(int row = 0; row < 3; row ++) {
                for (int column = 0; column < 3; column++) {
                    if (matrix[row][column] == 32 || matrix[row][column] == 95) {
                        isEmpty = true;
                    }
                }
            }

            if (xWins == 0 && oWins == 0 && !isEmpty) {
                System.out.println("Draw");
                System.exit(0);
            }

            if (oWins == 1) {
                System.out.println("O wins!");
                return true;
            } else if (xWins == 1) {
                System.out.println("X wins!");
                return true;
            }
        }
        return false;
    }

    public static boolean checkErrors(int[][] matrix, int xWins, int oWins, String coordinates) {

        boolean isEmpty = false;

        for(int row = 0; row < 3; row ++) {
            for (int column = 0; column < 3; column++) {
                if (matrix[row][column] == 32 || matrix[row][column] == 95) {
                    isEmpty = true;
                }
            }
        }
//        int impossibleChecker = Math.abs(xSum - oSum);
        try {
            int row = Integer.parseInt(String.valueOf(coordinates.charAt(0)));
            int column = Integer.parseInt(String.valueOf(coordinates.charAt(1)));
            if (row > 3 || column > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                return false;
            } else if (matrix[row-1][column-1] == 79 || matrix[row-1][column-1] == 88 && isEmpty) {
                System.out.println("This cell is occupied! Choose another one!");
                return false;
            } /*else if (impossibleChecker == 2 || (xWins == 1 && oWins == 1)) {
                System.out.println("Impossible");
                return false;
            } else if (xSum == oSum && otherSum > 0 && xWins == 0 && oWins == 0) {
                System.out.println("Game not finished");
                return false;
            }*/ else if (xWins == 0 && oWins == 0 && !isEmpty) {
                System.out.println("Draw");
                System.exit(0);
            } else {
                return true;
            }
        } catch (NumberFormatException ex) {
            System.out.println("You should enter numbers!");
            return false;
        }
        return false;
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

    public static int[][] updateMatrix(int[][] matrix, String newCoordinates, int xTurn) {
        int row = Integer.parseInt(String.valueOf(newCoordinates.charAt(0)));
        int column = Integer.parseInt(String.valueOf(newCoordinates.charAt(1)));
        if (xTurn % 2 == 0) {
            matrix[row-1][column-1] = 'X';
            updateXTurn();
            return matrix;
        } else {
            matrix[row-1][column-1] = 'O';
            updateXTurn();
            return matrix;
        }
    }
    public static void printBoard(String input) {
        System.out.println("---------");
        System.out.println("| " + input.charAt(0) + " " + input.charAt(1) + " " + input.charAt(2) + " |");
        System.out.println("| " + input.charAt(3) + " " + input.charAt(4) + " " + input.charAt(5) + " |");
        System.out.println("| " + input.charAt(6) + " " + input.charAt(7) + " " + input.charAt(8) + " |");
        System.out.println("---------");
    }

    public static int updateXTurn() {
        return xTurn++;
    }


}
