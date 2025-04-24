# 🚗 Vehicle Rental Management System – Java Desktop App

A Java Swing-based desktop application for managing car rentals, demonstrating behavioral design patterns like **Observer** and **Command**, and following solid architectural principles.

---

## 📌 Problem Statement
To develop a comprehensive Vehicle Rental Management System that efficiently manages the rental process for various types of vehicles, including cars and two-wheelers. The system should allow administrators to manage vehicle inventory and enable customers to browse and rent vehicles for specific time periods.

---

## 🌟 Key Features
1. User authentication with separate admin and customer roles  
2. Vehicle inventory management (add, view, update count, delete)  
3. Vehicle rental processing with date selection  
4. Automatic payment calculation based on rental duration  
5. User-friendly GUI interface built with Java Swing  
6. Database integration for persistent data storage  

---

## 🏗️ Architectural Patterns

### 🎯 Model-View-Controller Pattern (MVC)

**Model**
- `Car.java`: Represents vehicle information  
- `CarModel.java`: Handles database operations for vehicles  
- `UserModel.java`: Manages user authentication and account operations  
- `RentalModel.java`: Processes rental transactions  
- `PaymentService.java`: Handles payment calculations  

**View**
- `AddCarView.java`: Interface for adding new vehicles  
- `AuthGUI.java`: Login and registration screens  
- `AdminDashboard.java`: Main interface for administrators  
- `CustomerDashboard.java`: Main interface for customers  
- `DeleteCarView.java`: Interface for removing vehicles  
- `RentCarView.java`: Interface for processing rentals  
- `UpdateVehicleCountView.java`: Interface for updating vehicle inventory  
- `ViewCarsView.java`: Display of available vehicles  

**Controller**
- `AddCarController.java`: Handles adding vehicles to inventory  
- `AuthController.java`: Manages authentication operations  
- `DeleteCarController.java`: Controls vehicle removal  
- `RentalController.java`: Processes rental requests  
- `ViewCarsController.java`: Retrieves vehicle information for display  

---

## 🧱 Design Principles

### ✅ Single Responsibility Principle (SRP)
Each class has a single responsibility, improving maintainability.

### ✅ Open/Closed Principle (OCP)
Easily extendable to support new vehicle types or features without modifying existing code.

### ✅ Dependency Inversion Principle (DIP)
Controllers interact with abstractions, promoting flexibility and testability.

### ✅ Interface Segregation Principle (ISP)
Controllers are modular and focused on specific tasks.

---

## 🧩 Design Patterns

### 🏭 Factory Method Pattern
Used in `Main.java` to initialize controllers and views.

### 🧍 Singleton Pattern (Implied)
Reuses database connection within models to avoid duplicate objects.

### 🧰 Facade Pattern
Controllers act as simplified interfaces to backend logic.

---

## 🧠 Behavioral Design Patterns

### 🔁 Observer Pattern (Partial Implementation)  
**File:** `ViewCarsView.java`  
The `refreshTable()` method dynamically updates the UI to reflect changes in the data model.

```java
public void refreshTable() {
    List<Car> cars = controller.getAllCars();
    model.setRowCount(0); // Clear existing data
    for (Car car : cars) {
        // Add rows to table
    }
}
```

Although not a formal Observer implementation, it mimics the pattern by manually refreshing the UI based on model updates.

---

### 🕹️ Command Pattern (Partial Implementation)  
**File:** `AddCarView.java`  
The action performed when the submit button is clicked acts as a command.

```java
submitBtn.addActionListener(e -> {
    try {
        String category = categoryField.getSelectedItem().toString();
        // [other properties...]
        Car car = new Car(model, category, fuelType, regNumber, rent, available, brand.isEmpty());
        controller.addCar(car);
        JOptionPane.showMessageDialog(this, "✅ Vehicle added successfully!");
        dispose();
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "❌ Invalid input. Please check numeric fields.");
    }
});
```

---

## 🛠️ Instructions to Run the Project

### ✅ Prerequisites
- Java Development Kit (JDK 8 or above)  
- MySQL database  
- Java IDE (e.g., IntelliJ IDEA, Eclipse)

---

### 🔧 Setup Steps

1. **Clone or Download the Repository**
```bash
git clone https://github.com/aruk04/Vehicle-Rental-Management-System.git
```
Or download and extract the `.zip` file.

---

2. **Set Up the Database**  
- Create a MySQL database (e.g., `vehiclerentaldb`)  
- Import the provided SQL schema and sample data if available  
- Update the database connection credentials in `DBConnection.java`:

```java
String url = "jdbc:mysql://localhost:3306/vehiclerentaldb";
String user = "your_mysql_username";
String password = "your_mysql_password";
```

---

3. **Compile the Project**
- Open the project in your Java IDE  
- Make sure all `.java` files are added to your source directory  
- Or compile via terminal:

```bash
javac -d bin src/**/*.java
```

---

4. **Run the Application**
- Run `Main.java` from your IDE  
- Or execute via terminal:

```bash
java -cp bin Main
```

---

5. **Login/Register**
- Use the GUI to log in or register as an admin or customer  
- Start managing or renting vehicles!

---

## 📝 Notes
- ✅ Ensure MySQL server is running before launching the app  
- 💻 Java Swing is used for GUI, so the app will launch as a desktop window  
