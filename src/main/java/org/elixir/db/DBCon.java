package org.elixir.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCon {

    public static void main(String[] args) {
        Connection connection = getConnection();
    }

    private static String db_name = "term_frequencies";

    private static String url = "jdbc:mysql://localhost:3306/" + db_name + "?useSSL=false";

    private static String driverName = "com.mysql.jdbc.Driver";

    private static String username = "root";

    private static String password = "123456";

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                ex.printStackTrace();
                // log an exception. fro example:
                System.out.println("Failed to create the database connection.");
                System.out.println("Exiting ...");
                System.exit(1);
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            // log an exception. for example:
            System.out.println("Driver not found.");
            System.out.println("Exiting ...");
            System.exit(1);
        }
        return con;
    }
}