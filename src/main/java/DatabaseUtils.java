import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * DatabaseUtils.java
 * Utility class for database-related operations.
 * 
 * Functionality:
 * - Build SQL column definitions from class fields.
 * - Print contents of a ResultSet.
 * - Find the length of the longest constant in an enum.
 * - Map Java types to SQL types.
 * 
 * @author Elijah Reyna
 * Last Edited 9/21/25
 */
public class DatabaseUtils {

    /**
     * Builds a SQL column definition string based on the fields of a given class.
     * @param cls The class to build the column info for.
     * @return The SQL column definition string.
     */
    public static String buildColumnInfo(Class<?> cls){
        StringBuilder sb = new StringBuilder();
        for (Field field : cls.getDeclaredFields()) {
            sb.append(field.getName() + " " + DatabaseUtils.getSQLType(field.getType()) + ", ");
        }

        // Remove the trailing comma and space
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }


    /**
    * Maps Java types to SQL types.
    * @param type The Java type to map.
    * @return The corresponding SQL type as a string. Used in buildColumnInfo, to then be used in query building.
    * @throws UnsupportedOperationException if the type is not supported.
    */
    public static String getSQLType(Class<?> type) {
        if(type.isEnum()){
            // Finds the longest enum constant name to determine the CHAR length
            Object[] constants = type.getEnumConstants();
            int maxConstantLength = DatabaseUtils.findLongestConstantLength(constants);
            return "VARCHAR(" + maxConstantLength + ")";
        }
        else if (type == String.class) {
            return "VARCHAR(" + 255 + ")";
        } else if (type == int.class || type == Integer.class) {
            return "INTEGER";
        } else if (type == boolean.class || type == Boolean.class) {
            return "BOOLEAN";
        } else if (type == double.class || type == Double.class) {
            return "DOUBLE";
        } else {
            throw new UnsupportedOperationException("Unimplemented type in 'getSQLType': " + type.getName());
        }
    }

    public static <T> String convertFieldToSQL(Field field, T obj) {
        try {
            field.setAccessible(true);

            Object value = field.get(obj);
            if (value instanceof String) {
                return "'" + value + "'";
            } else if(value instanceof Enum) {
                return "'" + value + "'";
            }else {
                return value.toString();
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access field: " + field.getName(), e);
        }
    }


    /**
     * Builds a comma-separated list of column names based on the fields of a given class.
     * @param cls The class to build the column names for.
     * @return The comma-separated list of column names.
     */
    public static String buildColumnNames(Class<?> cls){
        StringBuilder sb = new StringBuilder();
        for (Field field : cls.getDeclaredFields()) {
            sb.append(field.getName() + ", ");
        }

        // Remove the trailing comma and space
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }


    /**
        * Prints the contents of a ResultSet.
        * @param resultSet The ResultSet to print.
        * @return A string representation of the ResultSet contents.
        */
    public static String printResultSet(ResultSet resultSet){
        StringBuilder sb = new StringBuilder();
        try {
            ResultSetMetaData rsm = resultSet.getMetaData();
            int cols = rsm.getColumnCount();

            while(resultSet.next()){
                for(int i = 1; i <= cols; i++){
                sb.append(resultSet.getString(i)).append(" ");
                }
                sb.append("\n");
            }
        } catch (SQLException e) {
            System.out.println("There was an issue printing the result set");
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * Finds the length of the longest constant in an enum.
     * @param constants The enum constants to check.
     * @return The length of the longest constant.
     */
    public static int findLongestConstantLength(Object[] constants) {
        int maxConstantLength = 10;

        for (Object constant : constants) {
            int length = constant.toString().length();
            if (length > maxConstantLength) {
                maxConstantLength = length;
            }
        }
        return maxConstantLength;
    }
}
