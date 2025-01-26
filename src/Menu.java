import java.util.Scanner;

public class Menu {
    private LibraryApp libraryApp;
    private Scanner scanner;

    // Constructor to initialize the LibraryApp and Scanner
    public Menu() {
        libraryApp = new LibraryApp();  // Initialize the LibraryApp instance
        scanner = new Scanner(System.in);  // Initialize the Scanner for user input
    }
    public void displayMenu() {

        while (true) { //while true will let the program run and exits only if the user chooses to
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add patron");
            System.out.println("2. Remove patron by ID");
            System.out.println("3. List all patrons");
            System.out.println("4. Search patron by ID");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    libraryApp.addPatron();
                    break;

                case "2":
                    libraryApp.removePatrons();
                    break;
                case "3":
                    libraryApp.displayPatrons();
                    break;
                case "4":
                    libraryApp.searchPatronById();  // Use the single lms instance
                    break;
                case "5":
                    System.out.println("Exiting system. Goodbye!");
                    return; //exit program
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.displayMenu();
    }
}
