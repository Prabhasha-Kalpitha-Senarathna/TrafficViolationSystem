import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;



public class Main {
    private static TrafficSystem system = new TrafficSystem();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\n🚦 Welcome to TRAFFIC VIOLATION MANAGEMENT SYSTEM 🚦\n");

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addNewViolation();
                    break;
                case 2:
                    updatePayment();
                    break;
                case 3:
                    searchViolations();
                    break;
                case 4:
                    viewAllViolations();
                    break;
                case 5:
                    generateReports();
                    break;
                case 6:
                    System.out.println("\n✓ Thank you for using TVMS. Goodbye! 👋\n");
                    running = false;
                    break;
                default:
                    System.out.println("⚠ Invalid choice. Please try again.\n");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("==========================================");
        System.out.println("  TRAFFIC VIOLATION MANAGEMENT SYSTEM");
        System.out.println("==========================================");
        System.out.println("1. Add New Violation");
        System.out.println("2. Update Payment Status");
        System.out.println("3. Search Violations");
        System.out.println("4. View All Violations");
        System.out.println("5. Generate Reports");
        System.out.println("6. Exit");
        System.out.println("==========================================");
    }

    private static void addNewViolation() {
        System.out.println("\n--- Add New Violation ---");

        System.out.print("Enter Plate Number: ");
        String plate = scanner.nextLine();

        System.out.print("Enter Vehicle Type (Car/Bike/Truck): ");
        String type = scanner.nextLine();

        System.out.print("Enter Offense Type: ");
        String offense = scanner.nextLine();

        double fine = getDoubleInput("Enter Fine Amount: $");

        System.out.print("Enter Location: ");
        String location = scanner.nextLine();

        LocalDate date = getDateInput("Enter Date (YYYY-MM-DD): ");

        String id = system.addViolation(plate, type, offense, fine, location, date);
        System.out.println("\n✓ Violation added successfully! [ID: " + id + "]\n");
    }

    private static void updatePayment() {
        System.out.println("\n--- Update Payment Status ---");
        System.out.print("Enter Violation ID: ");
        String id = scanner.nextLine();

        if (system.updatePaymentStatus(id)) {
            System.out.println("✓ Payment status updated to 'Paid'.\n");
        } else {
            System.out.println("✗ Violation ID not found.\n");
        }
    }

    private static void searchViolations() {
        System.out.println("\n--- Search Violations ---");
        System.out.println("1. Search by Plate Number");
        System.out.println("2. Search by Location");
        int choice = getIntInput("Enter choice: ");

        List<Violation> results = null;

        if (choice == 1) {
            System.out.print("Enter Plate Number: ");
            String plate = scanner.nextLine();
            results = system.searchByPlateNumber(plate);
            System.out.println("\nFound " + results.size() + " violation(s):");
        } else if (choice == 2) {
            System.out.print("Enter Location: ");
            String location = scanner.nextLine();
            results = system.searchByLocation(location);
            System.out.println("\nFound " + results.size() + " violation(s):");
        } else {
            System.out.println("Invalid choice.\n");
            return;
        }

        system.displayViolations(results);
        System.out.println();
    }

    private static void viewAllViolations() {
        System.out.println("\n--- All Violations ---");
        system.displayAllViolations();
        System.out.println();
    }

    private static void generateReports() {
        System.out.println("\n--- Generate Reports ---");
        System.out.println("1. Daily Report");
        System.out.println("2. Overall Statistics");
        System.out.println("3. Top Offense Types");
        int choice = getIntInput("Enter choice: ");

        switch (choice) {
            case 1:
                LocalDate date = getDateInput("Enter Date (YYYY-MM-DD): ");
                system.generateDailyReport(date);
                break;
            case 2:
                system.generateOverallReport();
                break;
            case 3:
                system.getTopOffenses();
                break;
            default:
                System.out.println("Invalid choice.");
        }
        System.out.println();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("⚠ Invalid input. Please enter a number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("⚠ Invalid input. Please enter a valid amount.");
            }
        }
    }

    private static LocalDate getDateInput(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("⚠ Invalid date format. Use YYYY-MM-DD.");
            }
        }
    }
}