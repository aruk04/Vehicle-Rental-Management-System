package View;

import Controller.AuthController;

import javax.swing.*;
import java.awt.*;

public class UpdateVehicleCountView {

    public static void showDialog(Component parent, AuthController controller) {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField vehicleIdField = new JTextField();
        JTextField newCountField = new JTextField();

        panel.add(new JLabel("Vehicle ID:"));
        panel.add(vehicleIdField);
        panel.add(new JLabel("New Quantity:"));
        panel.add(newCountField);

        int result = JOptionPane.showConfirmDialog(parent, panel, "Update Vehicle Quantity",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int vehicleId = Integer.parseInt(vehicleIdField.getText().trim());
                int newCount = Integer.parseInt(newCountField.getText().trim());

                if (newCount < 0) {
                    JOptionPane.showMessageDialog(parent, "❌ Quantity can't be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = controller.updateVehicleCount(vehicleId, newCount);
                if (success) {
                    JOptionPane.showMessageDialog(parent, "✅ Vehicle quantity updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(parent, "❌ Failed to update quantity. Check Vehicle ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parent, "❌ Please enter valid numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
