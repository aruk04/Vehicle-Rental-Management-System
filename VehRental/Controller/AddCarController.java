package Controller;

import Model.Car;
import Model.CarModel;

public class AddCarController {
    private final CarModel carModel;

    public AddCarController(CarModel carModel) {
        this.carModel = carModel;
    }

    public void addCar(Car car) {
        carModel.addCar(car);
    }
}