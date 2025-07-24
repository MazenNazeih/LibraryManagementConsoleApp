package com.library_app.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private int availableCopies;
    private Connection conn;

    
    // public Book(String title, String author, String genre, int availableCopies) {
    //     this.title = title;
    //     this.author = author;
    //     this.genre = genre;
    //     this.availableCopies = availableCopies;
    // }

    public Book(int id, String title, String author, String genre, int availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availableCopies = availableCopies;
    }


    protected int updateId()  throws SQLException {

            conn = Database.getConnection();
            String query = "SELECT * FROM books WHERE title = ? AND author = ? AND genre = ?;";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, this.title);
            st.setString(2, this.author);
            st.setString(3, this.genre);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                int book_id = rs.getInt("book_id");
                this.id = book_id;
                return book_id;
            }
        System.out.println("No book with the following data is present in the database.");
        throw new  SQLException();
    }

    public int getId() {
        return this.id;
    }

    protected void setId(int id){
        this.id  = id;
    }

    public String getTitle() {
        return title;
    }

  
    public void setTitle(String title) {
        this.title = title;
    }

  
    public String getAuthor() {
        return author;
    }

 
    public void setAuthor(String author) {
        this.author = author;
    }

   
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

}
