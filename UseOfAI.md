Use of AI

I subscribe to github Copilot, and make use of its autocompletion feature. It saves alot of time and keeps me from some repetition. It does really like to give me try-catch blocks, and since it's something I dont use often, I figured I should include them in the code.
I do not use the chat feature of this software.

CHATGPT queries
    The following are notable queries made to chatgpt. I also note the information I was looking for and any additional information I learned from the response. This should help ensure I'm using AI as a learning tool, not a homework completer

    I also used it alot to find out how to write certain things, or the name of a method I couldn't reacall off the top of my head.

====== Query 1 =====
Query
   import java.sql.Connection;
   import java.sql.DriverManager;
   import java.sql.SQLException;
   import java.io.FileInputStream;
   import java.io.IOException;
   import java.util.Properties;

   /**
      A simple data source for getting database connections. 
   */
   public class SimpleDataSource
   {
      private static String url;
      private static String username;
      private static String password;

      /**
         Initializes the data source.
         @param fileName the name of the property file that 
         contains the database driver, URL, username, and password
      */
      public static void init(String fileName)
            throws IOException, ClassNotFoundException
      {  
         Properties props = new Properties();
         FileInputStream in = new FileInputStream(fileName);
         props.load(in);

         String driver = props.getProperty("jdbc.driver");
         url = props.getProperty("jdbc.url");
         username = props.getProperty("jdbc.username");
         if (username == null) username = "";
         password = props.getProperty("jdbc.password");
         if (password == null) password = "";
         if (driver != null)
            Class.forName(driver);
      }

      /**
         Gets a connection to the database.
         @return the database connection
      */
      public static Connection getConnection() throws SQLException
      {
         return DriverManager.getConnection(url, username, password);
      }
   }

   Can you help me understand why the variables and methods in this class are all static, and how it works?

Response
   Absolutely, let's break it down step by step. This `SimpleDataSource` class is a **utility class** that simplifies the process of connecting to a database in Java using JDBC (Java Database Connectivity).

   ---

   ## 🔧 Why are the variables and methods `static`?

   The key reason is that this class is designed as a **singleton-like utility** — meaning:

   * You don't need to create an instance of `SimpleDataSource`.
   * All operations are performed via **class-level methods and variables**.

   This approach avoids repetitive instantiations and centralizes database connection logic.

   ### Static Variables

   ```java
   private static String url;
   private static String username;
   private static String password;
   ```

   These variables hold the configuration (URL, username, password) needed to create a database connection.

   * They're `static` because the configuration is **shared across all usages** of this class.
   * Once initialized, any code that calls `getConnection()` will use the same database settings.

   ### Static Methods

   ```java
   public static void init(String fileName)
   ```

   * This sets the configuration (reads from a `.properties` file) once, and stores it in the static variables.
   * It also loads the JDBC driver class using `Class.forName(driver)`.

   ```java
   public static Connection getConnection()
   ```

   * This method uses the static configuration variables to return a new `Connection` object using `DriverManager`.

   So overall:

   * The class is initialized once with `SimpleDataSource.init(...)`.
   * Then you can get database connections at any time using `SimpleDataSource.getConnection()`.

   ---

   ## 📦 How It Works (Step-by-Step)

   Assume you have a properties file `database.properties`:

   ```properties
   jdbc.driver=com.mysql.cj.jdbc.Driver
   jdbc.url=jdbc:mysql://localhost:3306/mydatabase
   jdbc.username=root
   jdbc.password=secret
   ```

   ### 1. **Initialize the Data Source**

   ```java
   SimpleDataSource.init("database.properties");
   ```

   * Loads the properties file.
   * Sets the `url`, `username`, and `password`.
   * Loads the JDBC driver class (`Class.forName(...)`) — required by some older JDBC drivers.

   ### 2. **Get a Connection**

   ```java
   Connection conn = SimpleDataSource.getConnection();
   ```

   * Uses the previously loaded config to get a `Connection` from `DriverManager`.

   ---

   ## ⚠️ Considerations

   * **Thread Safety**: This class is not thread-safe if `init()` is called multiple times. Normally, `init()` should be called once during application startup.
   * **Better Practice**: For larger applications, consider using connection pools (like HikariCP, Apache DBCP) rather than opening new connections each time.
   * **No Instance Allowed**: Since everything is static, you don’t need and **should not instantiate** `SimpleDataSource`.

   ---

   ## ✅ Summary

   | Feature              | Why it's `static`                                 |
   | -------------------- | ------------------------------------------------- |
   | Configuration fields | Shared for the whole app (single source of truth) |
   | `init()` method      | Only needs to be run once to configure the class  |
   | `getConnection()`    | Accessible globally without instantiation         |

   This design is clean and simple for small applications or educational purposes, though in production systems you'd typically use a more robust connection pooling solution.

   Let me know if you want an example of using it with a real properties file or want to improve it further!

What I wanted to know
   I had wanted to understand how it was used in the code, and how I was supposed to use that class. I actually didn't need to use that class that much. It was only used in running the sql commands, so when I had a function that ran those commands, I didn't have any use for it.

What I learned
   I also learned from that class how to create Singletons in java! This was something I didn't know how to do, but it is actually very simple. I have a personal project that passes alot of variables from on place down into many other functions. A singleton would simplify things so immensely but I worry how much refactoring I will need to do to implement it!