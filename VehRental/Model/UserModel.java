package Model;

import java.sql.*;

public class UserModel {
    private final String url = "jdbc:mysql://localhost:3306/vehicle_rental";
    private final String dbUser = "root";
    private final String dbPassword = "archisha512";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, dbUser, dbPassword);
    }

    // ✅ Register a new customer
    public boolean registerCustomer(String username, String password, String name, String email, String phone, String hasLicense) {
        String sql = "INSERT INTO customers (username, password, name, email, phone_number, has_driving_license) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, name);
            stmt.setString(4, email);
            stmt.setString(5, phone);
            stmt.setString(6, hasLicense.toUpperCase()); // Should match ENUM('YES','NO')

            stmt.executeUpdate();
            System.out.println("✅ Customer registered successfully.");
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Registration failed: " + e.getMessage());
            return false;
        }
    }

    // ✅ Customer login (boolean)
    public boolean loginCustomer(String username, String password) {
        String sql = "SELECT * FROM customers WHERE username=? AND password=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            boolean found = rs.next();

            if (found) {
                System.out.println("✅ Customer login successful.");
            } else {
                System.out.println("❌ Invalid customer credentials.");
            }

            return found;

        } catch (SQLException e) {
            System.out.println("❌ Login failed: " + e.getMessage());
            return false;
        }
    }

    // ✅ Customer login and get customer_id
    public int validateCustomerLoginAndGetId(String username, String password) {
        String sql = "SELECT customer_id FROM customers WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("customer_id");
            }
        } catch (SQLException e) {
            System.out.println("❌ Customer ID fetch failed: " + e.getMessage());
        }
        return -1;
    }

    // ✅ Admin login
    public boolean loginAdmin(String username, String password) {
        String sql = "SELECT * FROM admins WHERE username=? AND password=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            boolean found = rs.next();

            if (found) {
                System.out.println("✅ Admin login successful.");
            } else {
                System.out.println("❌ Invalid admin credentials.");
            }

            return found;

        } catch (SQLException e) {
            System.out.println("❌ Admin login failed: " + e.getMessage());
            return false;
        }
    }

    // ✅ Admin updates vehicle count
    public boolean updateVehicleCount(int vehicleId, int newCount) {
        String sql = "UPDATE vehicles SET available_count = ? WHERE vehicle_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newCount);
            stmt.setInt(2, vehicleId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Vehicle quantity updated.");
                return true;
            } else {
                System.out.println("❌ No vehicle found with ID: " + vehicleId);
                return false;
            }

        } catch (SQLException e) {
            System.out.println("❌ Failed to update vehicle count: " + e.getMessage());
            return false;
        }
    }
}
