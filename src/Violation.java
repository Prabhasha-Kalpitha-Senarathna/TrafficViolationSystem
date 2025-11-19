import java.time.LocalDate;

public class Violation {
    private String violationId;
    private String plateNumber;
    private String vehicleType;
    private String offenseType;
    private double fineAmount;
    private String location;
    private LocalDate date;
    private String paymentStatus;

    // Constructor
    public Violation(String violationId, String plateNumber, String vehicleType,
                     String offenseType, double fineAmount, String location,
                     LocalDate date) {
        this.violationId = violationId;
        this.plateNumber = plateNumber;
        this.vehicleType = vehicleType;
        this.offenseType = offenseType;
        this.fineAmount = fineAmount;
        this.location = location;
        this.date = date;
        this.paymentStatus = "Pending";
    }

    // Getters
    public String getViolationId() { return violationId; }
    public String getPlateNumber() { return plateNumber; }
    public String getVehicleType() { return vehicleType; }
    public String getOffenseType() { return offenseType; }
    public double getFineAmount() { return fineAmount; }
    public String getLocation() { return location; }
    public LocalDate getDate() { return date; }
    public String getPaymentStatus() { return paymentStatus; }

    // Mark as paid
    public void markAsPaid() {
        this.paymentStatus = "Paid";
    }

    // Formatted display
    @Override
    public String toString() {
        return String.format("%-8s | %-10s | %-6s | %-18s | $%-6.2f | %-15s | %-10s | %-8s",
                violationId, plateNumber, vehicleType, offenseType,
                fineAmount, location, date.toString(), paymentStatus);
    }

    // Header for table display
    public static String getHeader() {
        return String.format("%-8s | %-10s | %-6s | %-18s | %-8s | %-15s | %-10s | %-8s",
                "ID", "Plate", "Type", "Offense", "Fine", "Location", "Date", "Status");
    }
}