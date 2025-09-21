

/**
 * VehicleFactory.java
 * A factory interface for creating Vehicle objects with random or specified attributes.
 * 
 * Functionality:
 * - Provides a method to create a random vehicle with random make, size, weight, and engine size.
 * - Provides a method to create a vehicle with specified make, size, weight, and engine size.
 * 
 * Author: Elijah Reyna
 * Date: 09/20/2025
 */
public interface VehicleFactory {
    /**
     * Creates a random vehicle
     * @return A Vehicle object with random make, size, weight, and engine size.
     */
    public static Vehicle createRandomVehicle(){
        // Select a random make
        String[] makes = {"CHEVY", "FORD", "TOYOTA", "NISSAN", "HYUNDAI"};
        String make = makes[(int)(Math.random() * makes.length)];

        // Select a random size
        String[] sizes = {"COMPACT", "INTERMEDIATE", "FULL_SIZE"};
        String size = sizes[(int)(Math.random() * sizes.length)];

        // Generate random weight and engine size based on size
        int lowerWeight;
        int upperWeight;
        switch(size){
            case "COMPACT":
                lowerWeight = 1500;
                upperWeight = 2000;
                break;
            case "INTERMEDIATE":
                lowerWeight = 2000;
                upperWeight = 2500;
                break;
            case "FULL_SIZE":
                lowerWeight = 2500;
                upperWeight = 4500;
                break;
            default:
                lowerWeight = 1500;
                upperWeight = 4500;
        }

        double weight = lowerWeight + Math.random() * (upperWeight - lowerWeight);

        // Generate random engine size between 100 and 200
        double engineSize = 100 + Math.random() * 100;

        return createVehicle(make, size, weight, engineSize);
    }

    /**
     * Creates a vehicle with the specified attributes.
     * @param make
     * @param size
     * @param weight
     * @param engineSize
     * @return
     */
    public static Vehicle createVehicle(String make, String size, double weight, double engineSize){
        Vehicle.Make vehicleMake;
        Vehicle.Size vehicleSize;

        switch(make.toUpperCase()){
            case "CHEVY":
                vehicleMake = Vehicle.Make.CHEVY;
                break;
            case "FORD":
                vehicleMake = Vehicle.Make.FORD;
                break;
            case "TOYOTA":
                vehicleMake = Vehicle.Make.TOYOTA;
                break;
            case "NISSAN":
                vehicleMake = Vehicle.Make.NISSAN;
                break;
            case "HYUNDAI":
                vehicleMake = Vehicle.Make.HYUNDAI;
                break;
            default:
                throw new IllegalArgumentException("Invalid make: " + make);
        }

        switch(size.toUpperCase()){
            case "COMPACT":
                vehicleSize = Vehicle.Size.COMPACT;
                break;
            case "INTERMEDIATE":
                vehicleSize = Vehicle.Size.INTERMEDIATE;
                break;
            case "FULL_SIZE":
                vehicleSize = Vehicle.Size.FULL_SIZE;
                break;
            default:
                throw new IllegalArgumentException("Invalid size: " + size);
        }

        return new Vehicle(vehicleMake, vehicleSize, weight, engineSize);
    }
}
