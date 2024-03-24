package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONArray;

public class DBConnection {
    public static void main(String[] args) {
        // Establish database connection
        Connection conn = null;
    try {
        // Load the MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Connect to the database
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SDAProject", "root", "12345678");

        // Create an instance of DataAccessImpl
        Implementation.DataAccessImpl dataAccess = new Implementation.DataAccessImpl(conn);

   

        
        
//TestCurrentWeatherStoreFunction(dataAccess);
//TestCurrentWeatherRetrieveFunction(dataAccess);
//TestAirPollutionStoreFunction(dataAccess);
//TestAirPollutionRetrieveFunction(dataAccess);
//TestForcastStoreFunction(dataAccess);
//TestForcastRetrieveFunction(dataAccess);


            // Close the database connection
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static int getLocationId(Connection conn, double latitude, double longitude) throws SQLException {
        int locationId = -1; // Default value if location not found
        String sql = "SELECT id FROM Locations WHERE latitude = ? AND longitude = ?";
        try (var pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, latitude);
            pstmt.setDouble(2, longitude);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                locationId = rs.getInt("id");
            }
        }
        return locationId;
    }
    
    public static  void TestCurrentWeatherStoreFunction(Implementation.DataAccessImpl dataAccess)
    {
        JSONObject currentWeatherJson = new JSONObject();
        currentWeatherJson.put("feels_like", 12.9); // Adjusted feels_like value
        currentWeatherJson.put("temperature", 17.99);
        currentWeatherJson.put("min_temp", -12.3);
        currentWeatherJson.put("max_temp", 17.99);
        currentWeatherJson.put("humidity", 60);
        currentWeatherJson.put("sunrise", "2024-03-16 01:11:26");
        currentWeatherJson.put("sunset", "2024-03-16 13:11:07");
        currentWeatherJson.put("latitude", 13.5497); // Adding latitude
        currentWeatherJson.put("longitude", 91.3436); // Adding longitude

        // Store CurrentWeatherData from JSON
        dataAccess.storeCurrentWeatherDataFromJson(currentWeatherJson,"Quetta");

    }
    
    
    public static   void  TestCurrentWeatherRetrieveFunction(Implementation.DataAccessImpl dataAccess)
    {
               // Example location ID for retrieving CurrentWeatherData
            double latitude = 12.549700;
            double longitude = 90.343600;
System.out.println("Retreival function called");
            // Retrieve CurrentWeatherData based on latitude and longitude
            List<JSONObject> weatherDataList = dataAccess.retrieveCurrentWeatherData(latitude, longitude);

            // Display the retrieved weather data
            System.out.println("WEATHER DATA FETCHED:");
            for (JSONObject weatherData : weatherDataList) {
                System.out.println("City Name: " + weatherData.getString("city"));
                System.out.println("Current temp: " + weatherData.getDouble("temperature"));
                System.out.println("Max Temperature: " + weatherData.getDouble("max_temp"));
                System.out.println("Min Temperature: " + weatherData.getDouble("min_temp"));
                System.out.println("Feels like: " + weatherData.getDouble("feels_like"));
                System.out.println("Humidity: " + weatherData.getInt("humidity"));
                System.out.println("Sunrise: " + weatherData.getString("sunrise"));
                System.out.println("Sunset: " + weatherData.getString("sunset"));
            }
 
    }
    
   public static void TestAirPollutionStoreFunction(Implementation.DataAccessImpl dataAccess) {
    JSONObject airPollutionJson = new JSONObject();
    airPollutionJson.put("aqi", 5);
    airPollutionJson.put("timestamp", "2024-03-15 21:21:01");
    airPollutionJson.put("co", 2002.72);
    airPollutionJson.put("nh3", 121.59);
    airPollutionJson.put("no", 4.3);
    airPollutionJson.put("no2", 71.1);
    airPollutionJson.put("so2", 32.0);
    airPollutionJson.put("latitude", 43.5497); // Adding latitude
    airPollutionJson.put("longitude", 743.3436); // Adding longitude

    // Store Air Pollution Data from JSON
    dataAccess.storeAirPollutionDataFromJson(airPollutionJson,"faislabad");
}

   
   public static void TestAirPollutionRetrieveFunction(Implementation.DataAccessImpl dataAccess) {
       
      double latitude = 43.549700;
      double longitude = 743.343600;
    List<JSONObject> airPollutionData = dataAccess.retrieveAirPollutionData(latitude, longitude);

    // Display the retrieved air pollution data
    System.out.println("AIR POLLUTION DATA FETCHED:");
    for (JSONObject json : airPollutionData) {
        System.out.println("Air Quality Index (aqi): " + json.getInt("aqi"));
        System.out.println("Timestamp: " + json.getString("timestamp"));
        System.out.println("CO: " + json.getDouble("co"));
        System.out.println("NH3: " + json.getDouble("nh3"));
        System.out.println("NO: " + json.getDouble("no"));
        System.out.println("NO2: " + json.getDouble("no2"));
        System.out.println("SO2: " + json.getDouble("so2"));
        System.out.println("Latitude: " + json.getDouble("latitude"));
        System.out.println("Longitude: " + json.getDouble("longitude"));
        System.out.println();
    }
}

   
    public static void TestForcastStoreFunction(Implementation.DataAccessImpl dataAccess) {
    try {
        JSONObject forecastJson = new JSONObject();
        forecastJson.put("latitude", 13.549700); // Add latitude value
        forecastJson.put("longitude", 91.343600); // Add longitude value

        // Create an array to hold forecast data for multiple days
        JSONArray forecastArray = new JSONArray();

        // Add forecast data for each day
        for (int i = 1; i <= 5; i++) {
            JSONObject day = new JSONObject();
            day.put("date", "2024-03-1" + i);
            day.put("minTemperature", 12.75 + i); 
            day.put("maxTemperature", 16.24 + i); 
            day.put("humidity", 52 - i); 
            day.put("description", "Description for day " + i);
            forecastArray.put(day);
        }

        forecastJson.put("forecast", forecastArray);

        // Store Forecast Data from JSON
        dataAccess.storeForecastDataFromJson(forecastJson, "Quetta");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    
    
    public static void TestForcastRetrieveFunction(Implementation.DataAccessImpl dataAccess)
    {
        double longitude = 91.343600;
        double latitude = 13.549700;

        // Retrieve forecast data
        List<JSONObject> forecastData = dataAccess.retrieveForecastData(latitude, longitude);

        // Display the retrieved forecast data
        System.out.println("FORECAST DATA FETCHED:");
        for (JSONObject json : forecastData) {
            System.out.println("Date: " + json.getString("forecast_date"));
            System.out.println("Min Temperature: " + json.getDouble("min_temp"));
            System.out.println("Max Temperature: " + json.getDouble("max_temp"));
            System.out.println("Humidity: " + json.getInt("humidity"));
            System.out.println("Description: " + json.getString("description"));
            System.out.println();
        }
    }
}
