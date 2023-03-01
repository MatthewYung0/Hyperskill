package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Main {
    static final String JDBC_DRIVER = "org.h2.Driver";

    public static void main(String[] args) {
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            final String URL = "jdbc:h2:file:./src/carsharing/db/" + getFilename(args);
            Connection conn = DriverManager.getConnection(URL);
            conn.setAutoCommit(true);

            //STEP 3: Execute a query
            Statement stmt = conn.createStatement();
            String sql =  "DROP TABLE IF EXISTS COMPANY";
            stmt.executeUpdate(sql);

            sql =  "CREATE TABLE IF NOT EXISTS COMPANY\n" +
                    "(ID INT,\n" +
                    "name VARCHAR(255));\n";
            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");

            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFilename(String[] filename) {
        return filename.length > 1 ? filename[1].trim() : nameGenerator();
    }

    public static String nameGenerator() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 7;
        for(int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }

}
