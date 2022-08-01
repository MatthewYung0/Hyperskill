package bullscows;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<String> numberTemplate = new ArrayList<String>(10);
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        String codeLength;

        do {
            // Getting user input
            System.out.println("\nPlease, enter the secret code's length:");
            codeLength = scanner.nextLine();
        } while (!checkValidLength(codeLength));

        String number = createRandomNumber(codeLength);
        System.out.println("Okay, let's start a game!");

        String userGuess;
        int counter = 1;

        do {
            System.out.println("Turn " + counter + ": ");
            userGuess = scanner.nextLine();
            printResult(checkForBulls(number, userGuess), checkForCows(number, userGuess));
            counter++;
        } while(!userGuess.equals(number));

        System.out.println("Congratulations! You guess the secret code.");

    }

    public static boolean checkValidLength(String userInput) {
        if (Integer.parseInt(userInput) > 9) {
            System.out.println("Error: can't generate a secret number with a length of " + userInput + " because there aren't enough unique digits.");
            return false;
        }
        return true;
    }

    public static String createRandomNumber(String codeLength) {
        // Variable for return random number
        String number = "";

        boolean isValidNumber = false;
        initialiseNumberTemplate();
        long pseudoRandomNumber = System.nanoTime();

        while (!isValidNumber) {
            // Converting pseudoRandomNumber from long to String
            String randomNumber = String.valueOf(pseudoRandomNumber);
            // Removing last 2x 0's from randomly generated number
            randomNumber = randomNumber.substring(0, (randomNumber.length() - 2));
            for (int i = 1; i <= Integer.parseInt(codeLength); i++) {
                String lastDigit = String.valueOf(randomNumber.charAt(randomNumber.length() - i));
                if (numberTemplate.contains(lastDigit)) {
                    numberTemplate.remove(lastDigit);
                } else {
                    pseudoRandomNumber = System.nanoTime();
                    initialiseNumberTemplate();
                    number = "";
                    break;
                }
                if (i == Integer.parseInt(codeLength)) {
                    number = randomNumber.substring(randomNumber.length() - i);
                    isValidNumber = true;
                    break;
                }
            }
        }
        return number;
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

    public static void printResult(int noOfBulls, int noOfCows) {
        String bullString = "";
        String cowString = "";
        System.out.print("Grade: ");

        if (noOfBulls > 1 || noOfBulls == 0) {
            bullString = "bulls";
        } else if (noOfBulls == 1) {
            bullString = "bull";
        }

        if (noOfCows > 1 || noOfCows == 0) {
            cowString = "cows";
        } else if (noOfCows == 1) {
            cowString = "cow";
        }

        if (noOfBulls != 0 && noOfCows == 0) {
            System.out.println(noOfBulls + " " + bullString);
        } else if (noOfBulls == 0 && noOfCows != 0) {
            System.out.println(noOfCows + " " + cowString);
        } else {
            System.out.println(noOfBulls + " " + bullString + " and " + noOfCows + " " + cowString);
        }
    }

    public static void initialiseNumberTemplate() {
        for (int i = 0; i < 10; i++) {
            if (!numberTemplate.contains(String.valueOf(i))) {
                numberTemplate.add(String.valueOf(i));
            }
        }
    }
}
