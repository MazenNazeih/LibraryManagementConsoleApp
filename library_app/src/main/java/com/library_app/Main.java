package com.library_app;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.library_app.utilities.*;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;


public class Main {

    public static   Map<String, Book> books =  new HashMap<>(); // book must be unique in id
    public static   Map<String, User> users = new HashMap<>(); // users must be unique in id
    public static   Map<String, Admin> admins = new HashMap<>(); // admins must be unique in id
    public static   Set<String>        genres = new TreeSet<String>();




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

        // Admin admin1 = new Admin("mazen5", "mazen5@gmail.com", "mazen123");
        // System.out.println("admin id = "+ admin1.getId() + " admin_name = "+ admin1.getName() + " admin_email = "+ admin1.getEmail());


        // Book book1 = new Book( "1984", "George Orwell", "Dystopian", 5);
        // Book book2 = new Book( "To Kill a Mockingbird", "Harper Lee", "Fiction", 3);
        // Book book3 = new Book( "Test new book in between", "test1", "Classic", 4);
        // Book book4 = new Book( "Pride and Prejudice", "Jane Austen", "Romance", 2);
        // // List<Book> bookList = new ArrayList<Book>();
        // bookList.add(book1);
        // bookList.add(book2);
        // bookList.add(book3);
        // bookList.add(book4);

        // admin1.add_new_Books(bookList);

        // for (Book book : bookList) {
        //     System.out.println("Book: "+ book.getId() + " and title: "+ book.getTitle());

        // }


        

        loadDatabase();
        print_all_Maps();

      
        // need admin to add a new admin.
        Admin admin1 = admins.get("1");

        // ..........................Testing add new admin ...........................
        //   Admin new_admin = new Admin("test8 new_admin", "test8_duplicate@gmail.com", "test1234");
        // try {
        //     admin1.add_new_Admin(new_admin);
        //     System.out.println("New admin added sucesfully.");
        //     System.out.println("admin details added: ");
            
        //     System.out.println("admin id = "+ new_admin.getId() + " admin_name = "+ new_admin.getName() + " admin_email = "+ new_admin.getEmail());
            
        // } catch (Exception e) {
        //      e.printStackTrace();
        //     System.out.println("Failed adding new admin.");
        // }

        //..........................Testing add new book ...........................

        // Book existing_book = books.get("1");
        // List<Book> new_books = new ArrayList<>();
        
        // Book newbook1 = new Book("Test new book7", "test author 7", "test genre 7", 5);
        // Book newbook2 = new Book("Test new book9", "test author9  duplicate", "test genre 9", 3);
        
        // new_books.add(newbook1);
        // new_books.add(existing_book);
        // new_books.add(newbook2);
        // admin1.add_new_Books(new_books);

        // System.out.println("book1 id: "+ newbook1.getId());
        // System.out.println("book2 id: "+ newbook2.getId());


        
        
        //..........................Testing edit old book ...........................
        
        // // testing duplicate change.
        // Book existing_book = books.get("27");
        // admin1.editBook(existing_book, "title", "Test new book-1");

        // // testing changing title
        // Book existing_book2 = books.get("Test new book 1");
        // admin1.editBook(existing_book2, "title", "Test new book-1");
        
        // //testing changing genre
        // Book existing_book3 = books.get("4");
        // admin1.editBook(existing_book3, "genre", "History");
        

        // // testing changing author
        // Book existing_book4 = books.get("2");
        // admin1.editBook(existing_book4, "author", "rick");

        // //testing changing copies 
        // Book existing_book5 = books.get("1");
        // admin1.editBook(existing_book5, 10);


      //..........................Testing delete old book ...........................

    //   Book existing_book = books.get("43");
    //   Book deleted_book = admin1.deleteBook(existing_book);
    //   if (deleted_book !=null){

    //       System.out.println("deleted book_id: "+ deleted_book.getId() + " book title: "+ deleted_book.getTitle());
    //     }else{
    //         System.out.println("book deleted is null.");
    //     }

    //..........................Testing registerUser ...........................

        // RegularUser new_user = new RegularUser("test_user7", "test_user7@gmail.com", "test1234");
        // RegularUser new_user2 = new RegularUser("test_user5", "test_user5@gmail.com", "test1234");
        // RegularUser new_user3 = new RegularUser("test_user8", "test_user8@gmail.com", "test1234");

        //  List<User> new_users = new ArrayList<User>();
        //  new_users.add(new_user);
        //  new_users.add(new_user2);
        //  new_users.add(new_user3);
        //     admin1.registerUsers(new_users);
            
        //     System.out.println("user1 id: "+ new_user.getId());
        //     System.out.println("user2 id: "+ new_user2.getId());
        //     System.out.println("user3 id: "+ new_user3.getId());


        // ..........................Testing deleteUser ...........................
        // User user_to_delete = users.get("4");
        // User deleted_user = admin1.deleteUser(user_to_delete);
        // if (deleted_user !=null){
        //     System.out.println("deleted user_id: "+ deleted_user.getId() + "user name: "+ deleted_user.getName() + " user email: "+ deleted_user.getEmail());
        // }else{
        //     System.out.println("user deleted is null.");
        // }


        // ..........................Testing borrowBook ...........................
        // RegularUser user1 = (RegularUser) users.get("3");
        // user1.borrowBook("28");
        // user1.borrowBook("5");
        // user1.borrowBook("5");

       
        
        // try{
        // List<Book> borrowed_books = user1.getBorrowedBooks();
        // System.out.println("printing all borrowed books of user from the list:");
        // for (Book book : borrowed_books) {
        //     System.out.println("borrowed book with Book_id: "+book.getId());
            
        // }
        // } catch(SQLException e){
        //     System.out.println("ERROR WHILE BORROWING A BOOK ");
        // }
       
        // ..........................Testing borrowBook ...........................
        // RegularUser user1 = (RegularUser) users.get("1");
        // user1.returnBook("40"); //to return fr
        // user1.returnBook("3"); // to return an unborrowed book
        // user1.returnBook("50"); // to return non existing book
        // user1.returnBook(""); // to return a null book id

          // ..........................Testing borrowBook ...........................
        // RegularUser user1 = (RegularUser) users.get("1");
        // user1.viewBookCatalog();

        // user1.returnBook("5");
        // user1.returnBook("6");

        //  user1.viewBookCatalog();


        // ..........................Testing Searching service ...........................
        // SearchService<Book> bookSearchService = new SearchService<Book>(books.values().stream().toList());
        // Book book_by_name = bookSearchService.search_by_name("programming");
        // if (book_by_name != null){
        //     System.out.println("Book found by name: "+ book_by_name.getTitle() + " with id: "+ book_by_name.getId());
        // }else{
        //     System.out.println("Book not found by name.");
        // }
        // Book book_by_id = bookSearchService.search_by_id("40");
        // if (book_by_id != null){
        //     System.out.println("Book found by id: "+ book_by_id.getTitle() + " with id: "+ book_by_id.getId());
        // }else{
        //     System.out.println("Book not found by id.");
        // }

        // SearchService<User> userSearchService = new SearchService<User>(users.values().stream().toList());
        // User user_by_name = userSearchService.search_by_name("test_user7");
        // if (user_by_name != null){
        //     System.out.println("User found by name: "+ user_by_name.getName() + " with id: "+ user_by_name.getId());
        // }else{
        //     System.out.println("User not found by name.");
        // }
        // User user_by_id = userSearchService.search_by_id("3");
        // if (user_by_id != null){
        //     System.out.println("User found by id: "+ user_by_id.getName() + " with id: "+ user_by_id.getId());
        // }else{
        //     System.out.println("User not found by id.");
        // }

        // SearchService<Admin> adminSearchService = new SearchService<Admin>(admins.values().stream().toList());
        // Admin admin_by_name = adminSearchService.search_by_name("karim");
        // if (admin_by_name != null){
        //     System.out.println("Admin found by name: "+ admin_by_name.getName() + " with id: "+ admin_by_name.getId());
        // }else{
        //     System.out.println("Admin not found by name."); 
        // }
        // Admin admin_by_id = adminSearchService.search_by_id("12");
        // if (admin_by_id != null){
        //     System.out.println("Admin found by id: "+ admin_by_id.getName() + " with id: "+ admin_by_id.getId());
        // }else{
        //     System.out.println("Admin not found by id.");
        // }

        System.out.println("");
        // print_all_Maps();
        // print_all_genres();


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




    public static void loadDatabase(){
        try {
            Connection conn = Database.getConnection();
            System.out.println("Loading all admins from database........");
            String query = "SELECT * FROM `admins`;";
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int admin_id = rs.getInt("admin_id");
                String admin_name = rs.getString("admin_name");
                String admin_email = rs.getString("admin_email");
                String admin_password= rs.getString("admin_password");
                Admin admin = new Admin(Integer.toString(admin_id), admin_name, admin_email, admin_password);
                admins.put(Integer.toString(admin_id), admin);

            }
            System.out.println("All admins are loaded to the system successfully.");


            System.out.println("Loading all regular users from the database........");
            query = "SELECT * FROM `users`;";
            st = conn.prepareStatement(query);
            rs = st.executeQuery();
            while(rs.next()){
                int user_id = rs.getInt("user_id");
                String user_name = rs.getString("user_name");
                String user_email = rs.getString("user_email");
                String user_password= rs.getString("user_password");
                RegularUser user = new RegularUser(Integer.toString(user_id),user_name, user_email, user_password);
                users.put(Integer.toString(user_id), user);

            }
            System.out.println("All regular users are loaded to the system successfully.");

            System.out.println("Loading all books from the database........");
            query = "SELECT * FROM `books`;";
            st = conn.prepareStatement(query);
            rs = st.executeQuery();
            while(rs.next()){
                int book_id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre= rs.getString("genre");
                int copies = rs.getInt("copies");
                Book book = new Book(Integer.toString(book_id), title, author, genre, copies);
                books.put(Integer.toString(book_id), book);

            }
            System.out.println("All books are loaded to the system successfully.");

            System.out.println("Loading all genres from the database........");
            query = "SELECT DISTINCT genre FROM `books`;";
            st = conn.prepareStatement(query);
            rs = st.executeQuery();
            while(rs.next()){
                String temp_genre = rs.getString("genre");
                genres.add(temp_genre);

            }
            System.out.println("All genres are loaded to the system successfully.");



            // System.out.println("Loading all borrowed books from the database........");
            // query = "SELECT * FROM `borrowed_books`;";
            // st = conn.prepareStatement(query);
            // rs = st.executeQuery();
            // while(rs.next()){
            //     int user_id = rs.getInt("user_id");
            //     int book_id = rs.getInt("book_id");
            //     User user = users.get(user_id);
            //     Book book = books.get(book_id);
            //     if(user != null && book != null){
            //         user.getBorrowedBooks().add(book);
            //     }
            // }
            // System.out.println("All borrowed books are loaded to the system successfully.");

        } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Connection to database failed while loading data from database.\n");
        }

    }

    public static void  print_all_Maps(){
        System.out.println("\nPrinting all admins:");
        for (Admin admin : admins.values()) {
            System.out.println(admin.getName());
            
        }
        System.out.println("\n\nPrinting all users:");
         for (User user : users.values()) {
            System.out.println(user.getName());
            
        }
        System.out.println("\n\nPrinting all books:");
         for (Book book : books.values()) {
            System.out.println(book.getTitle());
            
        }
        System.out.println("\n");

    }

    public static void print_all_genres(){
        System.out.println("\n\nPrinting all books:");
        for (String genre : genres) {

            System.out.println(genre);
        }
        System.out.println("\n");
    }

// THINGS TO DO:
// 1- MAKE ID STRING AND MAKE IT THE KEY FOR EVERY MAP 
// 2- 

}