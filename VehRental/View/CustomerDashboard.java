package View;

import javax.swing.*;
import java.awt.*;
import Controller.*;

public class CustomerDashboard extends JFrame {
    private final ViewCarsView viewCarsView;
    private final RentCarView rentCarView;
    private final AddCarView addCarView;
    private final AuthController authController;
    private final int customerId;

    public CustomerDashboard(ViewCarsView viewCarsView, RentCarView rentCarView,
                             AddCarView addCarView, AuthController authController, int customerId) {
        this.viewCarsView = viewCarsView;
        this.rentCarView = rentCarView;
        this.addCarView = addCarView;
        this.authController = authController;
        this.customerId = customerId; // Store the logged-in customer's ID

        setTitle("🚗 Customer Dashboard - Vehicle Rental System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.decode("#f4f6f8")); // Light gray background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel heading = new JLabel("Customer Dashboard");
        heading.setFont(new Font("SansSerif", Font.BOLD, 22));
        heading.setForeground(Color.decode("#333"));

        gbc.gridy = 0;
        mainPanel.add(heading, gbc);

        // Create styled buttons
        JButton viewCarsBtn = createStyledButton("📋 View Vehicles");
        JButton rentCarBtn = createStyledButton("🚙 Rent Vehicle");
        JButton logoutBtn = createStyledButton("🚪 Logout");

        // Add action listeners to the buttons
        viewCarsBtn.addActionListener(e -> {
            viewCarsView.refreshTable();  // Refresh the table each time button is clicked
            viewCarsView.setVisible(true);
        });
        
        rentCarBtn.addActionListener(e -> rentCarView.showView(customerId));  // Pass customer ID
        logoutBtn.addActionListener(e -> logout());

        // Add buttons to the panel
        gbc.gridy++;
        mainPanel.add(viewCarsBtn, gbc);
        gbc.gridy++;
        mainPanel.add(rentCarBtn, gbc);
        gbc.gridy++;
        mainPanel.add(logoutBtn, gbc);

        add(mainPanel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.setBackground(new Color(76, 175, 80)); // Greenish button color
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        return button;
    }

    private void logout() {
        dispose();
        JOptionPane.showMessageDialog(null, "You have been logged out.", "Logout", JOptionPane.INFORMATION_MESSAGE);
        new AuthGUI(authController, addCarView, viewCarsView, rentCarView, null).setVisible(true);
    }
}
