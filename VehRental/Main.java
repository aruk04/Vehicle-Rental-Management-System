import Controller.*;
import Model.*;
import View.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Models
            CarModel carModel = new CarModel();
            UserModel userModel = new UserModel();

            // Controllers
            AddCarController addCarController = new AddCarController(carModel);
            ViewCarsController viewCarsController = new ViewCarsController(carModel);
            AuthController authController = new AuthController(userModel);
            RentalController rentalController = new RentalController();
            DeleteCarController deleteCarController = new DeleteCarController(carModel); // Add DeleteCarController

            // GUI Views
            AddCarView addCarView = new AddCarView(addCarController);
            ViewCarsView viewCarsView = new ViewCarsView(viewCarsController);
            RentCarView rentCarView = new RentCarView(rentalController);
            DeleteCarView deleteCarView = new DeleteCarView(deleteCarController); // Add DeleteCarView

            // Launch the GUI Auth window with all dependencies
            new AuthGUI(authController, addCarView, viewCarsView, rentCarView, deleteCarView).setVisible(true);
        });
    }
}
