import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;

public class WeatherDataFileManager implements DBInterface.DataAccessInterface 
{

    // Functions for Air Population
    @Override
    public void storeAirPollutionDataFromJson(JSONObject jsonData, String city, double longitude, double latitude) {
 String fileName = "air_pollution_data.txt";
    if (isDataExists(fileName, latitude, longitude)) {
        System.out.println("Data already exists for latitude: " + latitude + " and longitude: " + longitude + " in " + fileName);
        return;
    }
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
         String fileName = "CurrentWeatherForcast.txt";
    if (isDataExists(fileName, latitude, longitude)) {
        System.out.println("Data already exists for latitude: " + latitude + " and longitude: " + longitude + " in " + fileName);
        return;
    }
       jsonData.put("Longitude", longitude);
    jsonData.put("Latitude", latitude);
    storeCurrentWeatherDataToFile(jsonData, "CurrentWeatherForcast.txt");//helper function
    }

    @Override
    public JSONObject retrieveCurrentWeatherData(double latitude, double longitude) {
        return retrieveCurrentWeatherFromFile(latitude, longitude, "CurrentWeatherForcast.txt");
    }

    // Functions for Forecast Weather
        @Override
        public void storeForecastDataFromJson(JSONArray jsonData, String city, double longitude, double latitude) {
             // Check if the data already exists
             
          String fileName = "ForecastWeatherData.txt";
           if (isDataExistsForFD(fileName, latitude, longitude)) {
        System.out.println("Data already exists for latitude: " + latitude + " and longitude: " + longitude + " in " + fileName);
        return;
    }
       
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
            String fileName = "CurrentWeatherForcast.txt";
           return ( isDataExists(fileName, latitude, longitude)) ;
    }

    @Override
    public boolean isAirPollutionDataExists(double latitude, double longitude) {
          String fileName = "air_pollution_data.txt";
           return ( isDataExists(fileName, latitude, longitude)) ;
    }

    @Override
    public boolean isForcastDataExists(double latitude, double longitude) {
         String fileName = "ForecastWeatherData.txt";
           return ( isDataExistsForFD(fileName, latitude, longitude)) ;
    }

    // Utility function to store data to a file
    private void storeDataToAP(JSONObject data, String fileName) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
        writer.write(data.toString());
        writer.newLine(); // Ensure new line before appending
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
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
        // Ensure new line before appending
        writer.write(currentWeatherData.toString());
         writer.newLine();
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
       
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
        writer.write(forecastWeatherData.toString());
        writer.newLine(); // Ensure new line before appending
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
    
    
    private boolean isDataExists(String fileName, double latitude, double longitude) {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = reader.readLine()) != null) {
            JSONObject obj = new JSONObject(line);
            double objLatitude = obj.optDouble("Latitude");
            double objLongitude = obj.optDouble("Longitude");
            if (objLatitude == latitude && objLongitude == longitude) {
                return true;
            }
        }
    } catch (IOException e) {
        System.err.println("Error checking existing data in " + fileName + ": " + e.getMessage());
    }
    return false;
}
    
    
    private boolean isDataExistsForFD(String fileName, double latitude, double longitude) 
    {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) 
    {
        StringBuilder jsonData = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            // Append the line to jsonData StringBuilder
            jsonData.append(line);
        }

        // Check if jsonData is empty
        if (jsonData.length() == 0) 
        {
            // File is empty
            return false;
        }

        // Convert the string to JSONArray
        JSONArray jsonArray = new JSONArray(jsonData.toString());

        // Check if the JSONArray contains at least one element
        if (jsonArray.length() > 0) {
            // Get the first JSONObject from the JSONArray
            JSONObject obj = jsonArray.getJSONObject(0);
            double objLatitude = obj.optDouble("Latitude");
            double objLongitude = obj.optDouble("Longitude");
            // Compare the latitude and longitude
            return objLatitude == latitude && objLongitude == longitude;
        }
    } catch (IOException e) 
    {
        System.err.println("Error reading or parsing JSON data from " + fileName + ": " + e.getMessage());
    } 
    return false;
    }
    
}
