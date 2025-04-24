package Controller;

import Model.UserModel;

public class AuthController {
    private final UserModel userModel;

    public AuthController(UserModel userModel) {
        this.userModel = userModel;
    }

    public boolean registerCustomer(String username, String password, String name, String email, String phone, String license) {
        return userModel.registerCustomer(username, password, name, email, phone, license);
    }

    public int loginCustomerAndReturnId(String username, String password) {
        return userModel.validateCustomerLoginAndGetId(username, password);
    }

    public boolean loginAdmin(String username, String password) {
        return userModel.loginAdmin(username, password);
    }
    
    public boolean updateVehicleCount(int vehicleId, int newCount) {
        return userModel.updateVehicleCount(vehicleId, newCount);
    }
    
}
