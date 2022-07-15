package bullscows;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<String> numberTemplate = new ArrayList<String>(10);

    public static void main(String[] args) {
        boolean isValidNumber = false;
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        if (Integer.parseInt(userInput) > 9) {
            System.out.println("Error: can't generate a secret number with a length of " + userInput + " because there aren't enough unique digits.");
        } else {
            initialiseNumberTemplate();
            long pseudoRandomNumber = System.nanoTime();
            String number = "";
            while (!isValidNumber) {
                String randomNumber = String.valueOf(pseudoRandomNumber);
                randomNumber = randomNumber.substring(0, (randomNumber.length() - 2));
                for (int i = 1; i <= Integer.parseInt(userInput); i++) {
                    String lastDigit = String.valueOf(randomNumber.charAt(randomNumber.length() - i));
                    if (numberTemplate.contains(lastDigit)) {
                        numberTemplate.remove(lastDigit);
                    } else {
                        pseudoRandomNumber = System.nanoTime();
                        initialiseNumberTemplate();
                        number = "";
                        break;
                    }
                    if (i == Integer.parseInt(userInput)) {
                        number = randomNumber.substring(randomNumber.length() - i);
                        isValidNumber = true;
                    }
                }
            }
            System.out.println("The random secret number is " + number + ".");
        }
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
            System.out.print(bulls + " bull(s) and " + cows + " cow(s). ");
        } else if (bulls > 0) {
            System.out.print(bulls + " bull(s). ");
        } else if (cows > 0) {
            System.out.print(cows + " cow(s). ");
        } else {
            System.out.print("None. ");
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
