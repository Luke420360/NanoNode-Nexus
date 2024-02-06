package com.example.nanonodenexus;
import java.sql.*;

public class DBConnection {
    public static Connection connect() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:Test.db");
            System.out.println("Database Connected!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.printf("Error Connecting to Database " + e);
        }
        return con;
    }
    public static ResultSet executeQuery(String sql, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
            return null;
        }
    }
}
