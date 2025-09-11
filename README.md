# 📚 Library Management Console Application

This project is a **Java-based console application** that simulates a simple **Library Management System**.  
It demonstrates **Object-Oriented Programming (OOP)** concepts, **Database integration (MySQL)**, and **modular design** that was assigned by the Ejada Java Team internship. 

The system allows:
- **Admins** to manage books and users (CRUD operations).  
- **Regular Users** to search, borrow, and return books.  
- **Both roles** to authenticate via **signup** or **login**.  

---

## 📂 File Structure

src/main/java/com/library_app \
│── Main.java \
│ \
└── utilities  
&ensp;   │── Admin.java  
 &ensp;   │── Book.java  
 &ensp;   │── Borrowable.java  
  &ensp;  │── Database.java  
  &ensp;  │── RegularUser.java   
  &ensp;  │── SearchService.java  
  &ensp;  │── Searchable.java  
 &ensp;  │── User.java  

 
---

## 📝 File Responsibilities & Key Methods

### **Admin.java**
**Responsibility:**
The Admin class represents a system administrator user in the Library Management Console App. It extends the abstract/general `User` class and provides functionality for managing books and users within the library database.

**Admins can:**
- Add, edit, and delete books.
- Register and delete users.
- Add new admins.
- Login and sign up (with validation against the database).
- Interact with a console-based admin menu to perform these actions.
  
**Key Methods:**
  - `add_new_Books(List<Book> books): boolean` → bulk insert books into DB.
  - `editBook(Book, String type, String data): boolean` → update a book’s title/author/genre.
  - `editBook(Book, int copies): boolean` → update available copies.
  - `deleteBook(Book):Book` → remove `Book` from DB.
  - `registerUsers(List<User>)` → bulk add `regular users` into DB.
  - `deleteUser(User): User` → remove `User` from DB.
  - `add_new_Admin(Admin admin): boolean`  → inserts a new admin into DB.
  - `sign_up(): boolean` → calls add_new_Admin().
  - `login(): boolean` → verifies admin credentials via `updateId()`.
  - `show_menu(): void` → console-driven admin dashboard for performing tasks interactively.

---

### **Book.java**
**Responsibility:**
Represents a book entity in the library management system. Each `Book` holds metadata (title, author, genre) and availability information, while also being able to fetch and update its unique ID from the database. The class implements the Searchable interface, which ties into search functionality in the system.

***Key Methods:*** \
**Constructors**
- `Book(String id, String title, String author, String genre, int availableCopies)` → for loading book directly from database (ID already exists).
- `Book(String title, String author, String genre, int availableCopies)` → for creating new books before persisting to DB (ID assigned later).

**Database ID synchronization**
- `protected void updateId()` → queries the DB to find the book’s ID by matching title, author, and genre. Updates the object’s id if found.

**Getters & Setters**
- Encapsulated access for id, title, author, genre, and availableCopies.
- `getName()` → convenience alias that returns the book’s title (useful for Searchable interface).


---

### **Borrowable.java**
- Interface defining borrowing behavior.  
- **Methods (no return value):**
  - `borrowBook(String bookId)`  
  - `returnBook(String bookId)`  

---

### **Database.java**
- Handles **MySQL database connection**.
- **Key Methods:**
  - `getConnection(): Connection` → Returns an existing DB connection or establishes a new one if doesn't exist.
  - `closeConnection(): void` → Closes DB connection.  

---

### **Searchable.java**
- Interface implemented by `Book` and `User`.  
- Defines:
  - `getName(): String` → Returns entity name (Book → title) or (User → name).
  - `getId(): String` → Returns entity unique id stored in DB.
    
---

### **SearchService.java**
- `SearchService<T extends Searchable>`
- Works with any list of items (Users, Books, etc.) that implement `Searchable`.
- Provides two lookup methods:
  - `search_by_name(String name)`
  - `search_by_id(String id)`
- Provided nice separation of concerns: instead of embedding search logic inside User or Book, you centralized it.
  
---

### **User.java**
- Abstract base class for both `Admin` and `RegularUser`.
- Implements `Searchable` (so all users can be found by id or name).
- Defines authentication and common properties.  
- **Key Methods:**
  - `sign_up(): boolean` → Registers new user in DB.  
  - `login(): boolean` → Validates user credentials.  
  - `show_menu(): void` → Displays role-specific menu.  

---

### **RegularUser.java**
- Extends `User` and implements `Borrowable`.  
- Allows regular users to borrow/return books.  
- **Key Methods:**

  - `viewBookCatalog(): void` → Lists all books from Main.books, sorted by name.
  - `update_BorrowedBooks(): void` → Loads borrowed books from DB (borrowed_books join books).
  - `getBorrowedBooks(): List<Book>` → Always refreshes the BorrowedBooks list from DB before returning.
  - `borrowBook(String bookID): void`:
    - Checks if already borrowed
    - Updates DB (`borrowed_books` + decrements copies in books)
    - Refreshes borrowed list + Main.books

  - `returnBook(String bookID): void`:
    - Deletes from `borrowed_books` + increments books table
    - Refreshes borrowed list + Main.books

  - `login(): boolean` → Authenticates via DB.
  - `sign_up(): boolean` → Delegates registration to Admin.
  - `show_menu(): void` → Provides interactive console UI.

---


## Main.java — System Entry point

- `Main.java` is the **entry point** and **controller** of the application.  

- Loads data from DB (`loadDatabase()`).
- Maintains **global state**:
  ```java
  public static Map<String, Book> books;
  public static Map<String, User> users;
  public static Map<String, Admin> admins;
  public static Set<String> genres;

  ```
- Handles authentication via `show_first_options()`.

- Directs flow to either `Admin` or `RegularUse`r menus.

- Keeps the in-memory state (`books`, `users`, `admins`) synchronized with the database.

## System Workflow (High-Level)
```text
Main.main()
   ↓
loadDatabase() ──────────> Database (fetch admins, users, books, genres)
   ↓
show_first_options()
   ↓
User (Admin OR RegularUser)
   ↓
sign_up() / login()
   ↓
Database (validate/insert credentials)
   ↓
User.show_menu()
   ↓
[Option1(if User): View Book Catalog, Return Book, Borrow book]
[Option2(if Admin): CRUD operations for Book, Admin and  RegularUser]
   ↓
Database (updates)
   ↓
Main.books / Main.users refreshed

```
## Example Workflows
### 1️⃣ Regular User Login & Borrow Book
```text
Main.main()
   ↓
show_first_options() → choose RegularUser
   ↓
login() → Database validates credentials
   ↓
RegularUser.show_menu()
   ↓
choose "Borrow Book"
   ↓
borrowBook(bookId)
   ↓
Database: INSERT into borrowed_books + decrement copies
   ↓
Main.books & borrowedBooks updated
   ↓
Success message to user
```
### 2️⃣ Admin Creates New Admin & Adds Book with New Admin
```text
Main.main()
   ↓
show_first_options() → choose Admin
   ↓
login() → Database validates existing admin
   ↓
Admin.show_menu()
   ↓
choose "Create New Admin"
   ↓
add_new_Admin(new Admin)
   ↓
Database INSERT into admins
   ↓
logout()
   ↓
show_first_options() → choose Admin
  ↓
login() →  login with new Admin
   ↓
Admin.show_menu()
   ↓
choose "Add New Book"
   ↓
addBook(Book)
   ↓
Database INSERT into books
   ↓
Main.books updated
   ↓
Success message to admin
```

  
## 💡Concepts Demonstrated
- OOP: Inheritance (Admin, RegularUser from User), Interfaces (Borrowable, Searchable), Encapsulation (Book and User attributes).
- Database Integration: JDBC with MySQL.
- System Controller: Centralized control via Main.java.
- Code Modularity: Separation of concerns across multiple classes.

## 🛠️ Technologies Used
- Java (JDK 17+)
- MySQL (JDBC)
- Object-Oriented Programming
