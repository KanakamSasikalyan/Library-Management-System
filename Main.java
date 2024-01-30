// Importing required libraries
import java.sql.*;
import java.util.Scanner;

public class Main {
    // Database credentials
    static final String DB_URL = "jdbc:mysql://localhost/library";
    static final String USER = "root";
    static final String PASS = "password";

    // Main function
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Welcome to the Library Management System!");
            System.out.println("1. Admin Login");
            System.out.println("2. Student Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    studentLogin();
                    break;
                case 3:
                    System.out.println("Thank you for using the Library Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 3);
    }

    // login for the admin
    public static void adminLogin() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        if (username.equals("admin") && password.equals("admin")) {
            int choice;
            do {
                System.out.println("Admin Menu:");
                System.out.println("1. Update Book");
                System.out.println("2. Add Book");
                System.out.println("3. Delete Book");
                System.out.println("4. View All Books");
                System.out.println("5. Back to Main Menu");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        updateBook();
                        break;
                    case 2:
                        addBook();
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
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (choice != 5);
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    // login student
    public static void studentLogin() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        if (isValidStudent(username, password)) {
            int choice;
            do {
                System.out.println("Student Menu:");
                System.out.println("1. Register");
                System.out.println("2. Search Books Based on Author");
                System.out.println("3. Search Books Based on Book Name");
                System.out.println("4. Back to Main Menu");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        register();
                        break;
                    case 2:
                        searchBooksByAuthor();
                        break;
                    case 3:
                        searchBooksByBookName();
                        break;
                    case 4:
                        System.out.println("Returning to Main Menu...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (choice != 4);
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    // student is valid
    public static boolean isValidStudent(String username, String password) {
        boolean isValid = false;
        try {
            // Open a connection
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Eexecute the query
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM students WHERE username='" + username + "' AND password='" + password + "'";
            ResultSet rs = stmt.executeQuery(sql);

            // student is existed or not
            if (rs.next()) {
                isValid = true;
            }

            // close the connection which is opened
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return isValid;
    }
    
    // update the book function
    public static void updateBook() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter book ID: ");
        int bookId = sc.nextInt();
        System.out.print("Enter new book name: ");
        String bookName = sc.nextLine();
        System.out.print("Enter new author name: ");
        String authorName = sc.nextLine();
        try {
            // open the connection
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // eexecute a query
            Statement stmt = conn.createStatement();
            String sql = "UPDATE books SET book_name='" + bookName + "', author_name='" + authorName + "' WHERE book_id=" + bookId;
            int rowsAffected = stmt.executeUpdate(sql);

            // Ccheck the book updataion
            if (rowsAffected > 0) {
                System.out.println("Book updated successfully.");
            } else {
                System.out.println("Book not found. Please try again.");
            }

            // close connection
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // add book
    public static void addBook() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter book name: ");
        String bookName = sc.nextLine();
        System.out.print("Enter author name: ");
        String authorName = sc.nextLine();
        try {
            // open connectiono
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // execute the query
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO books (book_name, author_name) VALUES ('" + bookName + "', '" + authorName + "')";
            int rowsAffected = stmt.executeUpdate(sql);

            // added the bnook successfully
            if (rowsAffected > 0) {
                System.out.println("Book added successfully.");
            } else {
                System.out.println("Book not added. Please try again.");
            }

            // close the connection
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // delte the book
    public static void deleteBook() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter book ID: ");
        int bookId = sc.nextInt();
        try {
            // open the new connnectiono
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // execute the query
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM books WHERE book_id=" + bookId;
            int rowsAffected = stmt.executeUpdate(sql);

            // check the book deleted or not;
            if (rowsAffected > 0) {
                System.out.println("Book deleted successfully.");
            } else {
                System.out.println("Book not found. Please try again.");
            }

            // close the opened connection
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // function to view all the books 
    public static void viewAllBooks() {
        try {
            // Open a connection
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // execute the query
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM books";
            ResultSet rs = stmt.executeQuery(sql);

           //display all the books
            while (rs.next()) {
                System.out.println("Book ID: " + rs.getInt("book_id"));
                System.out.println("Book Name: " + rs.getString("book_name"));
                System.out.println("Author Name: " + rs.getString("author_name"));
                System.out.println();
            }

           //close the opened connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // register the student
    public static void register() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        try {
            // open the connection 
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // execute
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO students (username, password) VALUES ('" + username + "', '" + password + "')";
            int rowsAffected = stmt.executeUpdate(sql);

            // check the student registered successfulyy or not
            if (rowsAffected > 0) {
                System.out.println("Student registered successfully.");
            } else {
                System.out.println("Student not registered. Please try again.");
            }

            // close the connection here
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // search the books based on the author
    public static void searchBooksByAuthor() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter author name: ");
        String authorName = sc.nextLine();
        try {
            // open the connectiono
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // execute the statements
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM books WHERE author_name='" + authorName + "'";
            ResultSet rs = stmt.executeQuery(sql);

            //display all the boks
            while (rs.next()) {
                System.out.println("Book ID: " + rs.getInt("book_id"));
                System.out.println("Book Name: " + rs.getString("book_name"));
                System.out.println("Author Name: " + rs.getString("author_name"));
                System.out.println();
            }

            // Close the connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // search the books based on the book name
    public static void searchBooksByBookName() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter book name: ");
        String bookName = sc.nextLine();
        try {
            // open the connection
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // execute the statement
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM books WHERE book_name='" + bookName + "'";
            ResultSet rs = stmt.executeQuery(sql);

            // display all the books
            while (rs.next()) {
                System.out.println("Book ID: " + rs.getInt("book_id"));
                System.out.println("Book Name: " + rs.getString("book_name"));
                System.out.println("Author Name: " + rs.getString("author_name"));
                System.out.println();
            }

           // close the connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}



 -- Create students table
CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

-- Create books table
CREATE TABLE books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    book_name VARCHAR(100) NOT NULL,
    author_name VARCHAR(100) NOT NULL
);

-- Insert sample data into students table
INSERT INTO students (username, password) VALUES ('john', 'password123');
INSERT INTO students (username, password) VALUES ('jane', 'password456');

-- Insert sample data into books table
INSERT INTO books (book_name, author_name) VALUES ('The Great Gatsby', 'F. Scott Fitzgerald');
INSERT INTO books (book_name, author_name) VALUES ('To Kill a Mockingbird', 'Harper Lee');
INSERT INTO books (book_name, author_name) VALUES ('1984', 'George Orwell');
INSERT INTO books (book_name, author_name) VALUES ('Pride and Prejudice', 'Jane Austen');
