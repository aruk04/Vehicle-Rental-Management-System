package Controller;

import Model.Car;
import Model.CarModel;

import java.util.List;

public class ViewCarsController {
    private final CarModel model;

    public ViewCarsController(CarModel model) {
        this.model = model;
    }

    public List<Car> getAllCars() {
        return model.getAllCars();
    }
}