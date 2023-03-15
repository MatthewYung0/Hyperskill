package converter;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        boolean exitRun = false;
        do {
            System.out.print("\nEnter two numbers in format: {source base} {target base} (To quit type /exit) ");
            String text = scanner.nextLine();

            if (text.equals("/exit")) {
                exitRun = true;
            } else {
                String[] sourceAndBase = text.split("\\s+");
                beginConversion(Integer.parseInt(sourceAndBase[0]), Integer.parseInt(sourceAndBase[1]));
            }
        } while (!exitRun);
    }

    public static void beginConversion(int source, int target) {
        boolean exitBeginConversion = false;
        do {
            System.out.print("Enter number in base " + source + " to convert to base " + target + " (To go back type /back) ");
            String value = scanner.nextLine();
            String[] words = value.split(Pattern.quote("."));

            if (value.equals("/back")) {
                exitBeginConversion = true;
            } else {
                System.out.print("Conversion result: ");
                BigInteger decimalValue = new BigInteger(words[0], source);
                if (words.length > 1) {
                    String fractionalBIValue = convertFractionalPart(words[1], source, target);
                    System.out.println(decimalValue.toString(target) + "." + fractionalBIValue);
                } else {
                    System.out.println(decimalValue.toString(target));
                }
                System.out.print('\n');
            }

        } while (!exitBeginConversion);
    }

    private static String convertFractionalPart(String sourceFractional, int sourceBase, int targetBase) {
        double decimalValue = 0.0;
        if (sourceBase == 10) {
            decimalValue = Double.parseDouble("0." + sourceFractional);
        } else {
            char c;
            for (int i = 0; i < sourceFractional.length(); i++) {
                c = sourceFractional.charAt(i);
                decimalValue += Character.digit(c, sourceBase) / Math.pow(sourceBase, i + 1);
            }
        }
        StringBuilder result = new StringBuilder();
        int decimal;
        for (int i = 0; i < 5; i++) {
            double aux = decimalValue * targetBase;
            decimal = (int) aux;
            result.append(Long.toString(decimal, targetBase));
            decimalValue = aux - decimal;
        }
        return result.toString();
    }
}
