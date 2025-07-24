package com.library_app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.library_app.utilities.*;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;


public class Main {
    public static void main(String[] args) {
    //     Connection conn;
    //    try {
    //     conn = Database.getConnection();
    //     PreparedStatement stmt = conn.prepareStatement("SELECT * FROM `books` WHERE 1;");
    //     ResultSet rs = stmt.executeQuery();
    //    } catch (SQLException e) {
    //         e.printStackTrace();
    //         System.out.println("Connection to database failed in Main");
    //     }
    //     System.out.println("Connected to database successfully");

        Admin admin1 = new Admin("mazen5", "mazen5@gmail.com", "mazen123");
        System.out.println("admin id = "+ admin1.getId() + " admin_name = "+ admin1.getName() + " admin_email = "+ admin1.getEmail());


        Book book1 = new Book( "1984", "George Orwell", "Dystopian", 5);
        Book book2 = new Book( "To Kill a Mockingbird", "Harper Lee", "Fiction", 3);
        Book book3 = new Book( "Test new book in between", "test1", "Classic", 4);
        Book book4 = new Book( "Pride and Prejudice", "Jane Austen", "Romance", 2);
        // List<Book> bookList = new ArrayList<Book>();
        // bookList.add(book1);
        // bookList.add(book2);
        // bookList.add(book3);
        // bookList.add(book4);

        // admin1.add_new_Books(bookList);

        // for (Book book : bookList) {
        //     System.out.println("Book: "+ book.getId() + " and title: "+ book.getTitle());

        // }

        admin1.editBook(null, 0);



        System.out.println("Hello world!");




        // ------------------closing all connection and threads --------------------------
        try {
            Database.closeConnection();
            System.out.println("Database Connection closed succesfully.");
            AbandonedConnectionCleanupThread.checkedShutdown();
        } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error closing the database connection in Main");
        }
       
        
    }
}