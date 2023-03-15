package converter;

import java.math.BigInteger;
import java.util.Scanner;

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

            if (value.equals("/back")) {
                exitBeginConversion = true;
            } else {
                System.out.print("Conversion result: ");
                BigInteger decimalValue = new BigInteger(value,source);
                System.out.println(decimalValue.toString(target) + '\n');
            }
        } while (!exitBeginConversion);
    }
}
