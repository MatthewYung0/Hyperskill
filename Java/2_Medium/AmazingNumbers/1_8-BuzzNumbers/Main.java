package numbers;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Getting input from user
        System.out.println("Enter a natural number :");
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        int remainder = number % 10;
        int naturalNumber = (remainder * 2) - (number / 10);

        //Checking for not Buzz number conditions;
        if (number <= 0) {
            System.out.println("This number is not natural!");
            return;
        } else if (naturalNumber % 7 != 0 && remainder != 7) {
            isOddOrEven(number);
            System.out.println("It is not a Buzz number.");
            System.out.println("Explanation: ");
            System.out.println(number + " is neither divisible by 7 nor does it end with 7");
            return;
        }

        //All other conditions are Buzz numbers
        isOddOrEven(number);
        System.out.println("It is a Buzz number.");
        System.out.println("Explanation");
        if (naturalNumber % 7 == 0 && remainder == 7) {
            System.out.println(number + " is divisible by 7 and ends with 7.");
        } else if (naturalNumber % 7 == 0) {
            System.out.println(number + " is divisible by 7.");
        } else {
            System.out.println(number + " ends with 7");
        }
    }

    public static void isOddOrEven(int number) {
        if (number % 2 == 0) {
            System.out.println("This number is Even.");
        } else {
            System.out.println("This number is Odd");
        }
    }
}
