package dbconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONArray;
public class Implementation {
    
    public static class DataAccessImpl implements DBInterface.DataAccessInterface 
    {
        private Connection conn;
        //Funtion to check if location Exist or not
        public boolean locationExists(String city, double latitude, double longitude) throws SQLException {
    String sql = "SELECT id FROM Locations WHERE city = ? AND latitude = ? AND longitude = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, city);
    pstmt.setDouble(2, latitude);
    pstmt.setDouble(3, longitude);
    ResultSet rs = pstmt.executeQuery();
    return rs.next(); // Return true if a row exists, indicating that the location exists
}
 private int getLocationId(String city, double latitude, double longitude) throws SQLException {
        // Check if the location exists in the Locations table
        String locationSql = "SELECT id FROM Locations WHERE city = ? AND latitude = ? AND longitude = ?";
        PreparedStatement locationPstmt = conn.prepareStatement(locationSql);
        locationPstmt.setString(1, city);
        locationPstmt.setDouble(2, latitude);
        locationPstmt.setDouble(3, longitude);

        ResultSet resultSet = locationPstmt.executeQuery();

        if (resultSet.next()) {
            // Location exists, return its id
            return resultSet.getInt("id");
        } else {
            // Location does not exist, so insert it into the Locations table
            String insertLocationSql = "INSERT INTO Locations (city, latitude, longitude) VALUES (?, ?, ?)";
            PreparedStatement insertLocationPstmt = conn.prepareStatement(insertLocationSql, PreparedStatement.RETURN_GENERATED_KEYS);
            insertLocationPstmt.setString(1, city);
            insertLocationPstmt.setDouble(2, latitude);
            insertLocationPstmt.setDouble(3, longitude);

            insertLocationPstmt.executeUpdate();

            // Retrieve the auto-generated id
            ResultSet generatedKeys = insertLocationPstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve auto-generated key.");
            }
        }
    }
 public boolean locationExists(int locationId) throws SQLException {
    String sql = "SELECT id FROM Locations WHERE id = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, locationId);
    ResultSet rs = pstmt.executeQuery();
    return rs.next(); // Return true if a row exists, indicating that the location exists
}
 //Will be used for forcastData
private int insertLocation(String city, double latitude, double longitude) throws SQLException {
    String insertLocationSql = "INSERT INTO Locations (city, latitude, longitude) VALUES (?, ?, ?)";
    try (PreparedStatement insertLocationPstmt = conn.prepareStatement(insertLocationSql, Statement.RETURN_GENERATED_KEYS)) {
        insertLocationPstmt.setString(1, city);
        insertLocationPstmt.setDouble(2, latitude);
        insertLocationPstmt.setDouble(3, longitude);

        insertLocationPstmt.executeUpdate();

        // Retrieve the auto-generated id
        try (ResultSet generatedKeys = insertLocationPstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve auto-generated key.");
            }
        }
    }
}
        // Constructor to initialize the database connection
        public DataAccessImpl(Connection conn) 
        {
            this.conn = conn;
        }
       @Override
        public void storeCurrentWeatherDataFromJson(JSONObject jsonData,String city) {
        try {
            
            double longitude = jsonData.getDouble("longitude");
            double latitude = jsonData.getDouble("latitude");

            int locationId = getLocationId(city, latitude, longitude);

            // Now insert weather data into the CurrentWeatherData table
            double feelsLike = jsonData.getDouble("feels_like");
            double temperature = jsonData.getDouble("temperature");
            double minTemp = jsonData.getDouble("min_temp");
            double maxTemp = jsonData.getDouble("max_temp");
            int humidity = jsonData.getInt("humidity");
            String sunrise = jsonData.getString("sunrise");
            String sunset = jsonData.getString("sunset");

            String weatherSql = "INSERT INTO CurrentWeatherData "
                    + "(location_id, feels_like, temperature, min_temp, max_temp, humidity, city, sunrise, sunset) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(weatherSql);
            pstmt.setInt(1, locationId);
            pstmt.setDouble(2, feelsLike);
            pstmt.setDouble(3, temperature);
            pstmt.setDouble(4, minTemp);
            pstmt.setDouble(5, maxTemp);
            pstmt.setInt(6, humidity);
            pstmt.setString(7, city);
            pstmt.setString(8, sunrise);
            pstmt.setString(9, sunset);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
        @Override
        public List<JSONObject> retrieveCurrentWeatherData(double latitude, double longitude) {
     List<JSONObject> resultList = new ArrayList<>();
    try {
        // Prepare SQL statement to retrieve location ID based on latitude and longitude
        String locationSql = "SELECT id FROM Locations WHERE latitude = ? AND longitude = ? ";
        PreparedStatement locationPstmt = conn.prepareStatement(locationSql);
        locationPstmt.setDouble(1, latitude);
        locationPstmt.setDouble(2, longitude);
        ResultSet locationRs = locationPstmt.executeQuery();

        if (locationRs.next()) {
            // Location found, retrieve weather data based on location ID
            int locationId = locationRs.getInt("id");
            String sql = "SELECT * FROM CurrentWeatherData WHERE location_id = ? ORDER BY location_id DESC LIMIT 1";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, locationId);
            ResultSet rs = pstmt.executeQuery();

            // Process the results and convert to JSON
            while (rs.next()) {
                JSONObject json = new JSONObject();
                json.put("city", rs.getString("city"));
                json.put("temperature", rs.getDouble("temperature"));
                json.put("max_temp", rs.getDouble("max_temp"));
                json.put("min_temp", rs.getDouble("min_temp"));
                json.put("feels_like", rs.getDouble("feels_like"));
                json.put("humidity", rs.getInt("humidity"));
                json.put("sunrise", rs.getString("sunrise"));
                json.put("sunset", rs.getString("sunset"));

                resultList.add(json);
            }
        } else {
            System.out.println("Location not found.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return resultList;
}


 @Override
public void storeAirPollutionDataFromJson( JSONObject jsonData,String city) {
    try {
        double latitude = jsonData.getDouble("latitude");
        double longitude = jsonData.getDouble("longitude");

        int locationId;
        if (locationExists(city, latitude, longitude)) {
            locationId = getLocationId(city, latitude, longitude);
        } else {
            String insertLocationSql = "INSERT INTO Locations (city, latitude, longitude) VALUES (?, ?, ?)";
            PreparedStatement insertLocationPstmt = conn.prepareStatement(insertLocationSql, PreparedStatement.RETURN_GENERATED_KEYS);
            insertLocationPstmt.setString(1, city);
            insertLocationPstmt.setDouble(2, latitude);
            insertLocationPstmt.setDouble(3, longitude);

            insertLocationPstmt.executeUpdate();

            ResultSet generatedKeys = insertLocationPstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                locationId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve auto-generated key.");
            }
        }

        double aqi = jsonData.getDouble("aqi");
        String timestamp = jsonData.getString("timestamp");
        double co = jsonData.getDouble("co");
        double nh3 = jsonData.getDouble("nh3");
        double no = jsonData.getDouble("no");
        double no2 = jsonData.getDouble("no2");
        double so2 = jsonData.getDouble("so2");

        String airPollutionSql = "INSERT INTO AirPollutionData "
                + "(location_id, aqi, timestamp, co, nh3, no, no2, so2, city) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(airPollutionSql);
        pstmt.setInt(1, locationId);
        pstmt.setDouble(2, aqi);
        pstmt.setString(3, timestamp);
        pstmt.setDouble(4, co);
        pstmt.setDouble(5, nh3);
        pstmt.setDouble(6, no);
        pstmt.setDouble(7, no2);
        pstmt.setDouble(8, so2);
        pstmt.setString(9, city);

        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


      @Override
        public List<JSONObject> retrieveAirPollutionData(double latitude, double longitude) {
    List<JSONObject> resultList = new ArrayList<>();
    try {
        // Prepare SQL statement to retrieve location ID based on latitude and longitude
        String locationSql = "SELECT id FROM Locations WHERE latitude = ? AND longitude = ?";
        PreparedStatement locationPstmt = conn.prepareStatement(locationSql);
        locationPstmt.setDouble(1, latitude);
        locationPstmt.setDouble(2, longitude);
        ResultSet locationRs = locationPstmt.executeQuery();

        if (locationRs.next()) {
            // Location found, retrieve air pollution data based on location ID
            int locationId = locationRs.getInt("id");
            String sql = "SELECT * FROM AirPollutionData WHERE location_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, locationId);
            ResultSet rs = pstmt.executeQuery();

            // Process the results and convert to JSON
            while (rs.next()) {
                JSONObject json = new JSONObject();
                json.put("aqi", rs.getInt("aqi"));
                json.put("timestamp", rs.getString("timestamp"));
                json.put("co", rs.getDouble("co"));
                json.put("nh3", rs.getDouble("nh3"));
                json.put("no", rs.getDouble("no"));
                json.put("no2", rs.getDouble("no2"));
                json.put("so2", rs.getDouble("so2"));
                json.put("city", rs.getString("city"));
                json.put("latitude", latitude);
                json.put("longitude", longitude);

                resultList.add(json);
            }
        } else {
            System.out.println("Location not found.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return resultList;
}

@Override
public void storeForecastDataFromJson(JSONObject jsonData, String city) {
    try {
        // Check if the location exists, and retrieve its ID if it does; otherwise, insert it
        double latitude = jsonData.getDouble("latitude");
        double longitude = jsonData.getDouble("longitude");
        int locationId;
        if (locationExists(city, latitude, longitude)) {
            locationId = getLocationId(city, latitude, longitude);
        } else {
            locationId = insertLocation(city, latitude, longitude);
        }

        // Extract forecast data array from JSON
        JSONArray forecastArray = jsonData.getJSONArray("forecast");

        // Iterate over each forecast entry
        for (int i = 0; i < forecastArray.length(); i++) {
            JSONObject forecastEntry = forecastArray.getJSONObject(i);

            String forecastDate = forecastEntry.getString("date");
            double minTemp = forecastEntry.getDouble("minTemperature");
            double maxTemp = forecastEntry.getDouble("maxTemperature");
            int humidity = forecastEntry.getInt("humidity");
            String weatherDescription = forecastEntry.getString("description");

            // Now insert forecast data into the ForecastData table
            String forecastSql = "INSERT INTO ForecastData "
                    + "(location_id, forecast_date, min_temp, max_temp, humidity, description) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(forecastSql);
            pstmt.setInt(1, locationId);
            pstmt.setString(2, forecastDate);
            pstmt.setDouble(3, minTemp);
            pstmt.setDouble(4, maxTemp);
            pstmt.setInt(5, humidity);
            pstmt.setString(6, weatherDescription);
            pstmt.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

@Override
public List<JSONObject> retrieveForecastData(double latitude, double longitude) {
    List<JSONObject> forecastList = new ArrayList<>();
    try {
        // Prepare SQL statement to retrieve location ID based on latitude and longitude
        String locationSql = "SELECT id FROM Locations WHERE latitude = ? AND longitude = ?";
        PreparedStatement locationPstmt = conn.prepareStatement(locationSql);
        locationPstmt.setDouble(1, latitude);
        locationPstmt.setDouble(2, longitude);
        ResultSet locationRs = locationPstmt.executeQuery();

        if (locationRs.next()) {
            // Location found, retrieve forecast data based on location ID
            int locationId = locationRs.getInt("id");
            String sql = "SELECT * FROM ForecastData WHERE location_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, locationId);
            ResultSet rs = pstmt.executeQuery();

            // Process the results and convert to JSON
            while (rs.next()) {
                JSONObject forecastJson = new JSONObject();
                forecastJson.put("forecast_date", rs.getString("forecast_date"));
                forecastJson.put("min_temp", rs.getDouble("min_temp"));
                forecastJson.put("max_temp", rs.getDouble("max_temp"));
                forecastJson.put("humidity", rs.getInt("humidity"));
                forecastJson.put("description", rs.getString("description"));

                forecastList.add(forecastJson);
            }
        } else {
            System.out.println("Location not found.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return forecastList;
}

    }
    
    
}
