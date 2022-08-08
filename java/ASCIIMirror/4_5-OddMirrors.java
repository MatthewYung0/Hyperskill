package asciimirror;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Input the file path:");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        File file = new File(path);

        try {
            scanner = new Scanner(file);
            int maxStringLength = getLongestString(file);

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                if (currentLine.length() == maxStringLength) {
                    System.out.println(currentLine + " | " + currentLine);
                } else {
                    int whitespacesToBePrinted = maxStringLength - currentLine.length();
                    System.out.print(currentLine);
                    printWhiteSpaces(whitespacesToBePrinted);
                    System.out.print(" | ");
                    System.out.print(currentLine);
                    printWhiteSpaces(whitespacesToBePrinted);
                    System.out.print('\n');
                }
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }

    public static int getLongestString(File file) {
        int stringLength = 0;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().length() > stringLength) {
                    stringLength = scanner.nextLine().length();
                }
            }
        } catch (FileNotFoundException fileNotFound) {
            System.out.println("Could not get longest string");
        }
        return stringLength;
    }

    public static void printWhiteSpaces(int noOfWhiteSpacesToPrint) {
        for (int i = 0; i < noOfWhiteSpacesToPrint; i++) {
            System.out.print(" ");
        }
    }
}
