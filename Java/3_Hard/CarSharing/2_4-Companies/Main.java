package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        final String URL = "jdbc:h2:file:./src/carsharing/db/" + getFilename(args);
        CompanyDaoImpl companyDao = new CompanyDaoImpl();
        companyDao.createDatabase(URL);

        boolean isExit = false;

        do {
            printMainMenu();
            switch (Integer.parseInt(scanner.nextLine())) {
                case 0:
                    isExit = true;
                    break;
                case 1:
                    String loginInput;
                    do {
                        printLoginMenu();
                        loginInput = scanner.nextLine();
                        switch (Integer.parseInt(loginInput)) {
                            case 1:
                                printAllCompanies(companyDao.getAllCompanies(URL));
                                break;
                            case 2:
                                companyDao.createCompany(URL);
                                break;
                        }
                    } while (Integer.parseInt(loginInput) != 0);
            }
        } while (!isExit);
    }

    public static void printAllCompanies(List<Company> companyList) {
        if (companyList.isEmpty()) {
            System.out.println("\nThe company list is empty!");
        } else {
            System.out.println("\nCompany list:");
            companyList.forEach(company -> System.out.println(company.toString()));
        }
    }

    public static void printLoginMenu() {
        System.out.println("\n1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");
    }

    public static void printMainMenu() {
        System.out.println("\n1. Log in as a manager");
        System.out.println("0. Exit");
    }

    public static String getFilename(String[] filename) {
        return filename.length > 1 ? filename[1].trim() : nameGenerator();
    }

    public static String nameGenerator() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 7;
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }

}