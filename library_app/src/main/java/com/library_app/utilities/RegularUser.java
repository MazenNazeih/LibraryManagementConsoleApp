package com.library_app.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

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

    public void viewBookCatalog(){
        
            Collection<Book> books = Main.books.values();
            ArrayList<Book> book_array = new ArrayList<>(books);    

            Comparator<Book> com = new Comparator<Book>() { 
                public int compare(Book book1, Book book2){
                    String book1name = book1.getName();
                    String book2name = book2.getName();

                    if(book1name.compareTo(book2name) > 0)
                        return 1; // swap
                    else
                        return -1; // dont swap.+
                }
            };
            Collections.sort(book_array, com);
            System.out.println("\nView Borrowed Books Catalog for user_id: "+this.getId() + " :");
            for (Book book : book_array) {
                System.out.println("Book id:  "+book.getId());
                System.out.println("Book name: "+ book.getName());
                System.out.println("Book available copies: "+ book.getAvailableCopies());
                System.out.println("\n\n");
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
                
            }else{

                System.out.println("No user with the following data is present in the database.");
                throw new  SQLException();
            }
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
        if (bookID == null || bookID.isEmpty()){
            System.out.println("Book id passed is null or empty.");
            return;
        }
        try{
            List<Book> borrowedBooks = this.getBorrowedBooks();
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
                    updateDatabase_for_borrowBook(bookID, copies);
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

    public void  updateDatabase_for_borrowBook(String  bookId, int copies) throws SQLException{
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

    public void  updateDatabase_for_returnBook(String  bookId, int copies) throws SQLException{
        try{
            conn = Database.getConnection();

          

            // updating the borrowed books table
            String query = "DELETE from `borrowed_books` WHERE user_id = ? AND  book_id = ? ;";
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

            System.out.println("Database updated to apply returning of book_id: "+bookId+ " by user_id: "+ this.getId());

        } catch (SQLException e){
            System.out.println("Error in updating the database tables for returning a book.");
            throw e;
        }
    }

    
    
    @Override
    public void returnBook(String bookID) {
        
          if (bookID == null || bookID.isEmpty()){
            System.out.println("Book id passed is null or empty string.");
            return;
        }
        try{
            List<Book> borrowedBooks = this.getBorrowedBooks();
            SearchService<Book> book_SearchService = new SearchService<Book>(borrowedBooks);

            if (book_SearchService.search_by_id(bookID) == null){

                System.out.println("Book is not found between borrowed books list in user with user_id: "+ this.getId());
                return;
        
            }
       
            conn = Database.getConnection();
            String query = "SELECT * FROM `books` WHERE book_id = ?;";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, bookID);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                int copies = rs.getInt("copies");
                    // things to update: books table, borrowed books table, borrowed books list
                    copies +=1;
                    updateDatabase_for_returnBook(bookID, copies);
                    this.update_BorrowedBooks();
                    // incrementing available copies of the book in books map. 
                    Main.books.get(bookID).setAvailableCopies(copies);
                    System.out.println("Book with id: "+ bookID+ " has been returned successfully by user id: "+ this.getId());
                    
               
            }
            else{
                System.out.println("Book id passed cannot be found in the database.");
                return;
            }

        } catch (SQLException e){
            System.out.println("Error in returnBook method in regular user.");
            e.printStackTrace();
       
        }
    }



    @Override
    public boolean login() {
         try {
            this.updateId();
        } catch (Exception e) {

        System.out.println("Failed to login with the following credentials. Incorrect username, email, or password.");
        return false;
    }
    return true;

    }

    @Override
    public boolean sign_up() {
        List<User> users = new ArrayList<>();
        users.add(this);
        Admin admin = new Admin();
        boolean result = admin.registerUsers(users);
        return result;
    }

    @Override
    public void show_menu() {

        while(true){
        System.out.println();
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Menu:\n");
        System.out.println("View Book Catalog: 1");
        System.out.println("Borrow book: 2");
        System.out.println("Return Book: 3");
        System.out.println("Logout: 4");

        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        int input_num =10;
          try{
         input_num = Integer.parseInt(input) ;
        //  System.out.println("INPUT PARSED TO INTEGER"+ input_num);
         }catch (NumberFormatException e){
           System.out.println("Invalid option. choose again.");
            continue;
    }

        String book_id;

        switch(input_num) {
            case 1:
                this.viewBookCatalog();

                break;

            case 2:
                System.out.println("Please enter book id you want to borrow");
                book_id = scan.nextLine();
                this.borrowBook(book_id);

                break;

            case 3:
                System.out.println("Please enter book id you want to return.");
                book_id = scan.nextLine();
                this.returnBook(book_id);

                break;

            case 4:
                System.out.println("Logging out of the system ....");

                return;


            default:
                System.out.println("Invalid option. choose again.");
                continue;
        }
    }

    }

   




}
