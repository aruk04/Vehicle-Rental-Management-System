package View;

import Controller.DeleteCarController;

import javax.swing.*;
import java.awt.*;

public class DeleteCarView extends JFrame {
    private final DeleteCarController controller;

    public DeleteCarView(DeleteCarController controller) {
        this.controller = controller;

        setTitle("🗑️ Delete Vehicle");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#f4f6f8"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel label = new JLabel("Enter Vehicle ID to Delete:");
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JTextField idField = new JTextField(10);
        JButton deleteBtn = new JButton("Delete");

        deleteBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        deleteBtn.setBackground(Color.RED);
        deleteBtn.setForeground(Color.WHITE);

        deleteBtn.addActionListener(e -> {
            String input = idField.getText();
            try {
                int vehicleId = Integer.parseInt(input);
                boolean success = controller.deleteCar(vehicleId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "✅ Vehicle ID " + vehicleId + " deleted successfully!");
                    idField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Vehicle ID not found or couldn't be deleted.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "⚠️ Please enter a valid numeric Vehicle ID.");
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(deleteBtn, gbc);

        add(panel);
    }
}
