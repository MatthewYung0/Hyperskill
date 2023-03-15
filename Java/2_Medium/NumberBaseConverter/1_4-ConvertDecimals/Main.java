package converter;

import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println("Enter the number in decimal system: ");
        long number = scanner.nextInt();
        System.out.println("Enter target base: ");
        int base = scanner.nextInt();
        checkConversion(number, base);
    }

    public static void checkConversion(long number, int base) {
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
