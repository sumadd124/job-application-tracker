package com.tracker.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "job_tracker";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Default empty password

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // First connect to MySQL without DB to ensure DB exists
            Connection initialConn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = initialConn.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            initialConn.close();

            // Now connect to the specific DB
            connection = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            
            // Initialize table if not exists
            initializeTable();
        }
        return connection;
    }

    private static void initializeTable() {
        String sql = "CREATE TABLE IF NOT EXISTS applications (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "company VARCHAR(255) NOT NULL, " +
                     "role VARCHAR(255) NOT NULL, " +
                     "date_applied DATE, " +
                     "status ENUM('Applied', 'Screening', 'Interview', 'Offer', 'Rejected') DEFAULT 'Applied', " +
                     "deadline DATE, " +
                     "notes TEXT)";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error initializing table: " + e.getMessage());
        }
    }
}
