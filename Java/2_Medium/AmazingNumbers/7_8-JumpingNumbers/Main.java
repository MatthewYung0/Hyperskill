package numbers;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public enum PROPERTIES {EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING}

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        printIntro();
        StringBuilder properties = new StringBuilder();
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
                    searchNumberProperties(Long.parseLong(numberArray[0]));
                } else if (numberArray.length == 2) {
                    searchNumberProperties(Long.parseLong(numberArray[0]), Long.parseLong(numberArray[1]));
                } else if (numberArray.length >= 3) {
                    for (int i = 2; i < numberArray.length; i++) {
                        properties.append(numberArray[i]);
                        if (i < numberArray.length - 1) properties.append(' ');
                    }
                    searchNumberProperties(Long.parseLong(numberArray[0]), Long.parseLong(numberArray[1]), properties.toString().split(" "));
                }
            }
            properties.setLength(0);
        }
        System.out.println("\nGoodbye!\n");
        scanner.close();
    }

    public static void printIntro() {
        System.out.println("Welcome to Amazing Numbers!\n");
        System.out.println("Supported requests: ");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println("* the first parameter represents a starting number;");
        System.out.println("* the second parameter shows how many consecutive numbers are to be processed;");
        System.out.println("- two natural numbers and two properties to search for;");
        System.out.println("- separate the parameters with one space;");
        System.out.println("- enter 0 to exit.\n");
    }

    public static void searchNumberProperties(long numberOne, long numberTwo, String[] properties) {
        int numberTwoCounter = 0;
        int sizeOfProperties = properties.length;
        String[] array = new String[(int) numberTwo];
        while (numberTwoCounter < numberTwo) {
            int propertyCounter = 0;
            while (propertyCounter < sizeOfProperties) {
                if ((properties[propertyCounter].toLowerCase().equals("even") && checkIfEven(numberOne)) ||
                        (properties[propertyCounter].toLowerCase().equals("odd") && !checkIfEven(numberOne)) ||
                        (properties[propertyCounter].toLowerCase().equals("buzz") && checkIfBuzz(numberOne)) ||
                        (properties[propertyCounter].toLowerCase().equals("duck") && checkIfDuck(numberOne)) ||
                        (properties[propertyCounter].toLowerCase().equals("palindromic") && checkIfPalindromic(numberOne)) ||
                        (properties[propertyCounter].toLowerCase().equals("gapful") && checkIfGapful(numberOne)) ||
                        (properties[propertyCounter].toLowerCase().equals("spy") && checkIfSpy(numberOne)) ||
                        (properties[propertyCounter].toLowerCase().equals("square") && checkIfSqaure(numberOne)) ||
                        (properties[propertyCounter].toLowerCase().equals("sunny") && checkIfSunny(numberOne)) ||
                        (properties[propertyCounter].toLowerCase().equals("jumping") && checkIfJumping(numberOne))) {
                    propertyCounter++;
                } else {
                    numberOne++;
                    propertyCounter = 0;
                }
            }
            array[numberTwoCounter] = Long.toString(numberOne);
            numberTwoCounter++;
            numberOne++;
        }
        printProperties(array);
    }

    public static void searchNumberProperties(long numberOne, long numberTwo) {
        System.out.println(" ");
        for (long i = numberOne; i != (numberOne + numberTwo); i++) {
            System.out.print(i + " is ");
            printProperties(i);
        }
        System.out.println(" ");
    }

    public static void searchNumberProperties(long number) {
        System.out.println("\nProperties of " + number);
        System.out.println("        buzz: " + checkIfBuzz(number));
        System.out.println("        duck: " + checkIfDuck(number));
        System.out.println(" palindromic: " + checkIfPalindromic(number));
        System.out.println("      gapful: " + checkIfGapful(number));
        System.out.println("         spy: " + checkIfSpy(number));
        System.out.println("      square: " + checkIfSqaure(number));
        System.out.println("       sunny: " + checkIfSunny(number));
        System.out.println("     jumping: " + checkIfJumping(number));
        System.out.println("        even: " + checkIfEven(number));
        System.out.println("         odd: " + checkIfEven(number + 1) + "\n");
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
        if (checkIfSqaure(number)) {
            System.out.print(PROPERTIES.SQUARE.toString().toLowerCase() + ", ");
        }
        if (checkIfSunny(number)) {
            System.out.print(PROPERTIES.SUNNY.toString().toLowerCase() + ", ");
        }
        if (checkIfJumping(number)) {
            System.out.print(PROPERTIES.JUMPING.toString().toLowerCase() + ", ");
        }
        if (checkIfEven(number)) {
            System.out.print(PROPERTIES.EVEN.toString().toLowerCase() + " \n");
        } else {
            System.out.print(PROPERTIES.ODD.toString().toLowerCase() + " \n");
        }
    }

    public static void printProperties(String[] array) {
        System.out.println(" ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " is ");
            printProperties(Long.parseLong(array[i]));
        }
        System.out.println(" ");
    }

    public static boolean checkNumbers(String[] array) {
        int count = 1;
        Long firstParam = parseLongSafe(array[0]);
        if (firstParam == null || firstParam < 0) {
            System.out.println("\nThe first parameter should be a natural number or zero.\n");
            return false;
        }
        if (array.length > 1) {
            Long secondParam = parseLongSafe(array[1]);
            if (secondParam == null || secondParam < 0) {
                System.out.println("\nThe second parameter should be a natural number.\n");
                return false;
            }
            if (array.length > 2) {
                String[] wrongProperties = checkProperties(array);
                if (!wrongProperties[0].equals("")) {
                    wrongProperties(wrongProperties);
                    return false;
                }
                for (int i = 0; i < PROPERTIES.values().length; i++) {
                    if (!array[2].toUpperCase().equals(PROPERTIES.values()[i].toString().toUpperCase()) && count == PROPERTIES.values().length) {
                        break;
                    } else if (array[2].toUpperCase().equals(PROPERTIES.values()[i].toString().toUpperCase())) {
                        break;
                    }
                    count++;
                }
                count = 1;
                if (array.length > 3) {
                    if (!checkMutuallyExclusiveConditions(array)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static void wrongProperties(String[] wrongProperties) {
        if (wrongProperties.length == 1) {
            System.out.println("\nThe property [" + wrongProperties[0] + "] is wrong");
            System.out.println("Available properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, EVEN, ODD]\n");
        } else {
            StringBuilder propertySentence = new StringBuilder();
            System.out.print("\nThe properties [");
            for (int i = 0; i < wrongProperties.length; i++) {
                propertySentence.append(wrongProperties[i] + ", ");
            }
            System.out.print(propertySentence.toString().substring(0, propertySentence.length() - 2));
            System.out.print("] are wrong\n");
            System.out.println("Available properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, EVEN, ODD]\n");
        }
    }

    private static void mutualExclusive(String propertyOne, String propertyTwo) {
        System.out.println("\nThe request contains mutually exclusive properties: [" + propertyOne + ", " + propertyTwo + "]");
        System.out.println("There are no numbers with these properties.\n");
    }

    public static boolean checkMutuallyExclusiveConditions(String[] array) {
        List<String> propertyList = Arrays.asList(array);
        propertyList.replaceAll(String::toUpperCase);
        if (propertyList.contains("ODD") && propertyList.contains("EVEN")) {
            mutualExclusive("ODD", "EVEN");
            return false;
        }
        if (propertyList.contains("DUCK") && propertyList.contains("SPY")) {
            mutualExclusive("DUCK", "DUCK");
            return false;
        }
        if (propertyList.contains("SUNNY") && propertyList.contains("SQUARE")) {
            mutualExclusive("SUNNY", "SQUARE");
            return false;
        }
        return true;
    }

    private static Long parseLongSafe(String s) {
        Long number;
        try {
            return number = Long.parseLong(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String[] checkProperties(String[] array) {
        StringBuilder wrongProperties = new StringBuilder();
        for (int i = 2; i < array.length; i++) {
            for (int j = 0; j < PROPERTIES.values().length; j++) {
                if (array[i].toUpperCase().equals(PROPERTIES.values()[j].toString().toUpperCase())) {
                    break;
                } else if (j == PROPERTIES.values().length - 1) {
                    wrongProperties.append(array[i].toUpperCase() + " ");
                }
            }
        }
        return wrongProperties.toString().trim().split(" ");
    }

    public static boolean checkIfEven(long number) {
        return number % 2 == 0;
    }

    public static boolean checkIfBuzz(long number) {
        return number % 10 == 7 || number % 7 == 0;
    }

    public static boolean checkIfDuck(long number) {
        while (number != 0) {
            if (number % 10 == 0) {
                return true;
            }
            number /= 10;
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
        if (number < 100) {
            return false;
        }
        long firstDigit = 0;
        long lastDigit = number % 10;
        long aux = number;
        while(aux > 0) {
            firstDigit = aux % 10;
            aux /= 10;
        }
        long div = firstDigit * 10 + lastDigit;
        return number % div == 0;
    }

    public static boolean checkIfSpy(long number) {
        long product = 1;
        long sum = 0;
        long lastDigit = 0;
        while (number > 0) {
            lastDigit = number % 10;
            sum += lastDigit;
            product *= lastDigit;
            number /= 10;
        }
        if (sum == product) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkIfSqaure(long number) {
        return (Math.sqrt((double) number) % 1 == 0);
    }

    public static boolean checkIfSunny(long number) {
        return checkIfSqaure(number + 1);
    }

    public static boolean checkIfJumping(long number) {
        String[] str = String.valueOf(number).split("");
        for (int i = 0; i < str.length - 1; i++) {
            if (Math.abs(Long.parseLong(str[i]) - Long.parseLong(str[i + 1])) != 1) {
                return false;
            }
        }
        return true;
    }

}