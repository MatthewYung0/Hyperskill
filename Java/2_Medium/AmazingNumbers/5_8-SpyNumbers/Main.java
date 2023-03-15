package numbers;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public enum PROPERTIES {EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY}

    public static void main(String[] args) {
        start();
    }

    public static boolean checkIfEven(long number) {
        return number % 2 == 0;
    }

    public static boolean checkIfBuzz(long number) {
        long remainder = number % 10;
        long naturalNumber = (remainder * 2) - (number / 10);
        return number > 0 && (naturalNumber % 7 == 0 || remainder == 7);
    }

    public static boolean checkIfDuck(long number) {
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
        String[] numArray = convertToStringArray(number);
        long convertedNumber = Long.parseLong(Arrays.toString(numArray).replaceAll("[\\[\\], ]", ""));
        long divisibleNumber = Long.parseLong(numArray[0] + numArray[numArray.length - 1]);
        return number > 99 && convertedNumber % divisibleNumber == 0;
    }

    public static boolean checkIfSpy(long number) {
        long product = 1;
        long sum = 0;
        String[] numArray = convertToStringArray(number);
        for (String s : numArray) {
            product *= Long.parseLong(s);
            sum += Long.parseLong(s);
        }
        return product == sum;
    }

    public static void searchNumberProperties(long numberOne, long numberTwo, String property) {
        int counter = 0;
        String[] array = new String[(int) numberTwo];

        while (counter < numberTwo) {
            if ((property.toLowerCase().equals("even") && checkIfEven(numberOne)) ||
                    (property.toLowerCase().equals("odd") && !checkIfEven(numberOne)) ||
                    (property.toLowerCase().equals("buzz") && checkIfBuzz(numberOne)) ||
                    (property.toLowerCase().equals("duck") && checkIfDuck(numberOne)) ||
                    (property.toLowerCase().equals("palindromic") && checkIfPalindromic(numberOne)) ||
                    (property.toLowerCase().equals("gapful") && checkIfGapful(numberOne)) ||
                    (property.toLowerCase().equals("spy") && checkIfSpy(numberOne))) {
                array[counter] = Long.toString(numberOne);
                counter++;
            }
            numberOne++;
        }
        System.out.println(" ");
        for(int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " is ");
            printProperties(Long.parseLong(array[i]));
        }
        System.out.println(" ");
    }

    public static void printProperties(long number) {
        if (checkIfBuzz(number)) {
            System.out.print(PROPERTIES.BUZZ.toString().toLowerCase() + ", ");
        }
        if (checkIfDuck(number)) {
            System.out.print(PROPERTIES.DUCK.toString().toLowerCase() + ", ");
        }
        if (checkIfPalindromic(number)) {
            System.out.print(PROPERTIES.PALINDROMIC.toString().toLowerCase() + ", ");
        }
        if (checkIfGapful(number)) {
            System.out.print(PROPERTIES.GAPFUL.toString().toLowerCase() + ", ");
        }
        if (checkIfSpy(number)) {
            System.out.print(PROPERTIES.SPY.toString().toLowerCase() + ", ");
        }
        if (checkIfEven(number)) {
            System.out.print(PROPERTIES.EVEN.toString().toLowerCase() + " \n");
        } else {
            System.out.print(PROPERTIES.ODD.toString().toLowerCase() + " \n");
        }
    }

    public static void printDoubleNumberProperties(long numberOne, long numberTwo) {
        System.out.println(" ");
        for (long i = numberOne; i != (numberOne + numberTwo); i++) {
            System.out.print(i + " is ");
            printProperties(i);
        }
        System.out.println(" ");
    }

    public static void printSingleNumberProperties(long number) {
        System.out.println("\nProperties of " + number);
        System.out.println("        buzz: " + checkIfBuzz(number));
        System.out.println("        duck: " + checkIfDuck(number));
        System.out.println(" palindromic: " + checkIfPalindromic(number));
        System.out.println("      gapful: " + checkIfGapful(number));
        System.out.println("         spy: " + checkIfSpy(number));
        System.out.println("        even: " + checkIfEven(number));
        System.out.println("         odd: " + checkIfEven(number + 1) + "\n");
    }

    public static void printIntro() {
        System.out.println("Welcome to Amazing Numbers!\n");
        System.out.println("Supported requests: ");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println("* the first parameter represents a starting number;");
        System.out.println("* the second parameter shows how many consecutive numbers are to be processed;");
        System.out.println("- two natural numbers and a property to search for;");
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
                } else if (numberArray.length == 2) {
                    printDoubleNumberProperties(Long.parseLong(numberArray[0]), Long.parseLong(numberArray[1]));
                } else if (numberArray.length == 3) {
                    searchNumberProperties(Long.parseLong(numberArray[0]), Long.parseLong(numberArray[1]), numberArray[2]);
                }
            }
        }
        System.out.println("\nGoodbye!\n");
        scanner.close();
    }

    public static boolean checkNumbers(String[] numberArray) {

         try {
            Long.parseLong(numberArray[0]);
            if (Long.parseLong(numberArray[0]) < 0) {
                System.out.println("\nThe first parameter should be a natural number or zero.\n");
                return false;
            }
            if (numberArray.length > 1) {
                try {
                    Long.parseLong(numberArray[1]);
                    if (Long.parseLong(numberArray[1]) < 0) {
                        System.out.println("\nThe second parameter should be a natural number.\n");
                        return false;
                    }
                } catch (Exception two) {
                    System.out.println("\nThe second parameter should be a natural number.\n");
                    return false;
                }
                if (numberArray.length > 2) {
                    for (PROPERTIES property : PROPERTIES.values()) {
                        if (numberArray[2].toLowerCase().equals(property.toString().toLowerCase())) {
                            return true;
                        }
                    }
                    System.out.println("\nThe property [" + numberArray[2].toUpperCase() + "] is wrong");
                    System.out.println("Available properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD]\n");
                    return false;
                }
            }
        } catch (Exception one) {
            System.out.println("\nThe first parameter should be a natural number or zero.\n");
            return false;
        }
        return true;
    }

    public static String[] convertToStringArray(long number) {
        long temp = number;
        String[] numArray = new String[String.valueOf(number).length()];
        for (int i = String.valueOf(number).length() - 1; i >= 0; i--) {
            numArray[i] = String.valueOf(temp % 10);
            temp /= 10;
        }
        return numArray;
    }
}
