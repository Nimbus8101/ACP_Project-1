

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * DatabaseLog.java
 * Utility class for logging database operations to a file.
 * 
 * Functionality:
 * - Log messages to a file.
 * 
 * @author Elijah Reyna
 * Last Edited 9/21/25
 */
public class DatabaseLog {
    private StringBuilder log;
    private static File logFile = new File("database_log.txt");
    private static FileWriter writer;

    /**
     * Default constructor that initializes the log file.
     */
    public DatabaseLog() {
        log = new StringBuilder();
        try {
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            writer = new FileWriter(logFile, false);
        } catch (IOException e) {
            System.out.println("Error initializing log file: " + e.getMessage());
        }
    }


    /**
     * Logs a message to the log file.
     * @param message
     */
    public void log(String message) {
        log.append(message).append(System.lineSeparator());
        try {
            writer.write(message);
            writer.write(System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }


    /**
     * Displays the contents of the log file to the console
     */
    public void displayLog() {
        FileReader reader;
        try {
            System.out.println("//=============== LOG FILE CONTENTS ===============\\\\");
            reader = new FileReader(logFile);
            int ch;
            while ((ch = reader.read()) != -1) {
                System.out.print((char) ch);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }


    /**
     * Closes the log and file writer.
     */
    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing log file: " + e.getMessage());
        }
    }
}
