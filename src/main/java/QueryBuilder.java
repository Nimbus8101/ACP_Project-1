

/**
 * QueryBuilder.java
 * Utility class for building SQL queries.
 * 
 * Functionality:
 * - Supports building queries for SELECT, INSERT, DELETE, CREATE TABLE, and DROP TABLE operations.
 *   - Takes in table name, columns, values, and conditions (wherever they apply) as parameters, and constructs the appropriate SQL query string.
 * - Includes basic SQL injection prevention.
 * 
 * @author Elijah Reyna
 * Last Edited 9/21/25
 */
public class QueryBuilder{
    /**
     * A quick check to prevent injection attacks
    */
    public static boolean sqlSafe(String input){
        return input != null && !input.trim().isEmpty() && !input.matches(".*[;\"]+.*");
    }


    /**
     * Builds a Delete SQL query.
     * @param tableName The name of the table to delete from.
     * @param whereClause The WHERE clause (without the "WHERE" keyword).
     * @return The constructed SQL DELETE query.
     */
    public static String buildDeleteQuery(String tableName, String whereClause){
        if(!sqlSafe(tableName)){
            throw new IllegalArgumentException("Unsafe SQL input detected in the Table Name");
        }

        if(whereClause.trim().isEmpty()){
            return "DELETE FROM " + tableName;
        }

        if(!sqlSafe(whereClause)){
            throw new IllegalArgumentException("Unsafe SQL input detected in the WHERE clause");
        }

        return "DELETE FROM " + tableName + " WHERE " + whereClause;
    }


    /**
     * Builds a Create Table SQL query.
     * @param tableName The name of the table to create.
     * @param columns The column definitions (e.g., "id INT, name VARCHAR(100)").
     * @return The constructed SQL CREATE TABLE query.
     */
    public static String buildCreateTableQuery(String tableName, String columns){
        if(!sqlSafe(tableName) || !sqlSafe(columns)){
            throw new IllegalArgumentException("Unsafe SQL input detected");
        }
        return "CREATE TABLE " + tableName + " (" + columns + ")";
    }


    public static String buildDropTableQuery(String tableName){
        if(!sqlSafe(tableName)){
            throw new IllegalArgumentException("Unsafe SQL input detected in the Table Name");
        }
        return "DROP TABLE " + tableName;
    }


    /**
     * Builds a Select SQL query.
     * @param tableName The name of the table to select from.
     * @param columns The columns to select (comma-separated).
     * @param whereClause The WHERE clause (without the "WHERE" keyword).
     * @return The constructed SQL SELECT query.
     */
    public static String buildSelectQuery(String tableName, String columns, String whereClause){
        if(!sqlSafe(tableName) || !sqlSafe(columns)){
            throw new IllegalArgumentException("Unsafe SQL input detected in the Table Name or Columns");
        }

        if (whereClause.trim().isEmpty()) {
            return "SELECT " + columns + " FROM " + tableName;
        }

        if(!sqlSafe(whereClause)){
            throw new IllegalArgumentException("Unsafe SQL input detected in the WHERE clause");
        }

        return "SELECT " + columns + " FROM " + tableName + " WHERE " + whereClause;
    }


    /**
     * Builds an Insert SQL query.
     * @param tableName The name of the table to insert into.
     * @param columns The columns to insert into (comma-separated).
     * @param values The values to insert (comma-separated).
     * @return The constructed SQL INSERT query.
     */
    public static String buildInsertQuery(String tableName, String columns, String values){
        if(!sqlSafe(tableName) || !sqlSafe(columns) || !sqlSafe(values)){
            throw new IllegalArgumentException("Unsafe SQL input detected");
        }
        return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
    }
}