package sorting;

import java.util.*;

public class Main {
    static ArrayList<Long> numberList = new ArrayList<>();

    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLong()) {
            long number = scanner.nextLong();
            numberList.add(number);
        }

        System.out.println("Total numbers: " + getHowManyNumbers());
        System.out.println("The greatest number: " + getGreatestNumber() + "(" + getFrequencyOfGreatestNumber() + " time(s))");
    }

    public static int getHowManyNumbers() {
        return numberList.size();
    }

    public static long getGreatestNumber() {
        return Collections.max(numberList);
    }

    public static int getFrequencyOfGreatestNumber() {
        return Collections.frequency(numberList, getGreatestNumber());
    }
}
