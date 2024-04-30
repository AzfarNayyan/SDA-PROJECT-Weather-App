# Software Design and Architecture. Weather App Project.
----------------------------------------------------------------------------------------------------------------------------------


:red_circle: How to Run/Compile this project: :bangbang:</br>

1- Download the zip file of the repository. </br>
2- Un-Zip the folder. </br>
3- You can use NetBeans and IntelliJ Java compiler to run this project. </br>
4- For SQL database firstly you have to make a SQL database by running the queries provided on GitHub. Make a Database named ‘SDAProject’ as provided in WeatherAppDataBaseSchema.sql on the repository. 
   MySQL based database is used. Use MySQL Workbench for this. </br>
5- Open the Project named ‘SDA-WEATHER-APP-MAIN' in the folder ‘MAIN WEATHER APP’. Open this project in the java compiler you have chosen. </br>
6- In the project file and in the src folder there is a package named ‘BACKENDBussinessLogic’ which is BL for this project. In this package there is a main java executable class named 
   ‘BLBackendWeatherAppSDAProject.java’. </br>
7- This main java class is executable, and you will run this file. </br>
8- In this file there are two static variables. </br>
9- UI_NUMBER_1TERMINAL_2GRAPHICAL and DB_NUMBER_1SQL_2TXT. </br>
10- SIMPLY CHANGE THE NUMBER TO ACCESS DIFFERENT UI'S AND DB'S. BOTH ARE INTERCHANGABLE. </br>
11- UI: (1 IS TERMINAL BASED) (2 IS GRAPHICAL BASED). </br>
12- DB: (1 IS SQL BASED) (2 IS TXT BASED). </br>
13- Change and run to compile. </br>
14- The terminal UI has some meaning full outputs which basically tells how application is working. Please ignore if you are using Terminal UI. </br>

----------------------------------------------------------------------------------------------------------------------------------
:red_circle: If you are facing library issue, make sure to include the libraries included in ‘JARS’ on GitHub repository. :bangbang:  

1. json-20240303.jar </br>
2. json-simple-1.1.jar </br>
3. mysql-connector-j-8.3.0.jar </br>

    
----------------------------------------------------------------------------------------------------------------------------------

:red_circle: SOLID Principles: :bangbang:

1. S: Every class has a single responsibility like the classes like ‘WeatherAPIHandler.java’ has the responsibility to handle Api response and parse Json data. This principle is followed throughout. <br />
2. O: It's used in the Interfaces of Ui and db. <br />
3. L: Although there is no specific inheritance used in the code, but still the interface is interchangeable by the child classes in UI and DB. <br />
4. I: Interfaces are made to tackle UI and DB. <br />
5. D: It is the main principle of the project. Both UI and DB are independent of BL.  <br />

----------------------------------------------------------------------------------------------------------------------------------


:red_circle: Design Patterns used: :bangbang: <br />

1. Adapter: JSONExporter.java and JSONConverter.java are the Adapter classes to JSON data. </br>
2. Facade: the interface of UI and DB hide the mess of implementation.  </br>
3. Singleton: cache manager is created only once. </br>
4. Strategy: The application has two UI and DB so application selects a ALGORITHM at run time. </br>
5. Builder: "CacheManger" is the Director and its asks a Concrete Builder "WeatherAPIHandler.java" to build products like WeatherData, ForcastData and AirpollutionData. </br>

----------------------------------------------------------------------------------------------------------------------------------

   
:red_circle: Dependency Inversion: :bangbang: <br />

The communication between UI and DB is done through interface classes. Interface class is of single type for both UI and DB. Making changes in BL does not require changes in UI or DB. Similarly changes in DB or UI doesn't affect the BL because the BL only knows/uses the interface provided to BL.  

----------------------------------------------------------------------------------------------------------------------------------

:red_circle: Class Diagram: :bangbang: <br />

The complete classes model is included in the Documentation folder. The class structure is the same as provided in the first phase. The new class diagram is bulky because complete functions and all classes of project are shown.  
The is no change in the class structure as provided in the first phase except only there is change in notification classes.  
The package diagram is also provided in the first phase. The package diagram is also implemented the same as provided.  
Check the design document for further clarification of design. 

----------------------------------------------------------------------------------------------------------------------------------



:red_circle: GROUP MEMBERS: :bangbang: <br />
Azfar Nayyan (22L-7886). <br />
Saim Imran (22L-7906). <br />
Talha Tofeeq (22L-6190). <br />
Abdullah Shafqat (22L-7905). <br />
Bilal Afzal (22L-7889). <br />
Anas Khan (22L-7963). <br />
