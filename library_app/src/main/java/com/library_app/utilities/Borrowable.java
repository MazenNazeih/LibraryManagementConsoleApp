package com.library_app.utilities;

public interface Borrowable {

    
    void borrowBook(Book book);
    void returnBook(String bookId);
}
