package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CompanyDaoImpl implements CompanyDao {

    static final String JDBC_DRIVER = "org.h2.Driver";


    public void createDatabase(String URL) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(URL);
            conn.setAutoCommit(true);
            Statement stmt = conn.createStatement();
            conn.createStatement().executeUpdate("""
                    CREATE TABLE IF NOT EXISTS COMPANY
                    (ID INT PRIMARY KEY AUTO_INCREMENT,
                    NAME VARCHAR(255) UNIQUE NOT NULL);
                    """);
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Company> getAllCompanies(String URL) {
        List<Company> companies = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(true);
            ResultSet rs = statement.executeQuery("SELECT * FROM COMPANY ORDER BY ID");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                companies.add(new Company(id, name));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return companies;
    }

    @Override
    public void createCompany(String URL) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter the company name:");
        String name = scanner.nextLine();
        addToDatabase(name, URL);
    }

    public void addToDatabase(String name, String URL) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(URL);
            conn.setAutoCommit(true);
            Statement stmt = conn.createStatement();
            conn.createStatement().executeUpdate("INSERT INTO COMPANY\n" +
                    "VALUES(NULL, " + "'" + name + "'" + ");");
            stmt.close();
            conn.close();
            System.out.println("The company was created!");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
