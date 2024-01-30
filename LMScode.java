import java.sql.*;
import java.util.Scanner;

public class LMS {

    //jdbc connectivity
    static final String DB_URL = "jdbc:mysql://localhost/library";
    static final String USER = "root";
    static final String PASS = "password";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("Welcome to LMS");
            System.out.println("1. Login as Admin");
            System.out.println("2. Student Menu");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    studentMenu();
                    break;
                case 3:
                    System.out.println("Thank you for using Library Management System...");
                    break;
                default:
                    System.out.println("Invalid choice. Retry again.");
            }
        } while (choice != 3);
    }

    // login
    public static void login() {
        Scanner sc = new Scanner(System.in);
        String username = "";
        String password = "";
        System.out.println("Please enter your username and password to login.");
        System.out.print("Username: ");
        username = sc.nextLine();
        System.out.print("Password: ");
        password = sc.nextLine();
        if (username.equals("admin") && password.equals("password")) {
            adminMenu();
        } else {
            System.out.println("Invalid username or password. Retry again.");
        }
    }

    // admin menu
    public static void adminMenu() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("Hi Admin");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. View All Books");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    updateBook();
                    break;
                case 3:
                    deleteBook();
                    break;
                case 4:
                    viewAllBooks();
                    break;
                case 5:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Retry again.");
            }
        } while (choice != 5);
    }

    // student menu
    public static void studentMenu() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("Student Menu");
            System.out.println("1. Register Student");
            System.out.println("2. Search Book by Name");
            System.out.println("3. Record Book Taken");
            System.out.println("4. Record Book Return");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    registerStudent();
                    break;
                case 2:
                    searchBookByName();
                    break;
                case 3:
                    recordBookTaken();
                    break;
                case 4:
                    recordBookReturn();
                    break;
                case 5:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Retry again.");
            }
        } while (choice != 5);
    }

 // add books
    public static void addBook() {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Enter Book ID: ");
            int bookId = sc.nextInt();
            System.out.println("Enter Book Name: ");
            String bookName = sc.next();
            System.out.println("Enter Author Name: ");
            String authorName = sc.next();
            System.out.println("Enter Publisher Name: ");
            String publisherName = sc.next();
            System.out.println("Enter Quantity: ");
            int quantity = sc.nextInt();
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "INSERT INTO books VALUES (" + bookId + ", '" + bookName + "', '" + authorName + "', '" + publisherName + "', " + quantity + ")";
            stmt.executeUpdate(sql);
            System.out.println("Successfully added the book to LMS.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    // delete books
    public static void deleteBook() {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Enter Book ID: ");
            int bookId = sc.nextInt();
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "DELETE FROM books WHERE book_id = " + bookId;
            int rowsAffected = stmt.executeUpdate(sql);
            if (rowsAffected > 0) {
                System.out.println("Book deleted successfully.");
            } else {
                System.out.println("Book with ID " + bookId + " not found.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    // update books
    public static void updateBook() {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Enter Book ID: ");
            int bookId = sc.nextInt();
            System.out.println("Enter Quantity: ");
            int quantity = sc.nextInt();
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "UPDATE books SET quantity = " + quantity + " WHERE book_id = " + bookId;
            int rowsAffected = stmt.executeUpdate(sql);
            if (rowsAffected > 0) {
                System.out.println("Book updated successfully.");
            } else {
                System.out.println("Book with ID " + bookId + " not found.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    // view all books
    public static void viewAllBooks() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //Class.forName("com.mysql.jdbc.Driver");
        	Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM books";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String bookName = rs.getString("book_name");
                String authorName = rs.getString("author_name");
                String publisherName = rs.getString("publisher_name");
                int quantity = rs.getInt("quantity");
                System.out.println("Book ID: " + bookId);
                System.out.println("Book Name: " + bookName);
                System.out.println("Author Name: " + authorName);
                System.out.println("Publisher Name: " + publisherName);
                System.out.println("Quantity: " + quantity);
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
    }


    // register student
    public static void registerStudent() {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Enter Student ID: ");
            int studentId = sc.nextInt();
            System.out.println("Enter Student Name: ");
            String studentName = sc.next();
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "INSERT INTO students VALUES (" + studentId + ", '" + studentName + "')";
            stmt.executeUpdate(sql);
            System.out.println("Student registered successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    // search book by name
    public static void searchBookByName() {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            System.out.println("Enter Book Name to search: ");
            String bookName = sc.nextLine();
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM books WHERE book_name LIKE '%" + bookName + "%'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String authorName = rs.getString("author_name");
                String publisherName = rs.getString("publisher_name");
                int quantity = rs.getInt("quantity");
                System.out.println("Book ID: " + bookId);
                System.out.println("Book Name: " + bookName);
                System.out.println("Author Name: " + authorName);
                System.out.println("Publisher Name: " + publisherName);
                System.out.println("Quantity: " + quantity);
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    // record book taken by student
    public static void recordBookTaken() {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Enter Student ID: ");
            int studentId = sc.nextInt();
            System.out.println("Enter Book ID: ");
            int bookId = sc.nextInt();
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "INSERT INTO student_books VALUES (" + studentId + ", " + bookId + ")";
            stmt.executeUpdate(sql);
            System.out.println("Book taken by student successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    // record book return by student
    public static void recordBookReturn() {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Enter Student ID: ");
            int studentId = sc.nextInt();
            System.out.println("Enter Book ID: ");
            int bookId = sc.nextInt();
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "DELETE FROM student_books WHERE student_id = " + studentId + " AND book_id = " + bookId;
            stmt.executeUpdate(sql);
            System.out.println("Book returned by student successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    // close resources
    private static void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}




/*
-- mysql code

CREATE DATABASE library;

USE library;

CREATE TABLE books (
    book_id INT PRIMARY KEY,
    book_name VARCHAR(255),
    author_name VARCHAR(255),
    publisher_name VARCHAR(255),
    quantity INT
);

CREATE TABLE students (
    student_id INT PRIMARY KEY,
    student_name VARCHAR(255)
);

CREATE TABLE student_books (
    student_id INT,
    book_id INT,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    PRIMARY KEY (student_id, book_id)
);






*/
