package Model;

import java.sql.*;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PaymentService {

    /**
     * Calculates total amount for a rental based on dates and rent_per_day.
     * Stores it in the payment table along with a unique transaction ID.
     *
     * @param conn      Active DB connection (transaction should be managed by caller)
     * @param rentalId  ID of the rental record
     */
    public void calculateAndStorePayment(Connection conn, int rentalId) throws SQLException {
        String rentalQuery = "SELECT r.rent_date, r.return_date, v.rent_per_day " +
                             "FROM rentals r JOIN vehicles v ON r.vehicle_id = v.vehicle_id " +
                             "WHERE r.rental_id = ?";
        System.out.println("✅ Payment of ₹ recorded for rental ID " + rentalId);
        try (PreparedStatement ps = conn.prepareStatement(rentalQuery)) {
            ps.setInt(1, rentalId);
            conn.setAutoCommit(false);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Date rentDate = rs.getDate("rent_date");
                    Date returnDate = rs.getDate("return_date");
                    double rentPerDay = rs.getDouble("rent_per_day");

                    // Calculate number of days (minimum 1)
                    long diffInMillies = returnDate.getTime() - rentDate.getTime();
                    long days = Math.max(1, TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS));

                    double amount = rentPerDay * days;

                    // Generate a unique transaction ID
                    String transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

                    // Insert into payment table
                    String insertPayment = "INSERT INTO payment (rental_id, transaction_id, amount) VALUES (?, ?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertPayment)) {
                        insertStmt.setInt(1, rentalId);
                        insertStmt.setString(2, transactionId);
                        insertStmt.setDouble(3, amount);

                        int rows = insertStmt.executeUpdate();
                        if (rows > 0) {
                            System.out.println("✅ Payment of ₹" + amount + " recorded for rental ID " + rentalId);
                            System.out.println("🧾 Transaction ID: " + transactionId);
                        } else {
                            System.out.println("❌ Payment insertion failed.");
                        }
                    }
                } else {
                    System.out.println("❌ Rental ID not found in rentals table.");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error while inserting payment: " + e.getMessage());
            e.printStackTrace();
            throw e; // rethrow for upper-level rollback
        }
    }
}
