package bullscows;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String secretNumber = "9305";
        String guess = "";

        int counter = 1;

        System.out.println("The secret code is prepared: ****\n");

        while (!guess.equals(secretNumber)) {
            System.out.println("Turn " + counter + ". Answer: ");
            if (counter == 1) {
                guess = "1234";
            } else {
                guess = "9305";
                //guess = scanner.nextLine();
            }
            System.out.println(guess);
            printResult(checkForBulls(secretNumber, guess), checkForCows(secretNumber, guess));
            System.out.print("\n");
            counter++;
        }

        System.out.println("Congrats! The secret code is " + secretNumber);

    }

    public static int checkForBulls(String secretNumber, String guess) {
        int noOfBulls = 0;
        for (int i = 0; i < guess.length(); i++) {
            if (secretNumber.charAt(i) == guess.charAt(i)) {
                noOfBulls++;
            }
        }
        return noOfBulls;
    }

    public static int checkForCows(String secretNumber, String guess) {
        int noOfCows = 0;
        for (int i = 0; i < secretNumber.length(); i++) {
            for (int j = 0; j < guess.length(); j++) {
                if (i != j && secretNumber.charAt(i) == guess.charAt(j)) {
                    noOfCows++;
                    break;
                }
            }
        }
        return noOfCows;
    }

    public static void printResult(int bulls, int cows) {
        System.out.print("Grade: ");
        if (bulls > 0 && cows > 0) {
            System.out.println(bulls + " bull and " + cows + " cow.");
        } else if (bulls > 0) {
            System.out.println(bulls + " bull.");
        } else if (cows > 0) {
            System.out.println(cows + " cow.");
        } else {
            System.out.println("None.");
        }
    }
}
