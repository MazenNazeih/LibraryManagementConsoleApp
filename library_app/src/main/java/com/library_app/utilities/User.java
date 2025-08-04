package com.library_app.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class User implements Searchable {
    private String id;
    private String name;
    private String email;
    private String password;

    private List<Book> borrowedBooks;

    public User(String name, String email, String password){
        this.name = name;
        this.password = password;
        this.email = email;
        this.borrowedBooks = new ArrayList<Book>();
        
    }
    public User(){}

    
   public List<Book> getBorrowedBooks() throws SQLException {
        return borrowedBooks;
    }
    
    public void setBorrowedBooks(List<Book> books) {
        this.borrowedBooks = books;

    }

    protected void setId(String id){
        this.id = id;
    }
    
    public String getId() {
        
        return id; 
    }



    public String getName() {
        return name;
    }


    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

     public  void  updateId() throws SQLException{

     }

     public abstract boolean sign_up();
 
     public abstract  boolean login();

     public abstract void show_menu();


    
}
