import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import org.json.JSONException;

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
            obj.remove("Longitude");
            obj.remove("Latitude");
            return obj;
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
            obj.remove("Longitude");
            obj.remove("Latitude");
            return obj;
        }
        System.out.println("No current weather data found in " + fileName);
    } catch (IOException e) {
        System.err.println("Error retrieving current weather data from " + fileName + ": " + e.getMessage());
    }
    return null;
}


    // Utility function to store forecast weather data to a file
private void storeForecastWeatherDataToFile(JSONArray forecastWeatherData, String fileName) {
  try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
    // Write the opening bracket before iterating
    writer.write("[");

    for (int i = 0; i < forecastWeatherData.length(); i++) {
      if (i > 0) {
        writer.write(","); // Add a comma between JSON objects
      }
      writer.write(forecastWeatherData.getJSONObject(i).toString());
    }

    // Write the closing bracket after iterating
    writer.write("]");

    writer.newLine(); // Ensure new line at the end
    System.out.println("Forecast weather data stored successfully in " + fileName);
  } catch (IOException e) {
    System.err.println("Error storing forecast weather data to " + fileName + ": " + e.getMessage());
  }
}

    // Utility function to retrieve forecast weather data from a file based on latitude and longitude
private JSONArray retrieveForecastWeatherFromFile(double latitude, double longitude, String fileName) {
  JSONArray forecastData = new JSONArray();
  try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
    String line;
    while ((line = reader.readLine()) != null) {
      // Parse the entire line as a JSON array
      JSONArray completeData = new JSONArray(line);

      // Check if the latitude and longitude match the first element (location data)
      if (completeData.length() > 0) {
        JSONObject locationData = completeData.getJSONObject(0);
        double objLatitude = locationData.optDouble("Latitude");
        double objLongitude = locationData.optDouble("Longitude");
        if (objLatitude == latitude && objLongitude == longitude) {
          // Since the first element is location data, skip it
          for (int i = 1; i < completeData.length(); i++) {
            forecastData.put(completeData.getJSONObject(i));
          }
        }
      }
    }
  } catch (IOException e) {
    System.err.println("Error reading JSON data from " + fileName + ": " + e.getMessage());
  } catch (JSONException e) {
    System.err.println("Error parsing JSON data from " + fileName + ": " + e.getMessage());
  }
  return forecastData;
}

    @Override
public void storeForecastDataFromJson(JSONArray jsonData, String city, double longitude, double latitude) {
    // Check if the data already exists
    String fileName = "ForecastWeatherData.txt";
    if (isDataExistsForFD(fileName, latitude, longitude)) {
        System.out.println("Data already exists for latitude: " + latitude + " and longitude: " + longitude + " in " + fileName);
        return;
    }

    try {
        JSONObject locationData = new JSONObject();
        locationData.put("Longitude", longitude);
        locationData.put("Latitude", latitude);
        
        JSONArray completeData = new JSONArray();
        completeData.put(locationData); // Add location data first

        // Add forecast data
        for (int i = 0; i < jsonData.length(); i++) {
            JSONObject forecast = jsonData.getJSONObject(i);
            completeData.put(forecast);
        }

        storeForecastWeatherDataToFile(completeData, fileName);
    } catch (JSONException e) {
        System.err.println("Error creating JSON data: " + e.getMessage());
    }
}

  @Override
public JSONArray retrieveForecastData(double latitude, double longitude) {
    return retrieveForecastWeatherFromFile(latitude, longitude, "ForecastWeatherData.txt");
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
    
    
private boolean isDataExistsForFD(String fileName, double latitude, double longitude) {
  try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
    String line;
    while ((line = reader.readLine()) != null) {
      // Parse the entire line as a JSON array
      JSONArray completeData = new JSONArray(line);

      // Check if the data array has any elements (meaning data exists)
      if (completeData.length() > 0) {
        // Access the first element (location data)
        JSONObject locationData = completeData.getJSONObject(0);
        double objLatitude = locationData.optDouble("Latitude");
        double objLongitude = locationData.optDouble("Longitude");
        if (objLatitude == latitude && objLongitude == longitude) {
          return true; // Data exists
        }
      }
    }
  } catch (IOException e) {
    System.err.println("Error reading JSON data from " + fileName + ": " + e.getMessage());
  } catch (JSONException e) {
    System.err.println("Error parsing JSON data from " + fileName + ": " + e.getMessage());
  }
  return false; // Data does not exist
}
 
}
