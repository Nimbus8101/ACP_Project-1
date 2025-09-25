

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
   Database.java
   Main class for managing database operations.
   - Initializes the database connection.
   - Executes SQL commands.
   - Closes database connections.
   - Logs database operations.

   @author Elijah Reyna
   Last Edited 9/21/25
*/
public class Database{
   private Connection conn;
   private static Statement stat;
   DatabaseLog dbLog = new DatabaseLog();

   /**
    * Default constructor that initializes the database with a default configuration file.
    */
   public Database(){
      this("database.properties");
   }

   /**
    * Constructor that initializes the database with a specified configuration file.
    * @param filename The name of the configuration file.
    */
   public Database(String filename){
      try {
         initializeDataSource(filename);
      } catch (ClassNotFoundException | SQLException | IOException e) {
         System.out.println("There was an issue initializing the database");
         e.printStackTrace();
      }
      
      System.out.println("Database initialized successfully");
   }


   /**
    * Initializes the data source using the provided configuration file.
    * @param filename The name of the configuration file.
    * @throws SQLException 
    * @throws IOException 
    * @throws ClassNotFoundException 
    */
   private void initializeDataSource(String filename) throws SQLException, ClassNotFoundException, IOException{
      System.out.print("Initializing database with " + filename + "...   ");

      SimpleDataSource.init(filename);
      conn = SimpleDataSource.getConnection();
      stat = conn.createStatement();
   }


   /**
    * Executes a SQL command.
    * @param command The SQL command to execute.
    * @return The ResultSet of the executed command, or null if not applicable.
    */
   private ResultSet executeCommand(String command){
   ResultSet result = null;
   dbLog.log("Executing command: " + command);
   try {
      String cmdType = command.trim().split("\\s+")[0].toUpperCase();

      if (cmdType.equals("SELECT")) {
         result = stat.executeQuery(command);
         dbLog.log("Command executed successfully (query)\n");
      } else {
         int count = stat.executeUpdate(command);
         dbLog.log("Command executed successfully (update), affected rows: " + count + "\n");
      }
   } catch (SQLException e) {
      dbLog.log("There was an issue executing the command\n");
      e.printStackTrace();
   }

   return result;
}


   /**
    * Closes the database connections.
    */
   public void closeConnections(){
      try {
         if (stat != null) stat.close();
         if (conn != null) conn.close();
      } catch (SQLException e) {
         System.out.println("There was an issue closing the database resources");
         e.printStackTrace();
      }
   }



//================== Main Function ===================//

   public static void main(String[] args) throws Exception{  
	   System.out.println("Program Start");

      // Create Vehicle instances and write them to "Vehicles.csv"
      System.out.println("Creating 10 random vehicles and saving them to Vehicles.csv");
      Vehicle[] vehicles = new Vehicle[10];

      for (int i = 0; i < vehicles.length; i++) {
         vehicles[i] = VehicleFactory.createRandomVehicle();  // Generates a random vehicle
      }

      ClassSaver.saveObjects(vehicles);


      // Initialize the database connection
      Database myDatabase = new Database("database.properties");
      System.out.println("Connected to database successfully\n");


      // Create a SQL command to create the database table, then issue that command
      Class<?> cls = vehicles[0].getClass();
      String command = QueryBuilder.buildCreateTableQuery("Vehicles", DatabaseUtils.buildColumnInfo(cls));
      myDatabase.executeCommand(command);


      // Read the 10 vehicles from "Vehicles.csv" and insert them into the database
      List<Vehicle> loadedVehicles = ObjectLoader.loadObjectsFromFile(new File("Vehicles.csv"), Vehicle.class);

      for (Vehicle v : loadedVehicles) {
         String tableName = v.getClass().getSimpleName() + "s";
         String columnInfo = DatabaseUtils.buildColumnNames(v.getClass());
         String values = "";
         for (Field field : v.getClass().getDeclaredFields()) {
            values += DatabaseUtils.convertFieldToSQL(field, v) + ", ";
         }

         // Remove the trailing comma and space
            if (values.length() > 0) {
               values = values.substring(0, values.length() - 2);
            }

         command = QueryBuilder.buildInsertQuery(tableName, columnInfo, values);
         myDatabase.executeCommand(command);
      }

      // Query the database to retrieve and print all vehicles
      command = QueryBuilder.buildSelectQuery("Vehicles", "*", "");
      ResultSet result = myDatabase.executeCommand(command);
      System.out.println("Vehicles in database:\n" + DatabaseUtils.printResultSet(result));


      // Query the database to retrieve and print all Chevys and Toyotas
      command = QueryBuilder.buildSelectQuery("Vehicles", "*", "Make = 'CHEVY' OR Make = 'TOYOTA'");
      result = stat.executeQuery(command);
      System.out.println("Chevys and Toyotas in database:\n" + DatabaseUtils.printResultSet(result));


      // Query the database to retrieve and print all vehicles weighing more than 2500 pounds
      command = QueryBuilder.buildSelectQuery("Vehicles", "*", "Weight > 2500");
      result = stat.executeQuery(command);
      System.out.println("Vehicles weighing more than 2500 pounds in database:\n" + DatabaseUtils.printResultSet(result));


      // Drop the Vehicles table and close the database connection
      try {
            command = QueryBuilder.buildDropTableQuery("Vehicles");
            myDatabase.executeCommand(command);
         } catch (Exception e) {
            System.out.println("Table Vehicles did not exist, so it could not be dropped.");
      }

      // Read and display the log file
      myDatabase.dbLog.displayLog();

      myDatabase.closeConnections();
      myDatabase.dbLog.close();
      System.out.println("Dropped Table Vehicles, closed connection and ending program");
   }
}

