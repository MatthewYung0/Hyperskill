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
                    System.out.print(currentLine);
                } else {
                    int noOfWhiteSpaces = maxStringLength - currentLine.length();
                    lines.set(i, changeString(noOfWhiteSpaces, currentLine));
                    System.out.print(lines.get(i));
                }
                System.out.print(" | ");
                for (int j = lines.get(i).length() - 1; j >= 0; j--) {
                    String character = Character.toString(lines.get(i).charAt(j));
                    printCharacter(character);
                }
                System.out.print('\n');
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

    public static String changeString(int noOfWhiteSpaces, String currentString) {
        StringBuilder newString = new StringBuilder(currentString);
        for (int i = 0; i < noOfWhiteSpaces; i++) {
            newString.append(" ");
        }
        return newString.toString();
    }

    public static void printCharacter(String character) {
        switch (character) {
            case "<":
                System.out.print(">");
                break;
            case ">":
                System.out.print("<");
                break;
            case "[":
                System.out.print("]");
                break;
            case "]":
                System.out.print("[");
                break;
            case "{":
                System.out.print("}");
                break;
            case "}":
                System.out.print("{");
                break;
            case "(":
                System.out.print(")");
                break;
            case ")":
                System.out.print("(");
                break;
            case "\\":
                System.out.print("/");
                break;
            case "/":
                System.out.print("\\");
                break;
            default:
                System.out.print(character);
                break;
        }
    }
}
