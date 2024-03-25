import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WeatherDataFileManager implements DBInterface.DataAccessInterface {

    // Functions for Air Population
    @Override
    public void storeAirPollutionDataFromJson(JSONObject jsonData, String city, double longitude, double latitude) {
       jsonData.put("Longitude", longitude);
    jsonData.put("Latitude", latitude);
    storeDataToAP(jsonData, "air_pollution_data.txt");
    }

    @Override
    public JSONObject retrieveAirPollutionData(double latitude, double longitude) {
        return retrieveDataFromFileAP(latitude, longitude, "air_pollution_data.txt");
    }

    // Functions for Current Weather
    @Override
    public void storeCurrentWeatherDataFromJson(JSONObject jsonData, String city, double longitude, double latitude) {
       jsonData.put("Longitude", longitude);
    jsonData.put("Latitude", latitude);
    storeCurrentWeatherDataToFile(jsonData, "CurrentWeatherForcast.txt");
    }

    @Override
    public JSONObject retrieveCurrentWeatherData(double latitude, double longitude) {
        return retrieveCurrentWeatherFromFile(latitude, longitude, "CurrentWeatherForcast.txt");
    }

    // Functions for Forecast Weather
    @Override
    public void storeForecastDataFromJson(JSONArray jsonData, String city, double longitude, double latitude) {
         for (int i = 0; i < jsonData.length(); i++) {
        JSONObject forecast = jsonData.getJSONObject(i);
        forecast.put("Longitude", longitude);
        forecast.put("Latitude", latitude);
    }
        storeForecastWeatherDataToFile(jsonData, "ForecastWeatherData.txt");
    }

    @Override
    public JSONArray retrieveForecastData(double latitude, double longitude) {
        return retrieveForecastWeatherFromFile(latitude, longitude, "ForecastWeatherData.txt");
    }

    @Override
    public boolean isWeatherDataExists(double latitude, double longitude) {
        // Implement weather data existence check if needed
        return false;
    }

    @Override
    public boolean isAirPollutionDataExists(double latitude, double longitude) {
        // Implement air pollution data existence check if needed
        return false;
    }

    @Override
    public boolean isForcastDataExists(double latitude, double longitude) {
        // Implement forecast data existence check if needed
        return false;
    }

    // Utility function to store data to a file
    private void storeDataToAP(JSONObject data, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(data.toString());
            System.out.println("Data stored successfully in " + fileName);
        } catch (IOException e) {
            System.err.println("Error storing data to " + fileName + ": " + e.getMessage());
        }
    }

    // Utility function to retrieve data from a file based on latitude and longitude
    private JSONObject retrieveDataFromFileAP(double latitude, double longitude, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject obj = new JSONObject(line);
                double objLatitude = obj.getDouble("Latitude");
                double objLongitude = obj.getDouble("Longitude");
                if (objLatitude == latitude && objLongitude == longitude) {
                    return obj;
                }
            }
            System.out.println("No data found for latitude: " + latitude + " and longitude: " + longitude + " in " + fileName);
        } catch (IOException e) {
            System.err.println("Error retrieving data from " + fileName + ": " + e.getMessage());
        }
        return null;
    }

    // Utility function to store current weather data to a file
    private void storeCurrentWeatherDataToFile(JSONObject currentWeatherData, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(currentWeatherData.toString());
            System.out.println("Current weather data stored successfully in " + fileName);
        } catch (IOException e) {
            System.err.println("Error storing current weather data to " + fileName + ": " + e.getMessage());
        }
    }

    // Utility function to retrieve current weather data from a file based on latitude and longitude
    private JSONObject retrieveCurrentWeatherFromFile(double latitude, double longitude, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject obj = new JSONObject(line);
                double objLatitude = obj.getDouble("Latitude");
                double objLongitude = obj.getDouble("Longitude");
                if (objLatitude == latitude && objLongitude == longitude) {
                    return obj;
                }
            }
            System.out.println("No current weather data found for latitude: " + latitude + " and longitude: " + longitude + " in " + fileName);
        } catch (IOException e) {
            System.err.println("Error retrieving current weather data from " + fileName + ": " + e.getMessage());
        }
        return null;
    }

    // Utility function to store forecast weather data to a file
    private void storeForecastWeatherDataToFile(JSONArray forecastWeatherData, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(forecastWeatherData.toString());
            System.out.println("Forecast weather data stored successfully in " + fileName);
        } catch (IOException e) {
            System.err.println("Error storing forecast weather data to " + fileName + ": " + e.getMessage());
        }
    }

    // Utility function to retrieve forecast weather data from a file based on latitude and longitude
    private JSONArray retrieveForecastWeatherFromFile(double latitude, double longitude, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder jsonData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }
            return new JSONArray(jsonData.toString());
        } catch (IOException e) {
            System.err.println("Error retrieving forecast weather data from " + fileName + ": " + e.getMessage());
        }
        return new JSONArray();
    }
}
