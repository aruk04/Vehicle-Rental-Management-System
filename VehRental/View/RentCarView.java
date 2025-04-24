package View;

import Controller.RentalController;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class RentCarView extends JFrame {
    private final RentalController controller;
    private JTextField vehicleIdField;
    private JSpinner rentDateSpinner;
    private JSpinner returnDateSpinner;
    private int customerId;

    public RentCarView(RentalController controller) {
        this.controller = controller;
        initGUI();
    }

    private void initGUI() {
        setTitle("🚗 Rent a Vehicle");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#f4f6f8"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel title = new JLabel("Rent a Car");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.decode("#333"));
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);
        gbc.gridwidth = 1;

        gbc.gridy++;
        panel.add(new JLabel("Vehicle ID:"), gbc);
        vehicleIdField = new JTextField();
        vehicleIdField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        vehicleIdField.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
        gbc.gridx = 1;
        panel.add(vehicleIdField, gbc);
        gbc.gridx = 0;

        // Rent Start Date Spinner
        gbc.gridy++;
        panel.add(new JLabel("Rent Start Date:"), gbc);
        rentDateSpinner = new JSpinner(new SpinnerDateModel());
        rentDateSpinner.setFont(new Font("SansSerif", Font.PLAIN, 16));
        rentDateSpinner.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
        rentDateSpinner.setEditor(new JSpinner.DateEditor(rentDateSpinner, "yyyy-MM-dd"));
        gbc.gridx = 1;
        panel.add(rentDateSpinner, gbc);
        gbc.gridx = 0;

        // Return Date Spinner
        gbc.gridy++;
        panel.add(new JLabel("Return Date:"), gbc);
        returnDateSpinner = new JSpinner(new SpinnerDateModel());
        returnDateSpinner.setFont(new Font("SansSerif", Font.PLAIN, 16));
        returnDateSpinner.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
        returnDateSpinner.setEditor(new JSpinner.DateEditor(returnDateSpinner, "yyyy-MM-dd"));
        gbc.gridx = 1;
        panel.add(returnDateSpinner, gbc);
        gbc.gridx = 0;

        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton rentBtn = new JButton("Rent Now");
        rentBtn.setBackground(new Color(76, 175, 80));
        rentBtn.setForeground(Color.WHITE);
        rentBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        rentBtn.setFocusPainted(false);
        rentBtn.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        rentBtn.addActionListener(e -> handleRentAction());
        panel.add(rentBtn, gbc);

        add(panel);
    }

    public void showView(int customerId) {
        this.customerId = customerId;
        setVisible(true);
    }

    private void handleRentAction() {
        try {
            String vehicleIdStr = vehicleIdField.getText().trim();
            Date rentDateSelected = (Date) rentDateSpinner.getValue();
            Date returnDateSelected = (Date) returnDateSpinner.getValue();

            if (vehicleIdStr.isEmpty() || rentDateSelected == null || returnDateSelected == null) {
                JOptionPane.showMessageDialog(this, "All fields must be filled!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int vehicleId = Integer.parseInt(vehicleIdStr);
            LocalDate rentDate = convertToLocalDate(rentDateSelected);
            LocalDate returnDate = convertToLocalDate(returnDateSelected);

            if (returnDate.isBefore(rentDate)) {
                JOptionPane.showMessageDialog(this, "Return date cannot be before rent date.", "Invalid Dates", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean success = controller.rentVehicle(customerId, vehicleId, rentDate, returnDate);
            if (success) {
                long days = ChronoUnit.DAYS.between(rentDate, returnDate);
                double amount = controller.calculateRentalAmount(vehicleId, (int) days);
                JOptionPane.showMessageDialog(this, "✅ Rental successful! Amount: ₹" + amount, "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Rental failed. Please check vehicle ID and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid vehicle ID. It must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private LocalDate convertToLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }
}
