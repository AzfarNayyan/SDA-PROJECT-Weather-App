package BACKENDBussinessLogic;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.List;
import java.util.ArrayList;




public class WeatherAPIHandler {
    
    private String apiKey = "70483fff196e58ca9a25fa29076f0f1e";
    private String apiKey2 = "47a324990db4f14780610c8400e1696b";

    public WeatherAPIHandler(String apiKey) {
        this.apiKey = apiKey;
    }
    
     public static String unixToDateTime(long utx) {
         
            Instant instant = Instant.ofEpochSecond(utx);
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return dateTime.format(formatter);
    }
     
      public static double kelvinToCelsius(double kelvin) {
  
         double celsius = kelvin - 273.15;
         DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(celsius));
    }

    public WeatherData getCurrentWeather(double latitude, double longitude) {
        try {
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude +
                            "&lon=" + longitude + "&appid=" + apiKey;
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            WeatherData weatherData = parseCurrentWeatherResponse(response.toString());
            return weatherData;
            
        } catch (IOException e) {
       
            return null;
        }
    }
    
    private WeatherData parseCurrentWeatherResponse(String jsonResponse) {
        
        try {
            
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);

            
            JSONObject mainObject = (JSONObject) jsonObject.get("main");
            double feelsLike = ((Number) mainObject.get("feels_like")).doubleValue();
            double temperature = ((Number) mainObject.get("temp")).doubleValue();
            double minTemp = ((Number) mainObject.get("temp_min")).doubleValue();
            double maxTemp = ((Number) mainObject.get("temp_max")).doubleValue();
            int humidity = ((Number) mainObject.get("humidity")).intValue();
            String cityName = (String) jsonObject.get("name");
            

            JSONObject sysObject = (JSONObject) jsonObject.get("sys");
            long sunriseT = (long) sysObject.get("sunrise");
            long sunsetT = (long) sysObject.get("sunset");
            
            String sunriseTime = unixToDateTime(sunriseT);
            String sunsetTime = unixToDateTime(sunsetT);
            
            WeatherData weatherData = new WeatherData(kelvinToCelsius(feelsLike), kelvinToCelsius(temperature), kelvinToCelsius(minTemp), kelvinToCelsius(maxTemp), humidity, sunriseTime, sunsetTime,cityName);
            return weatherData;
        } catch (ParseException e) {
         
            return null;
        }
    }
    

    public List<WeatherForecastData> getWeatherForecast(double latitude, double longitude) {
        try {
            String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude +
                            "&lon=" + longitude + "&appid=" + apiKey;
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            
            List<WeatherForecastData> forecastData = parseWeatherForecastResponse(response.toString());
            return forecastData;
        } catch (IOException e) {
            
            return null;
        }
    }
    
    private List<WeatherForecastData> parseWeatherForecastResponse(String jsonResponse) {
        
    List<WeatherForecastData> forecastDataList = new ArrayList<>();

    try {
        
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);

        
        JSONArray forecastList = (JSONArray) jsonObject.get("list");

        
        String currentDate = null;

        for (Object forecastObj : forecastList) {
            JSONObject forecastItem = (JSONObject) forecastObj;

            
            String dateTime = (String) forecastItem.get("dt_txt");
            String forecastDate = dateTime.split(" ")[0];
            String forecastTime = dateTime.split(" ")[1];

            
            if (currentDate == null || !forecastDate.equals(currentDate)) {
                
                JSONObject main = (JSONObject) forecastItem.get("main");
                double minTemp = ((Number) main.get("temp_min")).doubleValue();
                double maxTemp = ((Number) main.get("temp_max")).doubleValue();
                int humidity = ((Number) main.get("humidity")).intValue();

                JSONArray weatherArray = (JSONArray) forecastItem.get("weather");
                JSONObject weatherObject = (JSONObject) weatherArray.get(0);
                String description = (String) weatherObject.get("description");

                
                WeatherForecastData forecastData = new WeatherForecastData(forecastDate, kelvinToCelsius(minTemp), kelvinToCelsius(maxTemp), humidity, description);
                forecastDataList.add(forecastData);

                
                currentDate = forecastDate;
            }
        }
    } catch (ParseException e) {
       
    }

    return forecastDataList;
}


    public AirPollutionData getAirPollutionData(double latitude, double longitude) {
        try {
            String apiUrl = "https://api.openweathermap.org/data/2.5/air_pollution?lat=" + latitude +
                            "&lon=" + longitude + "&appid=" + apiKey2;
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            
            AirPollutionData pollutionData = parseAirPollutionResponse(response.toString());
            return pollutionData;
        } catch (IOException e) {
            
            return null;
        }
    }
    
   private AirPollutionData parseAirPollutionResponse(String jsonResponse) {
    try {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);

        JSONArray list = (JSONArray) jsonObject.get("list");

        JSONObject firstItem = (JSONObject) list.get(0);

        JSONObject mainComponents = (JSONObject) firstItem.get("components");
        double co = ((Number) mainComponents.get("co")).doubleValue();
        double no = ((Number) mainComponents.get("no")).doubleValue();
        double no2 = ((Number) mainComponents.get("no2")).doubleValue();
        double so2 = ((Number) mainComponents.get("so2")).doubleValue();
        double nh3 = ((Number) mainComponents.get("nh3")).doubleValue();
        
        long times = (long) firstItem.get("dt");

        JSONObject mainMain = (JSONObject) firstItem.get("main");
        int aqi = ((Number) mainMain.get("aqi")).intValue();
        
        String timestamp = unixToDateTime(times);
        
        AirPollutionData pollutionData = new AirPollutionData(aqi, co, no, no2, so2, nh3, timestamp);
        return pollutionData;
    } catch (ParseException e) {
       
        return null;
    }
    
     
}

    
    
}
