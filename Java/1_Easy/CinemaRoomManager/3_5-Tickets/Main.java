package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows: ");
        int numberOfRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row: ");
        int numberOfSeatsPerRow = scanner.nextInt();

        char[][] layout = generateBaseLayout(numberOfRows,numberOfSeatsPerRow);

        System.out.println(" ");

        System.out.println("Enter a row number: ");
        int rowNumber = scanner.nextInt();
        System.out.println("Enter a seat number in that row: ");
        int seatNumber = scanner.nextInt();

        generateBaseLayoutWithSeatSelection(rowNumber, seatNumber, numberOfRows, numberOfSeatsPerRow, layout);

        int ticketPrice = calculateTicketPrice(numberOfRows, numberOfSeatsPerRow, rowNumber);

        System.out.println(" ");
        System.out.println("Ticket price: $" + ticketPrice);
        System.out.println(" ");

    }

    public static char[][] generateBaseLayout(int numberOfRows, int numberOfSeatsPerRow) {
        char[][] layout = new char[numberOfRows][numberOfSeatsPerRow];
        for(int i = 0; i < layout.length; i++) {
            for(int j = 0; j < layout[i].length; j++) {
                layout[i][j] = 'S';
            }
        }

        System.out.println(" ");
        System.out.println("Cinema:");
        System.out.print("  ");

        for(int num = 1; num <= numberOfSeatsPerRow; num++) {
            System.out.print(num + " ");
        }

        System.out.println(" ");
        for(int k = 0; k < layout.length; k++) {
            System.out.println((k+1) + " " + Arrays.toString(layout[k]).replace(",","").replace("[","").replace("]","").trim());
        }
        return layout;
    }

    public static void generateBaseLayoutWithSeatSelection(int rowNumber, int seatNumber, int numberOfRows, int numOfSeatsPerRow, char[][] layout) {

        layout[rowNumber-1][seatNumber-1] = 'B';

        System.out.println(" ");
        System.out.println("Cinema:");
        System.out.print("  ");

        for(int i = 1; i <= numOfSeatsPerRow; i++) {
            System.out.print(i + " ");
        }

        System.out.println(" ");

        for(int j = 0; j < numberOfRows; j++) {
            System.out.println((j + 1) + " " + Arrays.toString(layout[j]).replace(",","").replace("[","").replace("]","").trim());
        }
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

}