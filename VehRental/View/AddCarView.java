package View;

import Controller.AddCarController;
import Model.Car;

import javax.swing.*;
import java.awt.*;

public class AddCarView extends JFrame {
    private final AddCarController controller;

    public AddCarView(AddCarController controller) {
        this.controller = controller;

        setTitle("➕ Add New Vehicle");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Input fields
        JComboBox<String> categoryField = new JComboBox<>(new String[]{"2W", "4W"});
        JTextField brandField = new JTextField();
        JTextField modelField = new JTextField();
        JComboBox<String> fuelTypeField = new JComboBox<>(new String[]{"Petrol", "Diesel", "EV"});
        JTextField regNumberField = new JTextField();
        JTextField rentField = new JTextField();
        JTextField availableField = new JTextField();

        // Add form components
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);

        panel.add(new JLabel("Brand:"));
        panel.add(brandField);

        panel.add(new JLabel("Model Name:"));
        panel.add(modelField);

        panel.add(new JLabel("Fuel Type:"));
        panel.add(fuelTypeField);

        panel.add(new JLabel("Registration No.:"));
        panel.add(regNumberField);

        panel.add(new JLabel("Rent per Day:"));
        panel.add(rentField);

        panel.add(new JLabel("Available Vehicles:"));
        panel.add(availableField);

        JButton submitBtn = new JButton("Add Vehicle");
        JButton cancelBtn = new JButton("Cancel");

        panel.add(submitBtn);
        panel.add(cancelBtn);

        add(panel);

        // Action listener for Add Vehicle button
        submitBtn.addActionListener(e -> {
            try {
                String category = categoryField.getSelectedItem().toString();
                String brand = brandField.getText().trim();
                String model = modelField.getText().trim();
                String fuelType = fuelTypeField.getSelectedItem().toString();
                String regNumber = regNumberField.getText().trim();
                double rent = Double.parseDouble(rentField.getText().trim());
                int available = availableField.getText().isEmpty() ? 5 : Integer.parseInt(availableField.getText().trim());

                Car car = new Car(model, category, fuelType, regNumber, rent, available, brand.isEmpty() ? null : brand);
                controller.addCar(car);

                JOptionPane.showMessageDialog(this, "✅ Vehicle added successfully!");
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Invalid input. Please check numeric fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> dispose());
    }
}
