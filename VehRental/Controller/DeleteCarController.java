// DeleteCarController.java (inside Controller package)
package Controller;

import Model.CarModel;

public class DeleteCarController {
    private final CarModel model;

    public DeleteCarController(CarModel model) {
        this.model = model;
    }

    public boolean deleteCar(int vehicleId) {
        return model.deleteCarById(vehicleId);
    }
}
