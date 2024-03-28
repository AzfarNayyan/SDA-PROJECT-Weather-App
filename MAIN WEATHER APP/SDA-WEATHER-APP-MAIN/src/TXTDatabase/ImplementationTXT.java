
package TXTDatabase;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class ImplementationTXT {
    public static class DataAccessImpl implements DBDataTransferInterface.DBInterface.DataAccessInterface 
    {
        private Connection conn;
        public DataAccessImpl(Connection conn) {
             this.conn=conn;       
        }
       
        
    @Override
    public boolean isWeatherDataExists(double longitude,double latitude) {
            String fileName = "CurrentWeatherForcast.txt";
           return ( isDataExists(fileName, latitude, longitude)) ;
    }

    @Override
    public boolean isAirPollutionDataExists(double longitude, double latitude) {
          String fileName = "air_pollution_data.txt";
           return ( isDataExists(fileName, latitude, longitude)) ;
    }

    @Override
    public boolean isForcastDataExists( double longitude,double latitude) {
         String fileName = "ForecastWeatherData.txt";
           return ( isDataExistsForFD(fileName, latitude, longitude)) ;
    }
    
    
    
     
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

  

    private void storeDataToAP(JSONObject data, String fileName) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
        writer.write(data.toString());
        writer.newLine(); 
        System.out.println("Data stored successfully in " + fileName);
    } catch (IOException e) {
        System.err.println("Error storing data to " + fileName + ": " + e.getMessage());
    }
}


   
    private JSONObject retrieveDataFromFileAP(double latitude, double longitude, String fileName) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = reader.readLine()) != null) {
            JSONObject obj = new JSONObject(line);
            double objLatitude = obj.optDouble("Latitude");
            double objLongitude = obj.optDouble("Longitude");
            if (objLatitude == latitude && objLongitude == longitude) {

                int aqi = obj.optInt("aqi");
                String timestamp = obj.optString("timestamp");
                double co = obj.optDouble("co");
                double nh3 = obj.optDouble("nh3");
                double no = obj.optDouble("no");
                double no2 = obj.optDouble("no2");
                double so2 = obj.optDouble("so2");

                // Create a new JSONObject with properly typed variables
                JSONObject result = new JSONObject();
                result.put("aqi", aqi);
                result.put("timestamp", timestamp);
                result.put("co", co);
                result.put("nh3", nh3);
                result.put("no", no);
                result.put("no2", no2);
                result.put("so2", so2);
                return result;
            }
        }
    } catch (IOException e) {
        System.err.println("Error checking existing data in " + fileName + ": " + e.getMessage());
    }
    return null;
    }

  
   private void storeCurrentWeatherDataToFile(JSONObject currentWeatherData, String fileName) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
        
        writer.write(currentWeatherData.toString());
         writer.newLine();
        System.out.println("Current weather data stored successfully in " + fileName);
    } catch (IOException e) {
        System.err.println("Error storing current weather data to " + fileName + ": " + e.getMessage());
    }
}


    private JSONObject retrieveCurrentWeatherFromFile(double latitude, double longitude, String fileName) {
        
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = reader.readLine()) != null) {
            JSONObject obj = new JSONObject(line);
            double objLatitude = obj.optDouble("Latitude");
            double objLongitude = obj.optDouble("Longitude");
            if (objLatitude == latitude && objLongitude == longitude) {
               
                double feelsLike = obj.optDouble("feelsLike");
                double temperature = obj.optDouble("currentTemp");
                double minTemp = obj.optDouble("minTemp");
                double maxTemp = obj.optDouble("maxTemp");
                int humidity = obj.optInt("humidity");
                String sunrise = obj.optString("sunrise");
                String sunset = obj.optString("sunset");
                String cityName = obj.optString("cityName");

                
                JSONObject result = new JSONObject();
                result.put("Latitude", objLatitude);
                result.put("Longitude", objLongitude);
                result.put("feelsLike", feelsLike);
                result.put("currentTemp", temperature);
                result.put("minTemp", minTemp);
                result.put("maxTemp", maxTemp);
                result.put("humidity", humidity);
                result.put("sunrise", sunrise);
                result.put("sunset", sunset);
                result.put("cityName", cityName);
                return result;
            }
        }
    } catch (IOException e) {
        System.err.println("Error checking existing data in " + fileName + ": " + e.getMessage());
    }
    return null;

    }

    
private void storeForecastWeatherDataToFile(JSONArray forecastWeatherData, String fileName) {
  try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
  
    writer.write("[");

    for (int i = 0; i < forecastWeatherData.length(); i++) {
      if (i > 0) {
        writer.write(","); 
      }
      writer.write(forecastWeatherData.getJSONObject(i).toString());
    }

    
    writer.write("]");

    writer.newLine(); // Ensure new line at the end
    System.out.println("Forecast weather data stored successfully in " + fileName);
  } catch (IOException e) {
    System.err.println("Error storing forecast weather data to " + fileName + ": " + e.getMessage());
  }
}

    

    @Override
public void storeForecastDataFromJson(JSONArray jsonData, String city, double longitude, double latitude) {
    
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
      
      JSONArray completeData = new JSONArray(line);

      
      if (completeData.length() > 0) {
       
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
  return false; 
}
    }
    

    private static JSONArray retrieveForecastWeatherFromFile(double latitude, double longitude, String fileName) {
    JSONArray forecastDataArray = new JSONArray();
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = reader.readLine()) != null) {
            
            JSONArray completeData = new JSONArray(line);

            
            if (completeData.length() > 0) {
                
                JSONObject locationData = completeData.getJSONObject(0);
                double objLatitude = locationData.optDouble("Latitude");
                double objLongitude = locationData.optDouble("Longitude");
                if (objLatitude == latitude && objLongitude == longitude) {
                    
                    
                for (int i = 1; i < completeData.length(); i++) {
                    JSONObject forecast = completeData.getJSONObject(i);
                    String forecastDate = forecast.getString("date");
                    double minTemp = forecast.getDouble("minTemp");
                    double maxTemp = forecast.getDouble("maxTemp");
                    int humidity = forecast.getInt("humidity");
                    String description = forecast.getString("description");

                    JSONObject forecastData = new JSONObject();
                    forecastData.put("date", forecastDate);
                    forecastData.put("minTemp", minTemp);
                    forecastData.put("maxTemp", maxTemp);
                    forecastData.put("humidity", humidity);
                    forecastData.put("description", description);

                    forecastDataArray.put(forecastData);
                }
                return forecastDataArray;
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Error reading JSON data from " + fileName + ": " + e.getMessage());
    } catch (JSONException e) {
        System.err.println("Error parsing JSON data from " + fileName + ": " + e.getMessage());
    }
    return null;
}

 
}
