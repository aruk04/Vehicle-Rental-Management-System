package Model;
import java.sql.*;
import java.time.LocalDate;

public class RentalModel {
    String url = "jdbc:mysql://localhost:3306/vehicle_rental";
    String user = "root";
    String password = "archisha512";

    public boolean rentVehicle(int customerId, int vehicleId, LocalDate rentDate, LocalDate returnDate) {
        String rentSql = "INSERT INTO rentals (customer_id, vehicle_id, rent_date, return_date) VALUES (?, ?, ?, ?)";
        String updateVehicleSql = "UPDATE vehicles SET available_count = available_count - 1 WHERE vehicle_id = ? AND available_count > 0";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false); // Start transaction

            // Step 1: Update vehicle availability (only if available)
            int rowsAffected;
            try (PreparedStatement updateStmt = conn.prepareStatement(updateVehicleSql)) {
                updateStmt.setInt(1, vehicleId);
                rowsAffected = updateStmt.executeUpdate();
            }

            if (rowsAffected == 0) {
                conn.rollback();
                System.out.println("❌ Cannot rent: Vehicle not available (available_count = 0)");
                return false;
            }

            // Step 2: Insert rental record
            int rentalId;
            try (PreparedStatement rentStmt = conn.prepareStatement(rentSql, Statement.RETURN_GENERATED_KEYS)) {
                rentStmt.setInt(1, customerId);
                rentStmt.setInt(2, vehicleId);
                rentStmt.setDate(3, Date.valueOf(rentDate));
                rentStmt.setDate(4, Date.valueOf(returnDate));
                rentStmt.executeUpdate();

                ResultSet rs = rentStmt.getGeneratedKeys();
                if (rs.next()) {
                    rentalId = rs.getInt(1);
                } else {
                    conn.rollback();
                    System.out.println("❌ Failed to retrieve rental ID.");
                    return false;
                }
            }

            // Step 3: Calculate and store payment using service
            PaymentService paymentService = new PaymentService();
            paymentService.calculateAndStorePayment(conn, rentalId);

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Error during rental/payment: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public double getRentalAmount(int vehicleId, int numDays) {
        String query = "SELECT rent_per_day FROM vehicles WHERE vehicle_id = ?";
        double rentAmount = 0.0;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, vehicleId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double rentPerDay = rs.getDouble("rent_per_day");
                rentAmount = rentPerDay * numDays;
            } else {
                System.out.println("❌ Vehicle not found for ID: " + vehicleId);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error calculating rental amount: " + e.getMessage());
            e.printStackTrace();
        }

        return rentAmount;
    }
}
