package View;

import Controller.AuthController;

import javax.swing.*;
import java.awt.*;

public class AuthGUI extends JFrame {
    private final AuthController controller;
    private final AddCarView addCarView;
    private final ViewCarsView viewCarsView;
    private final RentCarView rentCarView;
    private final DeleteCarView deleteCarView;

    public AuthGUI(AuthController controller, AddCarView addCarView, ViewCarsView viewCarsView, RentCarView rentCarView, DeleteCarView deleteCarView) {
        this.controller = controller;
        this.addCarView = addCarView;
        this.viewCarsView = viewCarsView;
        this.rentCarView = rentCarView;
        this.deleteCarView = deleteCarView;

        setTitle("🔐 Vehicle Rental - Authentication");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#f4f6f8"));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel heading = new JLabel("Welcome to Vehicle Rental");
        heading.setFont(new Font("SansSerif", Font.BOLD, 18));
        heading.setForeground(Color.decode("#333"));
        heading.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        panel.add(heading, gbc);

        JButton customerLoginBtn = createStyledButton("👤 Customer Login");
        JButton customerRegisterBtn = createStyledButton("📝 Customer Register");
        JButton adminLoginBtn = createStyledButton("🛠️ Admin Login");
        JButton exitBtn = createStyledButton("❌ Exit");

        gbc.gridy++;
        panel.add(customerLoginBtn, gbc);
        gbc.gridy++;
        panel.add(customerRegisterBtn, gbc);
        gbc.gridy++;
        panel.add(adminLoginBtn, gbc);
        gbc.gridy++;
        panel.add(exitBtn, gbc);

        customerLoginBtn.addActionListener(e -> showCustomerLoginDialog());
        customerRegisterBtn.addActionListener(e -> showCustomerRegisterDialog());
        adminLoginBtn.addActionListener(e -> showAdminLoginDialog());
        exitBtn.addActionListener(e -> System.exit(0));

        add(panel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 15));
        button.setFocusPainted(false);
        button.setBackground(new Color(66, 133, 244));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private void showCustomerLoginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] fields = {
            "Username:", usernameField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Customer Login", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            int customerId = controller.loginCustomerAndReturnId(username, password);

            if (customerId != -1) {
                JOptionPane.showMessageDialog(this, "✅ Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                new CustomerDashboard(viewCarsView, rentCarView, addCarView, controller, customerId).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Login failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showCustomerRegisterDialog() {
        JTextField nameField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        String[] licenseOptions = {"Yes", "No"};
        JComboBox<String> licenseComboBox = new JComboBox<>(licenseOptions);
    
        Object[] fields = {
            "Full Name:", nameField,
            "Username:", usernameField,
            "Password:", passwordField,
            "Email:", emailField,
            "Phone:", phoneField,
            "Do you have a valid driving license?", licenseComboBox
        };
    
        int option = JOptionPane.showConfirmDialog(this, fields, "Customer Registration", JOptionPane.OK_CANCEL_OPTION);
    
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String hasLicense = (String) licenseComboBox.getSelectedItem(); // "Yes" or "No"
    
            if (name.isEmpty() || username.isEmpty() || password.isEmpty() ||
                email.isEmpty() || phone.isEmpty() || hasLicense == null) {
                JOptionPane.showMessageDialog(this, "❌ All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            boolean success = controller.registerCustomer(username, password, name, email, phone, hasLicense);
            if (success) {
                JOptionPane.showMessageDialog(this, "✅ Registration successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "❌ Registration failed. Username might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showAdminLoginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] fields = {
            "Admin Username:", usernameField,
            "Admin Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Admin Login", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            boolean success = controller.loginAdmin(usernameField.getText(), new String(passwordField.getPassword()));
            if (success) {
                JOptionPane.showMessageDialog(this, "✅ Admin login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                new AdminDashboard(addCarView, viewCarsView, rentCarView, deleteCarView, controller).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Admin login failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
