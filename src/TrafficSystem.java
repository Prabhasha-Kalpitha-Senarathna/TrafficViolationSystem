import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TrafficSystem {
    private ArrayList<Violation> violations;
    private int violationCounter;

    public TrafficSystem() {
        violations = new ArrayList<>();
        violationCounter = 1;
        loadSampleData();
    }

    // Load sample data for testing
    private void loadSampleData() {
        addViolation("BCC-1234", "Car", "Speeding", 1500.0, "Main Street,Kribathgoda", LocalDate.of(2025, 11, 11));
        addViolation("CBR-5678", "Bike", "No Helmet", 2000.0, "Park Avenuet,Kadawatha", LocalDate.of(2025, 11, 12));
        addViolation("BCR-5678", "Car", "Speeding", 1500.0, "Kadawatha", LocalDate.of(2025, 11, 13));
        violations.get(1).markAsPaid();
        addViolation("CBR-1234", "Car", "Signal Jump", 2500.0, "Buliugha Junction", LocalDate.of(2025, 11, 14));
        addViolation("LB-9012", "Truck", "Overload", 1000.0, "Tyre Junction", LocalDate.of(2025, 11, 10));
        addViolation("BJQ-9012", "Bike", "Illegal Parking", 2400.0, "Kiribathgoda", LocalDate.of(2025, 11, 15));
        violations.get(3).markAsPaid();
    }

    // Add new violation
    public String addViolation(String plateNumber, String vehicleType, String offenseType,
                               double fineAmount, String location, LocalDate date) {
        String id = String.format("V%03d", violationCounter++);
        Violation v = new Violation(id, plateNumber, vehicleType, offenseType,
                fineAmount, location, date);
        violations.add(v);
        return id;
    }

    // Update payment status
    public boolean updatePaymentStatus(String violationId) {
        for (Violation v : violations) {
            if (v.getViolationId().equalsIgnoreCase(violationId)) {
                if (v.getPaymentStatus().equals("Paid")) {
                    System.out.println("⚠ This violation is already marked as paid.");
                    return false;
                }
                v.markAsPaid();
                return true;
            }
        }
        return false;
    }

    // Search by plate number
    public List<Violation> searchByPlateNumber(String plateNumber) {
        return violations.stream()
                .filter(v -> v.getPlateNumber().equalsIgnoreCase(plateNumber))
                .collect(Collectors.toList());
    }

    // Search by location
    public List<Violation> searchByLocation(String location) {
        return violations.stream()
                .filter(v -> v.getLocation().toLowerCase().contains(location.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Display list of violations
    public void displayViolations(List<Violation> list) {
        if (list.isEmpty()) {
            System.out.println("No violations found.");
            return;
        }

        System.out.println("=".repeat(120));
        System.out.println(Violation.getHeader());
        System.out.println("-".repeat(120));
        for (Violation v : list) {
            System.out.println(v);
        }
        System.out.println("=".repeat(120));
    }

    // Display all violations
    public void displayAllViolations() {
        displayViolations(violations);
    }

    // Generate daily report
    public void generateDailyReport(LocalDate date) {
        List<Violation> dailyViolations = violations.stream()
                .filter(v -> v.getDate().equals(date))
                .collect(Collectors.toList());

        System.out.println("\n==================== DAILY REPORT FOR " + date + " ====================");

        if (dailyViolations.isEmpty()) {
            System.out.println("No violations recorded on this date.");
            return;
        }

        double totalFines = dailyViolations.stream().mapToDouble(Violation::getFineAmount).sum();
        double paidFines = dailyViolations.stream()
                .filter(v -> v.getPaymentStatus().equals("Paid"))
                .mapToDouble(Violation::getFineAmount).sum();

        System.out.println("Total Violations: " + dailyViolations.size());
        System.out.println("Total Fines Issued: $" + String.format("%.2f", totalFines));
        System.out.println("Total Collected: $" + String.format("%.2f", paidFines));
        System.out.println("Total Pending: $" + String.format("%.2f", (totalFines - paidFines)));
        System.out.println("=".repeat(70));

        System.out.println("\nDetailed Records:");
        displayViolations(dailyViolations);
    }

    // Generate overall report
    public void generateOverallReport() {
        System.out.println("\n==================== OVERALL STATISTICS ====================");

        int total = violations.size();
        double totalFines = violations.stream().mapToDouble(Violation::getFineAmount).sum();
        double paidFines = violations.stream()
                .filter(v -> v.getPaymentStatus().equals("Paid"))
                .mapToDouble(Violation::getFineAmount).sum();
        double pendingFines = totalFines - paidFines;
        long paidCount = violations.stream()
                .filter(v -> v.getPaymentStatus().equals("Paid"))
                .count();

        System.out.println("Total Violations: " + total);
        System.out.println("Total Fines Issued: $" + String.format("%.2f", totalFines));
        System.out.println("Total Collected (Paid): $" + String.format("%.2f", paidFines));
        System.out.println("Total Pending: $" + String.format("%.2f", pendingFines));
        System.out.println("Average Fine Amount: $" + String.format("%.2f", (totalFines / total)));
        System.out.println("=".repeat(60));

        System.out.println("\nPayment Breakdown:");
        System.out.println("  - Paid: " + paidCount + " violations");
        System.out.println("  - Pending: " + (total - paidCount) + " violations");
    }

    // Get top offense types
    public void getTopOffenses() {
        Map<String, Integer> offenseCount = new HashMap<>();

        for (Violation v : violations) {
            offenseCount.put(v.getOffenseType(),
                    offenseCount.getOrDefault(v.getOffenseType(), 0) + 1);
        }

        System.out.println("\n==================== TOP OFFENSE TYPES ====================");
        offenseCount.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(3)
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue() + " violation(s)"));
        System.out.println("=".repeat(60));
    }
}