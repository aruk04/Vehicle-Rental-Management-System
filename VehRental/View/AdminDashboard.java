package View;

import Controller.*;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    private final AddCarView addCarView;
    private final ViewCarsView viewCarsView;
    private final RentCarView rentCarView;
    private final DeleteCarView deleteCarView;
    private final AuthController authController;

    public AdminDashboard(AddCarView addCarView, ViewCarsView viewCarsView, RentCarView rentCarView, DeleteCarView deleteCarView, AuthController authController) {
        this.addCarView = addCarView;
        this.viewCarsView = viewCarsView;
        this.rentCarView = rentCarView;
        this.deleteCarView = deleteCarView;
        this.authController = authController;

        setTitle("🚗 Admin Dashboard - Vehicle Rental System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.decode("#f4f6f8"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel heading = new JLabel("Admin Dashboard");
        heading.setFont(new Font("SansSerif", Font.BOLD, 22));
        heading.setForeground(Color.decode("#333"));

        gbc.gridy = 0;
        mainPanel.add(heading, gbc);

        JButton addCarBtn = createStyledButton("➕ Add Vehicle");
        JButton viewCarsBtn = createStyledButton("📋 View Vehicles");
        JButton updateVehicleBtn = createStyledButton("🔄 Update Vehicle Quantity"); // ✅ Styled now
        JButton deleteCarBtn = createStyledButton("🗑️ Delete Vehicle");
        JButton logoutBtn = createStyledButton("🚪 Logout");

        // ✅ Action Listeners
        addCarBtn.addActionListener(e -> addCarView.setVisible(true));
        viewCarsBtn.addActionListener(e -> {
            viewCarsView.refreshTable();
            viewCarsView.setVisible(true);
        });
        updateVehicleBtn.addActionListener(e -> UpdateVehicleCountView.showDialog(this, authController));
        deleteCarBtn.addActionListener(e -> deleteCarView.setVisible(true));
        logoutBtn.addActionListener(e -> logout());

        // ✅ Add buttons to panel
        gbc.gridy++;
        mainPanel.add(addCarBtn, gbc);
        gbc.gridy++;
        mainPanel.add(viewCarsBtn, gbc);
        gbc.gridy++;
        mainPanel.add(updateVehicleBtn, gbc); // ✅ Added to panel
        gbc.gridy++;
        mainPanel.add(deleteCarBtn, gbc);
        gbc.gridy++;
        mainPanel.add(logoutBtn, gbc);

        add(mainPanel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.setBackground(new Color(66, 133, 244));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        return button;
    }

    private void logout() {
        dispose();
        JOptionPane.showMessageDialog(null, "You have been logged out.", "Logout", JOptionPane.INFORMATION_MESSAGE);
        new AuthGUI(authController, addCarView, viewCarsView, rentCarView, deleteCarView).setVisible(true);
    }
}
