package com.library_app.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String url = "jdbc:mysql://localhost:3306/library_app?useSSL=false&serverTimezone=UTC";
    private static final String username = "root";
    private static final String password = ""; 

    private static Connection conn;

     public static Connection getConnection() throws SQLException { 

        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(url, username, password);
        }
        return conn;
        
    }

    public static void closeConnection() throws SQLException {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
       
        
    }
    

    // public static Connection getConnection(){ 
    //     try {
    //         if (conn == null || conn.isClosed()) {
    //         conn = DriverManager.getConnection(url, username, password);
    //     }
    //     return conn;
            
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         System.out.println("Connection to database failed.");
    //         return null;
    //     }
        
    // }

    // public static boolean closeConnection() {

    //     try {
    //         if (conn != null && !conn.isClosed()) {
    //             conn.close();
    //         }
    //         return true;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         System.out.println("Failed during closing the database connection.");
    //         return false;
    //     }
        
    // }

}



