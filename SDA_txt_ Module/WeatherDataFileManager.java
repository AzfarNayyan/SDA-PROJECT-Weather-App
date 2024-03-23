import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WeatherDataFileManager {

    // Functions for Air Population
    public static void storeDataToAP(JSONObject data, String fileName) 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(data.toString());
            System.out.println("Data stored successfully in " + fileName);
        } catch (IOException e) {
            System.err.println("Error storing data to " + fileName + ": " + e.getMessage());
        }
    }

    public static List<JSONObject> retrieveDataFromFileAP(double latitude, double longitude, String fileName) {
        List<JSONObject> matchingData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject obj = new JSONObject(line);
                double objLatitude = obj.getDouble("Latitude");
                double objLongitude = obj.getDouble("Longitude");
                if (objLatitude == latitude && objLongitude == longitude) {
                    matchingData.add(obj);
                }
            }
            if (!matchingData.isEmpty()) {
                System.out.println("Data retrieved successfully from " + fileName + " for latitude: " + latitude + " and longitude: " + longitude);
            } else {
                System.out.println("No data found for latitude: " + latitude + " and longitude: " + longitude + " in " + fileName);
            }
        } catch (IOException e) {
            System.err.println("Error retrieving data from " + fileName + ": " + e.getMessage());
        }
        return matchingData;
    }

    // Functions for Current Weather
    public static void storeCurrentWeatherDataToFile(JSONObject currentWeatherData, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Latitude: " + currentWeatherData.getDouble("Latitude"));
            writer.write("Longitude: " + currentWeatherData.getDouble("Longitude"));
            writer.write("City Name: " + currentWeatherData.getString("city"));
            writer.write("Current temp: " + currentWeatherData.getDouble("temperature"));
            writer.write("Max Temperature: " + currentWeatherData.getDouble("max_temp"));
            writer.write("Min Temperature: " + currentWeatherData.getDouble("min_temp"));
            writer.write("Feels like: " + currentWeatherData.getDouble("feels_like"));
            writer.write("Humidity: " + currentWeatherData.getInt("humidity"));
            writer.write("Sunrise: " + currentWeatherData.getString("sunrise"));
            writer.write("Sunset: " + currentWeatherData.getString("sunset"));
            System.out.println("Current weather data stored successfully in " + fileName);
        } catch (IOException e) {
            System.err.println("Error storing current weather data to " + fileName + ": " + e.getMessage());
        }
    }

    
public static List<JSONObject> retrieveCurrentWeatherFromFile(double latitude, double longitude, String fileName) {
    List<JSONObject> matchingData = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        StringBuilder jsonData = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonData.append(line);
        }
        JSONObject obj = new JSONObject(jsonData.toString());
        double objLatitude = obj.getDouble("Latitude");
        double objLongitude = obj.getDouble("Longitude");
        if (objLatitude == latitude && objLongitude == longitude) {
            matchingData.add(obj);
        }
        if (!matchingData.isEmpty()) {
            System.out.println("Current weather data retrieved successfully from " + fileName + " for latitude: " + latitude + " and longitude: " + longitude);
        } else {
            System.out.println("No current weather data found for latitude: " + latitude + " and longitude: " + longitude + " in " + fileName);
        }
    } catch (IOException e) {
        System.err.println("Error retrieving current weather data from " + fileName + ": " + e.getMessage());
    }
    return matchingData;
}


    // Functions for Forecast Weather
    public static void storeForecastWeatherDataToFile(JSONObject forecastWeatherData, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            JSONArray forecastArray = forecastWeatherData.getJSONArray("forecast");
            for (int i = 0; i < forecastArray.length(); i++) 
            {
                JSONObject forecast = forecastArray.getJSONObject(i);
                writer.write("Latitude: " + forecast.getDouble("Latitude"));
                writer.write("Longitude: " + forecast.getDouble("Longitude"));
                writer.write("Date: " + forecast.getString("Date"));
                writer.write("Min Temperature: " + forecast.getDouble("Min Temperature"));
                writer.write("Max Temperature: " + forecast.getDouble("Max Temperature"));
                writer.write("Humidity: " + forecast.getInt("Humidity"));
                writer.write("Description: " + forecast.getString("Description"));
            }
            System.out.println("Forecast weather data stored successfully in " + fileName);
        } catch (IOException e) {
            System.err.println("Error storing forecast weather data to " + fileName + ": " + e.getMessage());
        }
    }
    public static List<JSONObject> retrieveForecastWeatherFromFile(double latitude, double longitude, String fileName) {
        List<JSONObject> matchingData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject obj = new JSONObject(line);
                double objLatitude = obj.getDouble("Latitude");
                double objLongitude = obj.getDouble("Longitude");
                if (objLatitude == latitude && objLongitude == longitude) {
                    matchingData.add(obj);
                }
            }
            if (!matchingData.isEmpty()) {
                System.out.println("Forecast weather data retrieved successfully from " + fileName + " for latitude: " + latitude + " and longitude: " + longitude);
            } else {
                System.out.println("No forecast weather data found for latitude: " + latitude + " and longitude: " + longitude + " in " + fileName);
            }
        } catch (IOException e) {
            System.err.println("Error retrieving forecast weather data)");
        }
        return matchingData;
    }
    
   public static void main(String[] args) {
    // Store Air Pollution Data
    JSONObject airPollutionData = new JSONObject();
    airPollutionData.put("Latitude", 40.7128);
    airPollutionData.put("Longitude", -74.0060);
    airPollutionData.put("Air Quality Index (aqi)", 5);
    airPollutionData.put("Timestamp", "2024-03-15 21:21:41");
    airPollutionData.put("CO", 2002.72);
    airPollutionData.put("NH3", 121.59);
    airPollutionData.put("NO", 4.3);
    airPollutionData.put("NO2", 78.14);
    airPollutionData.put("SO2", 15.14);
    storeDataToAP(airPollutionData, "air_pollution_data.txt");

    // Retrieve Air Pollution Data
    List<JSONObject> retrievedAirPollutionData = retrieveDataFromFileAP(40.7128, -74.0060, "air_pollution_data.txt");
    if (!retrievedAirPollutionData.isEmpty()) {
        System.out.println("Retrieved air pollution data: ");
        for (JSONObject data : retrievedAirPollutionData) 
        {
            System.out.println(data);
        }
    }

    // Store Current Weather Data
    JSONObject currentWeatherData = new JSONObject();
    currentWeatherData.put("Latitude", 40.7128);
    currentWeatherData.put("Longitude", -74.0060);
    currentWeatherData.put("city", "Lahore");
    currentWeatherData.put("temperature", 17.99);
    currentWeatherData.put("max_temp", 17.99);
    currentWeatherData.put("min_temp", 13.06);
    currentWeatherData.put("feels_like", 17.39);
    currentWeatherData.put("humidity", 59);
    currentWeatherData.put("sunrise", "2024-03-16 01:11:26");
    currentWeatherData.put("sunset", "2024-03-16 13:11:07");
    storeCurrentWeatherDataToFile(currentWeatherData, "CurrentWeatherForcast.txt");

    /* This is not Working
    // Retrieve Current Weather Data
    List<JSONObject> retrievedCurrentWeatherData = retrieveCurrentWeatherFromFile(40.7128, -74.0060, "CurrentWeatherForcast.txt");
    if (!retrievedCurrentWeatherData.isEmpty()) {
        System.out.println("Retrieved current weather data: ");
        for (JSONObject data : retrievedCurrentWeatherData) {
            System.out.println(data);
        }
    }
    */

    // Store Forecast Weather Data
    JSONObject forecastWeatherData = new JSONObject();
    JSONArray forecastArray = new JSONArray();
    JSONObject forecast1 = new JSONObject();
    forecast1.put("Latitude", 40.7128);
    forecast1.put("Longitude", -74.0060);
    forecast1.put("Date", "2024-03-16");
    forecast1.put("Min Temperature", 12.75);
    forecast1.put("Max Temperature", 16.24);
    forecast1.put("Humidity", 52);
    forecast1.put("Description", "clear sky");
    forecastArray.put(forecast1);
    // Add more forecast entries here...
    forecastWeatherData.put("forecast", forecastArray);
    storeForecastWeatherDataToFile(forecastWeatherData, "ForecastWeatherData.txt");

    /* This is not Working
    // Retrieve Forecast Weather Data
    List<JSONObject> retrievedForecastWeatherData = retrieveForecastWeatherFromFile(40.7128, -74.0060, "ForecastWeatherData.txt");
    if (!retrievedForecastWeatherData.isEmpty()) {
        System.out.println("Retrieved forecast weather data: ");
        for (JSONObject data : retrievedForecastWeatherData) {
            System.out.println(data);
        }
    }
    */
}


}