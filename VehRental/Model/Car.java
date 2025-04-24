package Model;

public class Car {
    private int id;
    private String modelName;
    private String category; // 2W or 4W
    private String fuelType; // Petrol, Diesel, EV
    private String registrationNumber;
    private double rentPerDay;
    private int availableCount;
    private String brand;

    // Constructor without ID (since it's auto-generated) and with brand
    public Car(String modelName, String category, String fuelType,
               String registrationNumber, double rentPerDay,
               int availableCount, String brand) {
        this.modelName = modelName;
        this.category = category;
        this.fuelType = fuelType;
        this.registrationNumber = registrationNumber;
        this.rentPerDay = rentPerDay;
        this.availableCount = availableCount;
        this.brand = brand;
    }

    // Optional: Constructor with ID (if needed later)
    public Car(int id, String modelName, String category, String fuelType,
               String registrationNumber, double rentPerDay,
               int availableCount, String brand) {
        this.id = id;
        this.modelName = modelName;
        this.category = category;
        this.fuelType = fuelType;
        this.registrationNumber = registrationNumber;
        this.rentPerDay = rentPerDay;
        this.availableCount = availableCount;
        this.brand = brand;
    }

    // Getters
    public int getId() { return id; }
    public String getModelName() { return modelName; }
    public String getCategory() { return category; }
    public String getFuelType() { return fuelType; }
    public String getRegistrationNumber() { return registrationNumber; }
    public double getRentPerDay() { return rentPerDay; }
    public int getAvailableCount() { return availableCount; }
    public String getBrand() { return brand; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setModelName(String modelName) { this.modelName = modelName; }
    public void setCategory(String category) { this.category = category; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
    public void setRentPerDay(double rentPerDay) { this.rentPerDay = rentPerDay; }
    public void setAvailableCount(int availableCount) { this.availableCount = availableCount; }
    public void setBrand(String brand) { this.brand = brand; }
}
