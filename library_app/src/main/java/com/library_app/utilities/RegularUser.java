package com.library_app.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegularUser extends User implements Borrowable{
    
    private static Connection conn;

// borrowed books here are the borrowed books of this specific user
    public RegularUser (int user_id, String name, String email, String password){
        super(name, email, password);
        super.setId(user_id);
    }

    // public Book viewBookCatalog(){

    // }

    @Override
    public int updateId() throws SQLException {

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
                return user_id;
            }
        System.out.println("No user with the following data is present in the database.");
        throw new  SQLException();
    }


    public void borrowBook(String bookId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'borrowedBook'");
    }

    public void returnBook(String bookId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'returnBook'");
    }




}
