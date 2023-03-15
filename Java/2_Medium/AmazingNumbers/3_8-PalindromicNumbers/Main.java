package numbers;

import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        printIntro();
    }

    public static boolean isEven(long number) {
        return number % 2 == 0;
    }

    public static boolean checkIfBuzzNumber(long number) {
        long remainder = number % 10;
        long naturalNumber = (remainder * 2) - (number / 10);
        return number > 0 && (naturalNumber % 7 == 0 || remainder == 7);
    }

    public static boolean checkIfNaturalNumber(long number) {
        if (number <= 0) {
            System.out.println("\nThe first parameter should be a natural number or zero\n");
            return false;
        } else return true;
    }

    public static boolean checkIfDuckNumber(long number) {
        String numberAsString = Long.toString(number);
        for (int j = 0; j != numberAsString.length(); j++) {
            if (numberAsString.charAt(j) == '0') {
                return true;
            }
        }
        return false;
    }

    public static boolean checkIfPalindromic(long number) {
        String numberAsString = Long.toString(number);
        String reversed = "";
        for (int i = numberAsString.length() - 1; i != -1; i--) {
            reversed = reversed + numberAsString.charAt(i);
        }
        return reversed.equals(numberAsString);
    }

    public static void printProperties(long number) {
        if (checkIfNaturalNumber(number)) {
            System.out.println("\nProperties of " + number);
            System.out.println("        even: " + isEven(number));
            System.out.println("         odd: " + isEven(number + 1));
            System.out.println("        buzz: " + checkIfBuzzNumber(number));
            System.out.println("        duck: " + checkIfDuckNumber(number));
            System.out.println(" palindromic: " + checkIfPalindromic(number) + "\n");
        }
    }

    public static void printIntro() {
        long number = 1;
        System.out.println("Welcome to Amazing Numbers!\n");
        System.out.println("Supported requests: ");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter 0 to exit.\n");
        while (true) {
            System.out.print("Enter a request: ");
            number = scanner.nextLong();
            if (number == 0) {
                break;
            }
            printProperties(number);
        }
        System.out.println("\nGoodbye!\n");
    }

}
