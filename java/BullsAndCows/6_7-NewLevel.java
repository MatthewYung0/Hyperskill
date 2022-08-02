package bullscows;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static ArrayList<String> numberTemplate = new ArrayList<String>(36);
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        String codeLength;
        String range;
        do {
            // Getting user input for code length
            System.out.println("\nInput the length of the secret code: ");
            codeLength = scanner.nextLine();
            // Getting user input for range of characters
            System.out.println("Input the number of possible symbols in the code: ");
            range = scanner.nextLine();
        } while (!checkValidLength(codeLength));
        String number = createRandomNumber(codeLength, range);
        printIntro(codeLength, range);

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

    public static void printIntro(String codeLength, String range) {

        String lastCharacter = numberTemplate.get(Integer.parseInt(range) - 1);

        System.out.print("The secret is prepared: ");
        for (int i = 0; i < Integer.parseInt(codeLength); i++) {
            System.out.print("*");
        }
        System.out.print(" (");

        if (Integer.parseInt(range) == 0) {
            System.out.println(lastCharacter + ")");
        } else if (Integer.parseInt(range) != 0 && Integer.parseInt(range) < 11) {
            System.out.println("0-" + lastCharacter + ").");
        } else if (Integer.parseInt(range) == 11) {
            System.out.println("0-9, " + lastCharacter + ").");
        } else {
            System.out.println("0-9, a-" + lastCharacter + ").");
        }
        System.out.println("Okay, let's start a game!");
    }

    public static String createRandomNumber(String codeLength, String range) {
        // Variable for return random number
        StringBuilder code = new StringBuilder();
        // Creating template within specified range
        initialiseTemplate(Integer.parseInt(range));
        Random characterGetter = new Random();
        // Variable for holding the index
        int index;
        for (int element = 0; element < Integer.parseInt(codeLength); element++) {
            // Getting random index number to be used for numberTemplate
            index = characterGetter.nextInt(Integer.parseInt(range));
            // Check if element already exists in string code. If exists, decrease element by 1
            if (code.toString().contains(numberTemplate.get(index))) {
                element--;
                // Appends character from numberTemplate at index
            } else {
                code.append(numberTemplate.get(index));
            }
        }
        return code.toString();
    }

    public static boolean checkValidLength(String codeLength) {
        if (Integer.parseInt(codeLength) > 36) {
            System.out.println("Error: can't generate a secret number with a length of " + codeLength + " because there aren't enough unique digits.");
            return false;
        }
        return true;
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

    public static void initialiseTemplate(int range) {
        int counter = 0;
        for (int number = 0; number < 10 && counter < range; number++) {
            if (!numberTemplate.contains(String.valueOf(number))) {
                numberTemplate.add(String.valueOf(number));
            }
            counter++;
        }
        for (int character = 97; character < 123 && counter < range; character++) {
            if (!numberTemplate.contains(String.valueOf((char) character))) {
                numberTemplate.add((String.valueOf((char) character)));
            }
            counter++;
        }
    }
}
