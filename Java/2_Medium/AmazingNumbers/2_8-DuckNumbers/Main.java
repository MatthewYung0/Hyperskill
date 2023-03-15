package numbers;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Enter a natural number :");
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        printProperties(number);
    }

    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static boolean checkIfBuzzNumber(int number) {
        int remainder = number % 10;
        int naturalNumber = (remainder * 2) - (number / 10);
        return number > 0 && (naturalNumber % 7 == 0 || remainder == 7);
    }

    public static boolean checkIfNaturalNumber(int number) {
        if (number <= 0) {
            System.out.println("This number is not natural!");
            return false;
        } else return true;
    }

    public static boolean checkIfDuckNumber(int number) {
        String numberAsString = Integer.toString(number);
        for (int i = 0; i != numberAsString.length(); i++) {
            if(numberAsString.charAt(i) != '0' && i != numberAsString.length() - 1) {
                for (int j = i + 1; j != numberAsString.length(); j++) {
                    if (numberAsString.charAt(j) == '0') {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void printProperties(int number) {
        if(checkIfNaturalNumber(number)) {
            System.out.println("Properties of " + number);
            System.out.println("        even: " + isEven(number));
            System.out.println("         odd: " + isEven(number + 1));
            System.out.println("        buzz: " + checkIfBuzzNumber(number));
            System.out.println("        duck: " + checkIfDuckNumber(number));
        }
    }

}
