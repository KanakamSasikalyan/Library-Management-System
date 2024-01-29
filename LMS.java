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
            System.out.println("1. Login here");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    System.out.println("Thank you for using Library Management System...");
                    break;
                default:
                    System.out.println("Invalid choice. Retry again.");
            }
        } while (choice != 2);
    }

  	//login
    public static void login() {
        Scanner sc = new Scanner(System.in);
        String username = "";
        String password = "";
        System.out.println("please enter your username and password to login.");
        System.out.print("Username: ");
        username = sc.nextLine();
        System.out.print("Password: ");
        password = sc.nextLine();
        if (username.equals("admin") && password.equals("password")) {
            adminMenu();
        } else {
            System.out.println("Invalid username or password. recheck again");
        }
    }

    // admin
    public static void adminMenu() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("Hi Admin");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. View All Books");
            System.out.println("5. back to Main Menu");
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
                    System.out.println("Invalid choice. retry again.");
            }
        } while (choice != 5);
    }

    // add  books here
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
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "INSERT INTO books VALUES (" + bookId + ", '" + bookName + "', '" + authorName + "', '" + publisherName + "', " + quantity + ")";
            stmt.executeUpdate(sql);
            System.out.println("successfully book added to lms");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
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

    // update the books here by using admin credentialss
    public static void updateBook() {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Enter Book ID: ");
            int bookId = sc.nextInt();
            System.out.println("Enter Quantity: ");
            int quantity = sc.nextInt();
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "UPDATE books SET quantity = " + quantity + " WHERE book_id = " + bookId;
            stmt.executeUpdate(sql);
            System.out.println("Book updated successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
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

    // delete the book details
    public static void deleteBook() {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Enter Book ID: ");
            int bookId = sc.nextInt();
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "DELETE FROM books WHERE book_id = " + bookId;
            stmt.executeUpdate(sql);
            System.out.println("Book deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
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

    // view all the books heree
    public static void viewAllBooks() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
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
}


// type the code in mysql workbench and run accordingly to get the output

CREATE DATABASE library;

USE library;

CREATE TABLE books (
    book_id INT PRIMARY KEY,
    book_name VARCHAR(255),
    author_name VARCHAR(255),
    publisher_name VARCHAR(255),
    quantity INT
);

