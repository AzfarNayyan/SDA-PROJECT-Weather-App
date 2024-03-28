

package SQLDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.json.JSONObject;
import org.json.JSONArray;

public class DBConnection {
    
   private Connection conn;
    
    public DBConnection(Connection con){
        this.conn=con;
        ImplementationSQL.DataAccessImpl dataAccess = new ImplementationSQL.DataAccessImpl(conn);
    }
    
   public static int getLocationId(Connection conn, double latitude, double longitude) throws SQLException {
        int locationId = -1; 
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
    
   public static void TestCurrentWeatherStoreFunction(ImplementationSQL.DataAccessImpl dataAccess) {
    JSONObject currentWeatherJson = new JSONObject();
    currentWeatherJson.put("feels_like", 12.9); 
    currentWeatherJson.put("temperature", 17.99);
    currentWeatherJson.put("min_temp", -12.3);
    currentWeatherJson.put("max_temp", 17.99);
    currentWeatherJson.put("humidity", 60);
    currentWeatherJson.put("sunrise", "2024-03-16 01:11:26");
    currentWeatherJson.put("sunset", "2024-03-16 13:11:07");
    
    
    dataAccess.storeCurrentWeatherDataFromJson(currentWeatherJson,"RawalPundi",81.2,90.8);
}
        
   public static void TestCurrentWeatherRetrieveFunction(ImplementationSQL.DataAccessImpl dataAccess) {
    
    double latitude = 12.549700;
    double longitude = 90.343600;

    System.out.println("Retrieval function called");

    
    JSONObject weatherData = dataAccess.retrieveCurrentWeatherData( latitude,longitude);

    if (weatherData.isEmpty()) {
        System.out.println("No current weather data found for the location.");
    } else {
        
        System.out.println("WEATHER DATA FETCHED:");
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
    
   public static void TestAirPollutionStoreFunction(ImplementationSQL.DataAccessImpl dataAccess) {
    JSONObject airPollutionJson = new JSONObject();
    airPollutionJson.put("aqi", 5);
    airPollutionJson.put("timestamp", "2024-03-15 21:21:01");
    airPollutionJson.put("co", 2002.72);
    airPollutionJson.put("nh3", 121.59);
    airPollutionJson.put("no", 4.3);
    airPollutionJson.put("no2", 71.1);
    airPollutionJson.put("so2", 32.0);
    
    dataAccess.storeAirPollutionDataFromJson(airPollutionJson,"Gawadar",100.9,200.3);
}

   public static void TestAirPollutionRetrieveFunction(ImplementationSQL.DataAccessImpl dataAccess) {
    double latitude = 200.300000;
    double longitude = 100.900000;
    
   
    JSONObject airPollutionData = dataAccess.retrieveAirPollutionData(latitude, longitude);

   
    System.out.println("AIR POLLUTION DATA FETCHED:");
    System.out.println("Air Quality Index (aqi): " + airPollutionData.getInt("aqi"));
    System.out.println("Timestamp: " + airPollutionData.getString("timestamp"));
    System.out.println("CO: " + airPollutionData.getDouble("co"));
    System.out.println("NH3: " + airPollutionData.getDouble("nh3"));
    System.out.println("NO: " + airPollutionData.getDouble("no"));
    System.out.println("NO2: " + airPollutionData.getDouble("no2"));
    System.out.println("SO2: " + airPollutionData.getDouble("so2"));
    System.out.println("Latitude: " + airPollutionData.getDouble("latitude"));
    System.out.println("Longitude: " + airPollutionData.getDouble("longitude"));
}
 
   public static void TestForecastStoreFunction(ImplementationSQL.DataAccessImpl dataAccess) {
    try {
        
        JSONArray forecastArray = new JSONArray();

        
        for (int i = 1; i <= 5; i++) {
            JSONObject day = new JSONObject();
            day.put("date", "2024-03-1" + i);
            day.put("minTemperature", 12.75 + i); 
            day.put("maxTemperature", 16.24 + i); 
            day.put("humidity", 52 - i); 
            day.put("description", "Description for day " + i);
            forecastArray.put(day);
        }

       
        dataAccess.storeForecastDataFromJson(forecastArray, "Egypt", 29.50, 99.3600);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

   public static void TestForcastRetrieveFunction(ImplementationSQL.DataAccessImpl dataAccess) {
    double longitude = 29.500000;
    double latitude = 99.360000;

    
    JSONArray forecastData = dataAccess.retrieveForecastData(latitude, longitude);

    
    System.out.println("FORECAST DATA FETCHED:");
    for (int i = 0; i < forecastData.length(); i++) {
        JSONObject json = forecastData.getJSONObject(i);
        System.out.println("Date: " + json.getString("forecast_date"));
        System.out.println("Min Temperature: " + json.getDouble("min_temp"));
        System.out.println("Max Temperature: " + json.getDouble("max_temp"));
        System.out.println("Humidity: " + json.getInt("humidity"));
        System.out.println("Description: " + json.getString("description"));
        System.out.println();
    }
}

}