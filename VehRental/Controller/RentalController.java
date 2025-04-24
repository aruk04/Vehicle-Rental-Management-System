package Controller;

import Model.RentalModel;

import java.time.LocalDate;

public class RentalController {
    private final RentalModel rentalModel;

    public RentalController() {
        this.rentalModel = new RentalModel();
    }

    public boolean rentVehicle(int customerId, int vehicleId, LocalDate rentDate, LocalDate returnDate) {
        return rentalModel.rentVehicle(customerId, vehicleId, rentDate, returnDate);
    }

    public double calculateRentalAmount(int vehicleId, int numDays) {
        return rentalModel.getRentalAmount(vehicleId, numDays);
    }
}
