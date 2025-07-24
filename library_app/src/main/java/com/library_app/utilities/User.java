package com.library_app.utilities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
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

    
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    protected void setId(int id){
        this.id = id;
    }
    
    public int getId() {
        
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



    
}
