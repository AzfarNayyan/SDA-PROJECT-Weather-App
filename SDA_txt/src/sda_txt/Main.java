
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        // Sample weather data
        JSONObject currentWeatherData = new JSONObject();
        currentWeatherData.put("city", "fa34");
        currentWeatherData.put("temperature", 17.99);
        currentWeatherData.put("max_temp", 12.9);
        currentWeatherData.put("min_temp", 13.06);
        currentWeatherData.put("feels_like", 17.39);
        currentWeatherData.put("humidity", 59);
        currentWeatherData.put("sunrise", "2024-03-16 01:11:26");
        currentWeatherData.put("sunset", "2024-03-16 13:11:07");

        // Sample air pollution data
        JSONObject airPollutionData = new JSONObject();
        airPollutionData.put("Air Quality Index (aqi)", 5);
        airPollutionData.put("Timestamp", "2024-03-15 21:21:41");
        airPollutionData.put("CO", 2002.72);
        airPollutionData.put("NH3", 121.59);
        airPollutionData.put("NO", 4.3);
        airPollutionData.put("NO2", 78.14);
        airPollutionData.put("SO2", 15.14);

        // Sample weather forecast data
        JSONArray forecastData = new JSONArray();
        JSONObject forecast1 = new JSONObject();
        forecast1.put("Date", "2024-03-16");
        forecast1.put("Min Temperature", 12.75);
        forecast1.put("Max Temperature", 16.24);
        forecast1.put("Humidity", 52);
        forecast1.put("Description", "clear sky");
        forecastData.put(forecast1);

        JSONObject forecast2 = new JSONObject();
        forecast2.put("Date", "2024-03-17");
        forecast2.put("Min Temperature", 15.06);
        forecast2.put("Max Temperature", 15.06);
        forecast2.put("Humidity", 38);
        forecast2.put("Description", "broken clouds");
        forecastData.put(forecast2);

        JSONObject forecast3 = new JSONObject();
        forecast3.put("Date", "2024-03-18");
        forecast3.put("Min Temperature", 16.2);
        forecast3.put("Max Temperature", 16.2);
        forecast3.put("Humidity", 38);
        forecast3.put("Description", "overcast clouds");
        forecastData.put(forecast3);

        JSONObject forecast4 = new JSONObject();
        forecast4.put("Date", "2024-03-19");
        forecast4.put("Min Temperature", 16.51);
        forecast4.put("Max Temperature", 16.51);
        forecast4.put("Humidity", 29);
        forecast4.put("Description", "few clouds");
        forecastData.put(forecast4);

        JSONObject forecast5 = new JSONObject();
        forecast5.put("Date", "2024-03-20");
        forecast5.put("Min Temperature", 17.07);
        forecast5.put("Max Temperature", 17.07);
        forecast5.put("Humidity", 35);
        forecast5.put("Description", "scattered clouds");
        forecastData.put(forecast5);

        // Test data insertion and retrieval
        WeatherDataFileManager weatherManager = new WeatherDataFileManager();

        // Store current weather data
        weatherManager.storeCurrentWeatherDataFromJson(currentWeatherData, "fa34", 2221.433336, 111.9533357);

        // Retrieve current weather data
        JSONObject retrievedCurrentWeatherData = weatherManager.retrieveCurrentWeatherData(111.9533357, 2221.433336);
        System.out.println("Retrieved current weather data: " + retrievedCurrentWeatherData);

        // Store air pollution data
        weatherManager.storeAirPollutionDataFromJson(airPollutionData, "Lahore", 90.3436, 1.5497);

        // Retrieve air pollution data
        JSONObject retrievedAirPollutionData = weatherManager.retrieveAirPollutionData(1.5497, 90.3436);
        System.out.println("Retrieved air pollution data: " + retrievedAirPollutionData);

        // Store weather forecast data
      weatherManager.storeForecastDataFromJson(forecastData, "KARACHI", 1234, 2);
//
//        // Retrieve weather forecast data
        JSONArray retrievedForecastData = weatherManager.retrieveForecastData(1234,2);
       System.out.println("Retrieved forecast data: " + retrievedForecastData);
       
        // Sample latitude and longitude coordinates
        double weatherLatitude = 111.9533357;
        double weatherLongitude = 2221.433336;
        double airPollutionLatitude = 1.5497;
        double airPollutionLongitude = 90.3436;
        double forecastLatitude = 1234;
        double forecastLongitude = 2;


        // Check if weather data exists
        boolean weatherExists = weatherManager.isWeatherDataExists(weatherLatitude, weatherLongitude);
        System.out.println("Weather data exists: " + weatherExists);

        // Check if air pollution data exists
        boolean airPollutionExists = weatherManager.isAirPollutionDataExists(airPollutionLatitude, airPollutionLongitude);
        System.out.println("Air pollution data exists: " + airPollutionExists);

        // Check if forecast data exists
        boolean forecastExists = weatherManager.isForcastDataExists(forecastLatitude, forecastLongitude);
        System.out.println("Forecast data exists: " + forecastExists);
    }
}
    
   

