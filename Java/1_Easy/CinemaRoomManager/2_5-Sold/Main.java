package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        
        int pricePerTicket;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows: ");
        int numberOfRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row: ");
        int numberOfSeatsPerRow = scanner.nextInt();

        if (numberOfRows * numberOfSeatsPerRow <= 60) {
            pricePerTicket = 10 * numberOfRows * numberOfSeatsPerRow;
        } else {
            pricePerTicket = ((numberOfRows / 2) * numberOfSeatsPerRow * 10)
                    + ((numberOfRows - (numberOfRows / 2)) * numberOfSeatsPerRow * 8);
        }

        System.out.println("Total income:");
        System.out.println("$" + pricePerTicket);
    }
}