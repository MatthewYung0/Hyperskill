package cinema;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    private static int numberOfPurchasedTickets;
    private static int totalIncome;
    private static double percentage;
    private static int currentIncome;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean isMenuRunning = true;

        System.out.println("Enter the number of rows: ");
        int numberOfRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row: ");
        int numberOfSeatsPerRow = scanner.nextInt();

        char[][] layout = createLayout(numberOfRows, numberOfSeatsPerRow);

        while (isMenuRunning) {
            menu();
            int userInput = scanner.nextInt();
            switch (userInput) {
                case 1:
                    printLayout(layout);
                    break;
                case 2:
                    layout = checkSeating(layout, numberOfRows, numberOfSeatsPerRow);
                    break;
                case 3:
                    showStatistics(layout, numberOfRows, numberOfSeatsPerRow);
                    break;
                case 0:
                    isMenuRunning = false;
                    scanner.close();
            }
        }
    }

    public static char[][] createLayout(int numberOfRows, int numberOfSeatsPerRow) {
        char[][] newLayout = new char[numberOfRows][numberOfSeatsPerRow];
        for (char[] chars : newLayout) {
            Arrays.fill(chars, 'S');
        }
        return newLayout;
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

    public static char[][] checkSeating(char[][] layout, int numberOfRows, int numberOfSeatsPerRow) {

        boolean isCorrectInput = false;

        while(!isCorrectInput) {
            System.out.println("\nEnter a row number: ");
            int rowNumber = scanner.nextInt();
            System.out.println("Enter a seat number in that row: ");
            int seatNumber = scanner.nextInt();

            if (rowNumber > numberOfRows || seatNumber > numberOfSeatsPerRow) {
                System.out.println("Wrong input!");
            } else if (layout[rowNumber - 1][seatNumber - 1] == 'B') {
                System.out.println("That ticket has already been purchased!");
            } else {
                char[][] updatedLayout = addSeating(layout, rowNumber, seatNumber);
                currentIncome += calculateTicketPrice(numberOfRows, numberOfSeatsPerRow, rowNumber);
                numberOfPurchasedTickets += 1;
                isCorrectInput = true;
                return updatedLayout;
            }
        }
        return layout;
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
        System.out.println("Ticket price: $" + pricePerTicket);
        return pricePerTicket;
    }

    public static void menu() {
        System.out.println("\n1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    public static void showStatistics(char[][] layout, int numberOfRows, int numberOfSeatsPerRow) {

        double totalSeats = numberOfRows * numberOfSeatsPerRow;

        totalIncome = calculateTotalIncome(numberOfRows,numberOfSeatsPerRow);
        percentage = (numberOfPurchasedTickets / totalSeats) * 100;
        BigDecimal bd = new BigDecimal(percentage).setScale(2, RoundingMode.HALF_UP);

        System.out.println("Number of purchased tickets: " + (int) numberOfPurchasedTickets);
        System.out.println("Percentage: " + bd + "%");
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }

    public static int calculateTotalIncome(int numberOfRows, int numberOfSeatsPerRow) {
        if (numberOfRows * numberOfSeatsPerRow <= 60) {
            return numberOfRows * numberOfSeatsPerRow * 10;
        } else {
            int frontRows = (numberOfRows - (numberOfRows/2) - 1);
            int backRows = (numberOfRows - (numberOfRows/2));
            return (9 * (frontRows * 10 + backRows * 8));
        }
    }
}