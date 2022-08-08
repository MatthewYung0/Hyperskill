package asciimirror;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Input the file path:");
        Scanner scanner = new Scanner(System.in);
        String file = scanner.nextLine();
        try {
            Path path = Paths.get(file);
            List<String> lines = Files.readAllLines(path);
            int maxStringLength = getLongestString(lines);
            String currentLine;
            for (int i = 0; i < lines.size(); i++) {
                currentLine = lines.get(i);
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

    public static int getLongestString(List<String> lines) {
        int stringLength = 0;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).length() > stringLength) {
                stringLength = lines.get(i).length();
            }
        }
        return stringLength;
    }

    public static void printWhiteSpaces(int noOfWhiteSpacesToPrint) {
        for (int i = 0; i < noOfWhiteSpacesToPrint; i++) {
            System.out.print(" ");
        }
    }
}
