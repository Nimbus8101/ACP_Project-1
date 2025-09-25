import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * ObjectLoader.java
 * Utility class for loading objects from a CSV file.
 * Functionality:
 * - Load objects of a specified class from a CSV file.
 * 
 * @author Elijah Reyna
 * Last Edited 9/25/25
 */
public class ObjectLoader {
    /**
     * Loads objects of a specified class from a CSV file.
     * @param file The CSV file to load objects from.
     * @param cls The class of the objects to load.
     * @return A list of loaded objects.
     */
    public static <T> List<T> loadObjectsFromFile(File file, Class<T> cls) {
        List<T> objects = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine(); // Skip the header line

            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                // Create a new instance of the class
                T obj = cls.getDeclaredConstructor().newInstance();

                // Set each field of the object to the corresponding value from the line in the csv
                String[] values = line.split(",");
                Field[] fields = cls.getDeclaredFields();
                for (int i = 0; i < fields.length && i < values.length; i++) {
                    fields[i].setAccessible(true);
                    fields[i].set(obj, convertSQLToValue(fields[i].getType(), values[i]));
                }

                // Add the object to the list
                objects.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error loading objects from file: " + e.getMessage());
            e.printStackTrace();
        }
        return objects;
    }

    /**
     * Converts a SQL string value to the appropriate Java type based on the field's type.
     * @param type The type of the field.
     * @param string The SQL string value.
     * @return The converted value.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static Object convertSQLToValue(Class<?> type, String string) {
        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(string);
        } else if (type == double.class || type == Double.class) {
            return Double.parseDouble(string);
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(string);
        } else if (type == String.class) {
            return string;
        } else if (type.isEnum()) {
            return Enum.valueOf((Class<Enum>) type.asSubclass(Enum.class), string.trim());
        } else {
            throw new UnsupportedOperationException("Unimplemented type in 'convertSQLToValue': " + type.getName());
        }
    }
}
