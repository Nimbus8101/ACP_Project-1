

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;


/*
 * ClassSaver.java
 * Utility class for saving objects of any class to a CSV file.
 * 
 * Process:
 * - Open a CSV file for writing.
 * - Write column headers based on the fields of the class.
 * - Save objects to the CSV file.
 * - Convert objects to CSV string format.
 * - Close the CSV file.
 * 
 * @author Elijah Reyna
 * Last Edited 9/21/25
 */
public class ClassSaver<T> {
    public static File file;
    public static FileWriter writer;

    /**
     * Saves an array of objects to a CSV file.
     * This is the main method to call for saving objects.
     * 
     * @param objects The array of objects to save.
     * @throws IOException If an I/O error occurs.
     */
    public static <T> void saveObjects(T[] objects) throws IOException {
        // Check if the array is empty or null
        if (objects == null || objects.length == 0) {
            System.out.println("No objects to save.");
            return;
        }

        // Open the CSV file for writing (pluralizes it)
        openCSVFile(objects[0].getClass().getSimpleName() + "s.csv");

        // Write column headers based on the fields of the class
        writeColumns(objects[0].getClass());

        // Save each object to the CSV file
        for (T object : objects) {
            saveObject(object);
        }

        // Close the CSV file
        closeCSVFile();
    }


    /*
     * Opens a CSV file for writing.
     * @param filePath The path to the CSV file.
     */
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

            // Opens the file
            writer = new FileWriter(file, appendMode);
            System.out.println("Opened file: " + filePath);

        } catch (IOException e) {
            System.out.println("Error opening file for writing: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Writes the column headers to the CSV file based on the fields of the class.
     * @param objectClass The class of the objects being saved.
     */
    public static void writeColumns(Class<?> objectClass){
        StringBuilder columns = new StringBuilder();
        try {
            // Append each field name as a column header
            for (Field field : objectClass.getDeclaredFields()) {
                columns.append(field.getName()).append(",");
            }

            // Remove the trailing comma
            if (columns.length() > 0) {
                columns.setLength(columns.length() - 1);
            }

            // Write the column headers to the file
            writer.write(columns.toString() + "\n");

        } catch (IOException e) {
            System.out.println("Error writing columns to file: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Saves a single object to the CSV file.
     * @param <T> The type of the object.
     * @param object The object to save.
     */
    public static <T> void saveObject(T object) {
        try {
            writer.write(convertObjectToCSVString(object) + "\n");
            System.out.println("   Saving object: " + convertObjectToCSVString(object));

        } catch (IOException e) {
            System.out.println("Error writing object data to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Converts an object to a CSV string format.
     * @param <T> The type of the object.
     * @param object The object to convert.
     * @return The CSV string representation of the object.
     */
    public static <T> String convertObjectToCSVString(T object) {
        StringBuilder sb = new StringBuilder();
        try {
            // Append each field value as a CSV value
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(object);
                sb.append(value != null ? value.toString() : "").append(",");
            }

            // Remove the trailing comma
            if (sb.length() > 0) {
                sb.setLength(sb.length() - 1);
            }
        
        } catch (IllegalAccessException e) {
            System.out.println("Error converting object to CSV string: " + e.getMessage());
            e.printStackTrace();
        }

        return sb.toString();
    }


    /**
     * Closes the CSV file.
     */
    public static void closeCSVFile() {
        try {
            if (writer != null) {
                writer.close();
                System.out.println("Closed vehicles.csv file successfully.\n");
            }
        } catch (IOException e) {
            System.out.println("Error closing vehicles.csv file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
