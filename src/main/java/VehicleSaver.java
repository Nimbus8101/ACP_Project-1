
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *  VehicleSaver.java
 *  Utility class for saving Vehicle objects to a CSV file.
 *  
 *  NOTE: This class is not used in the database, but it is included to show the original implementation for saving vehicles to a CSV file.
 *  Because we were using reflection to create the database table and insert data, I rewrote this class to handle objects of any class type.
 *  The ClassSaver.java file is the class used in the Database.java file.
 *  
 *  Functionality:
 *  - Opens a CSV file for writing.
 *  - Writes column headers to the CSV file.
 *  - Saves individual Vehicle objects to the CSV file.
 *  - Closes the CSV file.
 *  
 *  @author Elijah Reyna
 *  Last Edited 9/21/25
 */
public class VehicleSaver {
    public static File file;
    public static FileWriter writer;

    public static void saveVehicles(Vehicle[] vehicles) throws IOException {
        openCSVFile("Vehicles.csv");
        writeColumns();
        
        for (Vehicle vehicle : vehicles) {
            saveVehicle(vehicle);
        }

        closeCSVFile();
    }

    public static void openCSVFile(String filePath) {
        // Open the file
        file = new File(filePath);
        try {
            // Create the file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
            }

            // If the file already exists
            boolean appendMode = file.length() == 0;

            writer = new FileWriter(file, appendMode);
            System.out.println("Opened file: " + filePath);
        } catch (IOException e) {
            System.out.println("Error opening file for writing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void writeColumns(){
        try {
            writer.write("Make,Size,Weight,EngineSize\n");
        } catch (IOException e) {
            System.out.println("Error writing columns to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void saveVehicle(Vehicle vehicle) {
        String vehicleData = convertVehicleToCSVString(vehicle);
        try {
            writer.write(vehicleData + "\n");
        } catch (IOException e) {
            System.out.println("Error writing vehicle data to file: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Saving vehicle: " + vehicleData);
    }

    public static String convertVehicleToCSVString(Vehicle vehicle) {
        return vehicle.make + "," + vehicle.size + "," + vehicle.weight + "," + vehicle.engineSize;
    }

    public static void closeCSVFile() {
        try {
            if (writer != null) {
                writer.close();
                System.out.println("Closed file successfully.");
            }
        } catch (IOException e) {
            System.out.println("Error closing file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
