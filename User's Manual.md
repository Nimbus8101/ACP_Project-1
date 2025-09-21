Project 1
Author: Elijah Reyna

Description:
    Project to practice using the Java Reflection API, using apacher.derby for local database connections, and using SQL in a java program

How to Use:
    Assuming that you have both Maven and Derby installed, simply build the project using "mvn build" if on windows. Then run the main function located in Database.java

Completed Work:
    I've achieved all the functionality in the project description. In the main function I have comments with the information in the "When the Project Runs" paragraph. The database creates a table based on the fields in the class passed to it (The Vehicle class, in this case)

The files:
    The files in the source folder have headers to explain their purpose and functionality, but here is a quick rundown:
     - Database.java: Operates the database connection and executes the sql commands
     - SimpleDatabaseConnection.java: Handles the actual connection to the database
     - DatabaseLog.java: Logs the actions taken in the database
     - QueryBuilder.java: Handles creating the queries in proper sql format
     - DatabaseUtils.java: Holds functions used to assist in executing the Database.java methods, placed in a seperate file for organization
     - Vehicle.java: Class to hold vehicle information
     - VehicleFactory.java: Creates vehicles (most notably creates randomized vehicles)
     - ClassSaver.java: A template class which saves objects of a class in a csv file, using reflection to build columns based on instance fields
     - VehicleSaver.java: A class to save Vehicle objects. Not used in the Database but I wanted to leave the first iteration of ClassSaver.java in there
     - ObjectLoader.java: Used to read the objects from a csv file. This is also a generic class

Use of AI:
    I know I'm filling out a survey about it, but I wanted to include a file (UseOfAI.txt) that documented some notable queries to Chatgpt, which follow a query, response, specific information I wanted to find, and what I learned (to prevent myself from just reading and forgetting what I was given)

    I also subscribe to Githubs Copilot, which has an autocomplete feature. This greatly increases the speed at which I can write code, and saves me from some of the repetitive writing I might otherwise do. The Copilot also has a chat feature, but I didn't use it, and haven't found it useful in personal projects.

What I learned and Enjoyed:
    I really enjoyed refactoring the code. I loved algebra and taking code and breaking it down feels alot like that. My favorite part of code writing has to be taking a large function I wrote and breaking it down and organizing it all.
    Using the Reflection API has shown me some things I can apply in my personal projects. I'm not sure if I should've used an enum for the Make and Size fields in the Vehicle class, but I was surprised at how Reflection took enums in stride.
    I also enjoyed practicing sql, even if we were only creating some basic queries