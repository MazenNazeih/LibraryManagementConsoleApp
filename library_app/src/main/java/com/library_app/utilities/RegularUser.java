package com.library_app.utilities;



public class RegularUser extends User implements Borrowable{
    

// borrowed books here are the borrowed books of this specific user
    public RegularUser (int user_id, String name, String email, String password){
        super(name, email, password);
        super.setId(user_id);
    }

    // public Book viewBookCatalog(){

    // }



    public void borrowBook(String bookId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'borrowedBook'");
    }

    public void returnBook(String bookId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'returnBook'");
    }




}
