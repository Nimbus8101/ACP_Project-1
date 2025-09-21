
/**
 * Vehicle.java
 * A class representing a vehicle with attributes such as make, size, weight, and engine size.
 * 
 * Author: Elijah Reyna
 * Date: 09/20/2025
 */
public class Vehicle {
    public enum Make { CHEVY, FORD, TOYOTA, NISSAN, HYUNDAI }
    public enum Size { COMPACT, INTERMEDIATE, FULL_SIZE }

    public Make make;
    public Size size;
    public double weight;
    public double engineSize;

    // Default constructor creates a random vehicle
    public Vehicle(){
        VehicleFactory.createRandomVehicle();
    }

    // Parameterized constructor
    public Vehicle(Make make, Size size, double weight, double engineSize) {
        this.make = make;
        this.size = size;
        this.weight = weight;
        this.engineSize = engineSize;
    }
}
