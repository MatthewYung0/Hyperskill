package asciimirror;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Input the file path:");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        File file = new File(path);
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }
}
