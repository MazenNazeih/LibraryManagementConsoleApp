package com.library_app.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RegularUser extends User implements Borrowable{

    private static Connection conn;

// borrowed books here are the borrowed books of this specific user

    // constructor used in loading from database.
    public RegularUser (int user_id, String name, String email, String password){
        super(name, email, password);
        super.setId(user_id);
    }

    //constructor for creating a normal user with no id yet as id is created in the database.
    public RegularUser(String name, String email, String password) {
        super(name, email, password);
    }

    public List<Book> viewBookCatalog(){
        return  super.getBorrowedBooks();

    }

    public void updateId() throws SQLException {

            conn = Database.getConnection();
            String query = "SELECT * FROM  `users` WHERE user_name = ? AND user_email = ? AND user_password = ?;";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, this.getName());
            st.setString(2, this.getEmail());
            st.setString(3, this.getPassword());
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                int user_id = rs.getInt("user_id");
                this.setId(user_id);
                
            }
        System.out.println("No user with the following data is present in the database.");
        throw new  SQLException();
    }

    @Override
    public void borrowBook(Book book) {

        List<Book> borrowedBooks = super.getBorrowedBooks();
        if (borrowedBooks.contains(book)){
            System.out.println("Book is already borrowed. Cannot borrow the same book twice.");
            return;
        }
        try{
            conn = Database.getConnection();
            String query = "SELECT * FROM `books` WHERE book_id = ?;";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, book.getId());
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                int copies = rs.getInt("copies");
                if(copies <= 0){
                    System.out.println("Book found in library but with 0 copies left to borrow. Try again later.");
                    return;
                }
                else{
                    // things to update: copies in book object, books table, borrowed books table, borrowed books list
                    copies -=1;
                    book.setAvailableCopies(copies);
                    updateDatabase(book);
                    borrowedBooks.add(book);
                    System.out.println("Book with title: "+ book.getTitle() + " and id: "+ book.getId() + " has been borrowed successfully.");
                    
                }
            }
            else{
                System.out.println("Book passed cannot be found in the database.");
                return;
            }

        } catch (SQLException e){
            System.out.println("Error in borrowBook method in regular user.");
            e.printStackTrace();
       
        }

    }

    public void  updateDatabase(Book book) throws SQLException{
        try{
            conn = Database.getConnection();

            // updating the books table.
            String query = "UPDATE `books` SET copies = ? WHERE book_id = ?;";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, book.getAvailableCopies());
            st.setInt(2, book.getId());
            st.executeUpdate();

            // updating the borrowed books table
            query = "INSERT INTO `borrowed_books` (user_id, book_id) VALUES (?, ?)";
            st = conn.prepareStatement(query);
            st.setInt(1, this.getId());
            st.setInt(2, book.getId());
            st.executeUpdate();



        } catch (SQLException e){
            System.out.println("Error in updating the database tables for borrowing a book.");
            throw e;
        }
    }

    public void returnBook(String bookId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'returnBook'");
    }

   




}
