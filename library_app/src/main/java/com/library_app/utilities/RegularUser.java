package com.library_app.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.library_app.Main;

public class RegularUser extends User implements Borrowable{

    private static Connection conn;

// borrowed books here are the borrowed books of this specific user

    // constructor used in loading from database.
    public RegularUser (String user_id, String name, String email, String password){
        super(name, email, password);
        super.setId(user_id);
    }

    //constructor for creating a normal user with no id yet as id is created in the database.
    public RegularUser(String name, String email, String password) {
        super(name, email, password);
    }

    public List<Book> viewBookCatalog(){
        try{
            List<Book> books = this.getBorrowedBooks();
            return books;

        }catch (SQLException e){
            System.out.println("Error while fetching the updated borrowed books list from the database. Error in viewBookCatalog method.");
            return null;
        }

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
                this.setId(Integer.toString(user_id));
                
            }
        System.out.println("No user with the following data is present in the database.");
        throw new  SQLException();
    }

   
     public void  update_BorrowedBooks() throws SQLException{
         List<Book> borrowedBooks =new ArrayList<Book>();
         try{

             conn = Database.getConnection();
             String query = "SELECT * FROM `borrowed_books` AS Br JOIN  `books` AS B ON Br.book_id = B.book_id WHERE user_id = ?;";
             PreparedStatement st = conn.prepareStatement(query);
             st.setString(1, this.getId());
             ResultSet rs = st.executeQuery();
             while(rs.next()){
                int book_id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                int copies = rs.getInt("copies");
                Book book = new Book(Integer.toString(book_id), title, author, genre, copies);
                borrowedBooks.add(book);
             }
             super.setBorrowedBooks(borrowedBooks);

             System.out.println("Borrowed books list of User with user_id: "+ this.getId()+ " is Loaded successfully from the database.");



            } catch (SQLException e){
                System.out.println("Error while updating the borrowed book list in update_BorrowedBooks method in RegularUser.");
                throw e;

            }

     }

      @Override
      public List<Book> getBorrowedBooks() throws SQLException{
         this.update_BorrowedBooks();
         return super.getBorrowedBooks();
      }



    @Override
    public void borrowBook(String bookID) {
        if (bookID == null){
            System.out.println("Book passed is null");
            return;
        }
        try{
            List<Book> borrowedBooks = this.getBorrowedBooks();
            Book temp = new Book();
            temp.setId(bookID);

            SearchService<Book> book_SearchService = new SearchService<Book>(borrowedBooks);

            if (book_SearchService.search_by_id(bookID) != null){

                System.out.println("Book is already borrowed. Cannot borrow the same book twice.");
                return;
        
            }
       
            conn = Database.getConnection();
            String query = "SELECT * FROM `books` WHERE book_id = ?;";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, bookID);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                int copies = rs.getInt("copies");
                if(copies <= 0){
                    System.out.println("Book found in library but with 0 copies left to borrow. Try again later.");
                    return;
                }
                else{
                    // things to update: books table, borrowed books table, borrowed books list
                    copies -=1;
                    updateDatabase(bookID, copies);
                    this.update_BorrowedBooks();
                    // decrementing available copies of the book in books map. 
                    Main.books.get(bookID).setAvailableCopies(copies);
                    System.out.println("Book with id: "+ bookID+ " has been borrowed successfully by user id: "+ this.getId());
                    
                }
            }
            else{
                System.out.println("Book id passed cannot be found in the database.");
                return;
            }

        } catch (SQLException e){
            System.out.println("Error in borrowBook method in regular user.");
            e.printStackTrace();
       
        }

    }

    public void  updateDatabase(String  bookId, int copies) throws SQLException{
        try{
            conn = Database.getConnection();

          

            // updating the borrowed books table
            String query = "INSERT INTO `borrowed_books` (user_id, book_id) VALUES (?, ?)";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, this.getId());
            st.setString(2, bookId);
            st.executeUpdate();

              // updating the books table.
            query = "UPDATE `books` SET copies = ? WHERE book_id = ?;";
            st = conn.prepareStatement(query);
            st.setInt(1, copies);
            st.setString(2, bookId);
            st.executeUpdate();

            System.out.println("Database updated to apply borrowing of book_id: "+bookId+ " by user_id: "+ this.getId());

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
