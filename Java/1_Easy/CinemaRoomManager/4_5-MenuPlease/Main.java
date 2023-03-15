package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {

        char[][] layout = null;
        boolean isMenuRunning = true;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows: ");
        int numberOfRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row: ");
        int numberOfSeatsPerRow = scanner.nextInt();

        int rowNumber;
        int seatNumber;

        while (isMenuRunning) {
            menu();
            int userInput = scanner.nextInt();
            switch (userInput) {
                case 1:
                    layout = viewLayout(layout, numberOfRows, numberOfSeatsPerRow);
                    printLayout(layout);
                    break;
                case 2:
                    layout = viewLayout(layout, numberOfRows, numberOfSeatsPerRow);
                    System.out.println("\nEnter a row number: ");
                    rowNumber = scanner.nextInt();
                    System.out.println("Enter a seat number in that row: ");
                    seatNumber = scanner.nextInt();
                    addSeating(layout, rowNumber, seatNumber);
                    System.out.println("Ticket price: $" + calculateTicketPrice(numberOfRows,numberOfSeatsPerRow,rowNumber));
                    break;
                case 0:
                    isMenuRunning = false;
                    scanner.close();
            }
        }
    }

    public static char[][] viewLayout(char[][] layout, int numberOfRows, int numberOfSeatsPerRow) {
        if (layout == null) {
            char[][] newLayout = new char[numberOfRows][numberOfSeatsPerRow];
            for (char[] chars : newLayout) {
                Arrays.fill(chars, 'S');
            }
            return newLayout;
        } else {
            return layout;
        }
    }

    public static void printLayout(char[][] layout) {
        System.out.println("\nCinema: ");
        System.out.print("  ");
        for(int i = 1; i <= layout[0].length; i++) {
            System.out.print(i + " ");
        }
        System.out.println("  ");
        for(int j = 0; j < layout.length; j++) {
            System.out.println((j + 1) + " " + Arrays.toString(layout[j]).replace(",","").replace("[","").replace("]","").trim());
        }
    }

    public static char[][] addSeating(char[][] layout, int rowNumber, int seatNumber) {
        layout[rowNumber - 1][seatNumber - 1] = 'B';
        return layout;
    }

    public static int calculateTicketPrice(int numberOfRows, int numberOfSeatsPerRow, int rowNumber) {
        int pricePerTicket;
        if (numberOfRows * numberOfSeatsPerRow <= 60 || (rowNumber <= (numberOfRows / 2))) {
            pricePerTicket = 10;
        } else {
            pricePerTicket = 8;
        }
        return pricePerTicket;
    }

    public static void menu() {
        System.out.println("\n1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("0. Exit");
    }
}