package View;

import Controller.ViewCarsController;
import Model.Car;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewCarsView extends JFrame {
    private final ViewCarsController controller;
    private JTable table;
    private DefaultTableModel model;

    public ViewCarsView(ViewCarsController controller) {
        this.controller = controller;
        setTitle("🚗 All Available Vehicles");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        String[] columnNames = {"ID", "Model", "Category", "Fuel Type", "Reg. Number", "Rent/Day", "Available"};
        model = new DefaultTableModel(columnNames, 0);

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(24);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        refreshTable(); // Initial load
    }

    public void refreshTable() {
        List<Car> cars = controller.getAllCars();

        model.setRowCount(0); // Clear existing data

        for (Car car : cars) {
            Object[] rowData = {
                    car.getId(),
                    car.getModelName(),
                    car.getCategory(),
                    car.getFuelType(),
                    car.getRegistrationNumber(),
                    "₹" + car.getRentPerDay(),
                    car.getAvailableCount()
            };
            model.addRow(rowData);
        }
    }

    public void display() {
        setVisible(true);
        refreshTable(); // Optional: ensures latest data every time it's reopened
    }
}
