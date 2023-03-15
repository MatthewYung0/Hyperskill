package numbers;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        start();
    }

    public static boolean isEven(long number) {
        return number % 2 == 0;
    }

    public static boolean checkIfBuzzNumber(long number) {
        long remainder = number % 10;
        long naturalNumber = (remainder * 2) - (number / 10);
        return number > 0 && (naturalNumber % 7 == 0 || remainder == 7);
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

    public static boolean checkIfGapful(long number) {
        long temp = number;
        String[] numArray = new String[String.valueOf(number).length()];
        for (int i = String.valueOf(number).length() - 1; i >= 0; i--) {
            numArray[i] = String.valueOf(temp % 10);
            temp /= 10;
        }
        long convertedNumber = Long.parseLong(Arrays.toString(numArray).replaceAll("[\\[\\], ]",""));
        long divisibleNumber = Long.parseLong(numArray[0] + numArray[numArray.length - 1]);
        return number > 99 && convertedNumber % divisibleNumber == 0;
    }

    public static void printDoubleNumberProperties(long numberOne, long numberTwo) {
        System.out.println(" ");
        for (long i = numberOne; i != (numberOne + numberTwo); i++) {
            System.out.print(i + " is");
            if (checkIfBuzzNumber(i)) {
                System.out.print(" buzz,");
            }
            if (checkIfDuckNumber(i)) {
                System.out.print(" duck,");
            }
            if (checkIfPalindromic(i)) {
                System.out.print(" palindromic,");
            }
            if (checkIfGapful(i)) {
                System.out.print(" gapful,");
            }
            if (isEven(i)) {
                System.out.print(" even\n");
            } else {
                System.out.print(" odd\n");
            }
        }
        System.out.println(" ");
    }

    public static void printSingleNumberProperties(long number) {
        System.out.println("\nProperties of " + number);
        System.out.println("        buzz: " + checkIfBuzzNumber(number));
        System.out.println("        duck: " + checkIfDuckNumber(number));
        System.out.println(" palindromic: " + checkIfPalindromic(number));
        System.out.println("      gapful: " + checkIfGapful(number));
        System.out.println("        even: " + isEven(number));
        System.out.println("         odd: " + isEven(number + 1) + "\n");
    }

    public static void printIntro() {
        System.out.println("\nWelcome to Amazing Numbers!\n");
        System.out.println("Supported requests: ");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println("* the first parameter represents a starting number;");
        System.out.println("* the second parameter shows how many consecutive numbers are to be processed;");
        System.out.println("- separate the parameters with one space;");
        System.out.println("- enter 0 to exit.\n");
    }

    public static void start() {
        printIntro();
        while (true) {
            System.out.print("Enter a request: ");
            String number = scanner.nextLine();
            String[] numberArray = number.split(" ");
            if (number.equals("")) {
                printIntro();
            } else if (checkNumbers(numberArray)) {
                if (numberArray[0].equals("0")) {
                    break;
                } else if (numberArray.length == 1) {
                    printSingleNumberProperties(Long.parseLong(numberArray[0]));
                } else {
                    printDoubleNumberProperties(Long.parseLong(numberArray[0]), Long.parseLong(numberArray[1]));
                }
            }
        }
        System.out.println("\nGoodbye!\n");
        scanner.close();
    }

    public static boolean checkNumbers(String[] numberArray) {
        try {
            Long.parseLong(numberArray[0]);
            if (Long.parseLong(numberArray[0]) < 0 ) {
                System.out.println("\nThe first parameter should be a natural number or zero.\n");
                return false;
            }
            if (numberArray.length > 1) {
                try {
                    Long.parseLong(numberArray[1]);
                    if (Long.parseLong(numberArray[1]) < 0 ) {
                        System.out.println("\nThe second parameter should be a natural number.\n");
                        return false;
                    }
                } catch (Exception two) {
                    System.out.println("\nThe second parameter should be a natural number.\n");
                    return false;
                }
            }
        } catch (Exception one) {
            System.out.println("\nThe first parameter should be a natural number or zero.\n");
            return false;
        }
        return true;
    }

}
