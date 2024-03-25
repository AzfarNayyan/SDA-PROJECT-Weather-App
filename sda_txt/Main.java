import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        // Sample weather data
        JSONObject currentWeatherData = new JSONObject();
        currentWeatherData.put("city", "Karachi");
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
        weatherManager.storeCurrentWeatherDataFromJson(currentWeatherData, "Karachi", 69.3436, 31.5497);

         //Retrieve current weather data
        JSONObject retrievedCurrentWeatherData = weatherManager.retrieveCurrentWeatherData(31.5497, 74.3436);
        System.out.println("Retrieved current weather data: " + retrievedCurrentWeatherData);

         //Store air pollution data
        weatherManager.storeAirPollutionDataFromJson(airPollutionData, "Karachi",69.3436, 31.5497);

        // Retrieve air pollution data
        JSONObject retrievedAirPollutionData = weatherManager.retrieveAirPollutionData(31.5497, 74.3436);
        System.out.println("Retrieved air pollution data: " + retrievedAirPollutionData);

         //Store weather forecast data
        weatherManager.storeForecastDataFromJson(forecastData, "Karachi", 69.3436, 31.5497);

        // Retrieve weather forecast data
        JSONArray retrievedForecastData = weatherManager.retrieveForecastData(31.5497, 74.3436);
        System.out.println("Retrieved forecast data: " + retrievedForecastData);
    }
}
