package com.library_app.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;


public class Admin extends User{

    private static Connection conn;

// public Connection getConnection() {
//         return Databas
//     }


    // borrowed books here are the total borrowed books of all users.
    public Admin(String name, String email, String password){

        super(name, email, password);

        try {
            
            int admin_id = search_admin(name, email, password);
            if (admin_id != -1){ //admin found in db
                super.setId(admin_id);
            }
            else{
                int id =  insert_NewAdmin(name, email, password);
                System.out.println("Created Admin: "+name+" successfully.");
                super.setId(id);
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Connection to database failed in Admin constructor method.\n");
        }

         

    }

    
    public int search_admin(String name,  String email, String password) throws SQLException {

        try{

            conn = Database.getConnection();
            String query = "Select * FROM admins WHERE admin_name = ? AND admin_email = ? AND admin_password = ?;";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, name);
            st.setString(2, email);
            st.setString(3, password);
            
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                int admin_id = rs.getInt("admin_id");
                System.out.println("Admin with the provided credentials is found in the database.");
                return admin_id;
                
            }
            else{
                return -1;
            }
        }catch (SQLException e){
            System.out.println("Connection to database failed in search_admin method in Admin\n");
            throw e;

        }
    }

    public int getLastAdminId() throws SQLException{

            try {
                
                conn = Database.getConnection();
                String query = "SELECT * FROM admins ORDER BY admin_id DESC LIMIT 1;" ;
                PreparedStatement st = conn.prepareStatement(query);
                ResultSet rs = st.executeQuery();
                rs.next(); // move cursor down one row to the firts row. 
                int admin_id = rs.getInt("admin_id");
                return admin_id;
                
            } catch (SQLException e) {
               System.out.println("Connection to database failed in getLastAdminId method in Admin.\n");
               throw e;
            }
    }

    public int insert_NewAdmin(String name, String email, String password) throws SQLException{
       
        try {
            
            conn = Database.getConnection();
            String query = "INSERT INTO admins(admin_name, admin_email, admin_password) VALUES (?,?,?);";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, name);
            st.setString(2, email);
            st.setString(3, password);
            st.executeUpdate();
            System.out.println("Admin: "+name + "is succesfully added in the database.");

        } catch( SQLException e){
            System.out.println("Connection to Database failed in insert_NewAdmin method in Admin.\n");
            throw e;
        }
            
        try {
            int id = getLastAdminId();
            return id;
        
        } catch (SQLException e) {
           throw e;
        }

    }


    public void add_new_Books(List<Book> books){
        try{
            conn = Database.getConnection();
            for (Book book : books) {
                String title = book.getTitle();
                String author =  book.getAuthor();
                String genre = book.getGenre();
                int copies = book.getAvailableCopies();

                String query = "INSERT INTO books (title, author, genre, copies) VALUES (?,?,?,?);";
                conn.setAutoCommit(false);
                try { // purpose of this try catch is to allow insertion of other books even if error occured with one of the books.
                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1,title);
                st.setString(2,author);
                st.setString(3,genre);
                st.setInt(4, copies);
                st.executeUpdate(); // can throw error
                int id =  book.updateId(); // can throw error
                    book.setId(id);
                    System.out.println("Book added with title: "+title +" and Author: "+author +" Genre: "+ genre + " copies: "+copies);
              
              

                } catch (Exception e){
                    System.out.println("Error while adding book: "+ title + " to the database. "+ e);
                    conn.rollback();
                }

               
                conn.commit();
               

                
            }
            conn.setAutoCommit(true);
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Connection to database failed in Admin addbooks method.\n");
        }
      
        
    }

    public void editBook(Book book, String type, String data){
        try {
            
            conn = Database.getConnection();
            int book_id = book.getId();
            String query;
            PreparedStatement st;
            type.toUpperCase();
            switch(type){
                case "TITLE":
                    query = "UPDATE books SET  title = ? WHERE book_id =  ?;";
                    st = conn.prepareStatement(query);
                    st.setString(1, data);
                    st.setInt(2, book_id);
                    st.executeUpdate();
                    break;

                case "AUTHOR":
                    query = "UPDATE books SET  author = ? WHERE book_id =  ?;";
                    st = conn.prepareStatement(query);
                    st.setString(1, data);
                    st.setInt(2, book_id);
                    st.executeUpdate();
                    break; 

                case "GENRE":
                    query = "UPDATE books SET  genre = ? WHERE book_id =  ?;";
                    st = conn.prepareStatement(query);
                    st.setString(1, data);
                    st.setInt(2, book_id);
                    st.executeUpdate();
                    break;


                
            } 
            
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection to database failed in editBook method in Admin.\n");
            }
                

        }

    public void editBook(Book book, int copies){
        try {
            conn = Database.getConnection();
            int book_id = book.getId();
            String query = "UPDATE books SET copies = ? WHERE book_id = ?;";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, copies);
            st.setInt(2, book_id);
            st.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection to database failed in editBook method in Admin.\n");
        }

    }
  


    public Book deleteBook(Book book){


        return book;
    }

    public void registerUsers(List<User> users){

        
    }


}