import java.io.*;
import java.util.*;

public class LibraryApp {
    private static List<Patron> patrons = new ArrayList<>();  // List to store patrons
    private static Scanner scanner = new Scanner(System.in);  // Scanner for user input

    //First I will set some methods to ensure proper input of ID and fine

    private static int getValidInputInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    // Parse fine as a double and handle NumberFormatException
    private static double parseFine(String fineString) {
        try {
            return Double.parseDouble(fineString);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid fine value. Skipping patron.");
            return -1;  // Invalid fine, return an out-of-bounds value
        }
    }

    // Check if PatronID already exists in the system
    private static boolean isPatronIDExists(String patronID) {
        for (Patron patron : patrons) {
            if (patron.getId().equals(patronID)) {
                return true;
            }
        }
        return false;
    }
    // this method ensure the iD is 7 digits
    private static String getValidPatronID() {
        String patronID;
        while (true) {
            System.out.println("Enter the Patron's ID (7 digits): ");
            patronID = scanner.nextLine();

            if (patronID.matches("\\d{7}") && !isPatronIDExists(patronID)) {  // "\\d{7}" is used to set 7 digits
                break;
            } else {
                System.out.println("Error: PatronID must be exactly 7 digits and unique. Please try again");
            }
        }
        return patronID;
    }
    // Get a valid fine input
    //This will make sure the fne s double, between 0-5000
    private static double getValidFine() {
        double overdueFine;
        while (true) {
            System.out.print("Enter Overdue Fine: ");
            try {
                overdueFine = Double.parseDouble(scanner.nextLine());
                if (overdueFine >= 0 && overdueFine <= 5000) {
                    break;
                } else {
                    System.out.println("Error: Fine must be between $0 and $5000.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a valid number for the fine.");
            }
        }
        return overdueFine;
    }

    // Now let's code for the features the app offers: add, remove, display, search by ID
        // Method to add a new patron
    public static void addPatron() {
        // Ask the user if they want to add patrons from a file or manually
        System.out.println("Do you want to add patrons from a file (1) or manually (2)?");
        int option = getValidInputInt();

        if (option == 1) {
            // If option 1 is chosen, prompt the user to enter the file path
            System.out.print("Please enter the full path of the file to import patrons: ");
            String filepath = scanner.nextLine();
            addFromFile(filepath);  // Load patrons from the file
        } else if (option == 2) {
            // If option 2 is chosen, allow the user to enter patron details manually
            addManually();
        } else {
            System.out.println("Invalid option.");  // Handle invalid input
        }
        displayPatrons();  // Display all patrons after adding as directed in assignment instructions
    }

    // Method to import patrons from a text file
    public static void addFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by '-' separator to ensure proper file format as directed in the assignment
                String[] parts = line.split("-");
                if (parts.length == 4) {
                    String patronID = parts[0].trim();
                    String patronName = parts[1].trim();
                    String address = parts[2].trim();
                    double fine = parseFine(parts[3].trim()); //calling parse fine to ensure it's double

                    if (fine < 0 || fine > 5000) {
                        System.out.println("Error: Fine for patron " + patronID + " is out of range. It must be between $0 and $5000.");
                        continue;
                    }

                    // Checking if PatronID is unique calling isPatronDExists method
                    if (isPatronIDExists(patronID)) {
                        System.out.println("Error: PatronID " + patronID + " already exists. Skipping this patron.");
                    } else {
                        // Create a new Patron object and add it to the list
                        Patron newPatron = new Patron(patronID, patronName, address, fine);
                        patrons.add(newPatron);
                        System.out.println("Patron " + patronID + " added successfully.");
                    }
                } else {
                    System.out.println("Error: Invalid data format in file. Skipping line.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to read the file " + filename);
        }
    }




    // Method to manually add a new patron
    public static void addManually() {
        String patronID = getValidPatronID();
        System.out.print("Enter patron name: ");
        String patronName = scanner.nextLine();

        System.out.print("Enter patron address: ");
        String address = scanner.nextLine();

        double overdueFine = getValidFine();

        // Create a new Patron object and add it to the list
        patrons.add(new Patron(patronID, patronName, address, overdueFine));
        System.out.println("Patron added successfully.");
    }



    // Method to remove a patron by their ID
    public static void removePatrons() {
        System.out.print("Enter the ID of the patron to remove: ");
        String patronID = scanner.nextLine();

        Iterator<Patron> iterator = patrons.iterator();
        while (iterator.hasNext()) {
            Patron patron = iterator.next();
            if (patron.getId().equals(patronID)) {
                iterator.remove();
                System.out.println("Patron removed successfully.");
                displayPatrons();  // Will display updated list
                return;
            }
        }

        System.out.println("Patron with ID " + patronID + " not found.");
    }

    // Method to display all patrons
    public static void displayPatrons() {
        if (patrons.isEmpty()) {
            System.out.println("No patrons to display.");
        } else {
            System.out.println("\nList of All Patrons:");
            for (Patron patron : patrons) {
                System.out.println(patron.getId()+"- " + patron.getName()+ "- "+ patron.getAddress()+"- $" + patron.getOverdueFine());
                System.out.println("--------------------------------");
            }
        }
    }

    // Method to search a patron by ID
    public static void searchPatronById() {
        System.out.print("Enter PatronID to search: ");
        String patronID = scanner.nextLine();

        for (Patron patron : patrons) {
            if (patron.getId().equals(patronID)) {
                System.out.println("Patron found: " + patron);
                return;
            }
        }
        System.out.println("Error: Patron with ID " + patronID + " not found.");
    }


}
