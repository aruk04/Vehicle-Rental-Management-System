package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarModel {
    String url = "jdbc:mysql://localhost:3306/vehicle_rental";
    String user = "root";
    String password = "archisha512";

    // Add a new car
    public void addCar(Car car) {
        String sql = "INSERT INTO vehicles (model_name, category, fuel_type, registration_number, rent_per_day, available_count, brand) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, car.getModelName());
            stmt.setString(2, car.getCategory());
            stmt.setString(3, car.getFuelType());
            stmt.setString(4, car.getRegistrationNumber());
            stmt.setDouble(5, car.getRentPerDay());
            stmt.setInt(6, car.getAvailableCount());
            stmt.setString(7, car.getBrand()); // can be null

            stmt.executeUpdate();
            System.out.println("✅ Car added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch all vehicles
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Car car = new Car(
                        rs.getInt("vehicle_id"),
                        rs.getString("model_name"),
                        rs.getString("category"),
                        rs.getString("fuel_type"),
                        rs.getString("registration_number"),
                        rs.getDouble("rent_per_day"),
                        rs.getInt("available_count"),
                        rs.getString("brand")
                );
                cars.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    // ✅ Delete a car by vehicle ID
    public boolean deleteCarById(int vehicleId) {
        String sql = "UPDATE vehicles SET available_count = 0 WHERE vehicle_id = ?";
    
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, vehicleId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
