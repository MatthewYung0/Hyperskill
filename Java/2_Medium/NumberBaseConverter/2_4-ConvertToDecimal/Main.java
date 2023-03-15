package converter;

import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        String text = "";
        do {
            System.out.print("\nDo you want to convert /from decimal or /to decimal? (To quit type /exit) ");
            text = scanner.nextLine();
            checkConversion(text);
        } while (!text.equals("/exit"));
    }

    public static void checkConversion(String text) {
        if (text.equals("/from")) {
            System.out.print("Enter the number in decimal system: ");
            long number = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter target base: ");
            int base = Integer.parseInt(scanner.nextLine());
            convertFromNumber(number,base);
        } else if (text.equals("/to")) {
            System.out.print("Enter source number: ");
            String sourceNumber = scanner.nextLine();
            System.out.print("Enter source base: ");
            int sourceBase = Integer.parseInt(scanner.nextLine());
            convertToNumber(sourceNumber,sourceBase);
        }
    }

    public static void convertToNumber(String sourceNumber, int sourceBase) {
        System.out.print("Conversion to decimal result: ");
        switch (sourceBase) {
            case 2:
                System.out.println(Long.parseLong(sourceNumber,2));
                break;
            case 8:
                System.out.println(Long.parseLong(sourceNumber,8));
                break;
            case 16:
                System.out.println(Long.parseLong(sourceNumber,16));
                break;
        }
    }

    public static void convertFromNumber(long number, int base) {
        System.out.print("Conversion result: ");
        switch (base) {
            case 2:
                System.out.println(Long.toBinaryString(number));
                break;
            case 8:
                System.out.println(Long.toOctalString(number));
                break;
            case 16:
                System.out.println(Long.toHexString(number));
                break;
        }
    }

}
