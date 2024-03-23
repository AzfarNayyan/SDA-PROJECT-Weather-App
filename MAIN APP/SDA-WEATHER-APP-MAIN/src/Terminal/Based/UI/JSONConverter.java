
package Terminal.Based.UI;

import bl.backend.weather.app.sda.project.AirPollutionData;
import bl.backend.weather.app.sda.project.WeatherData;
import bl.backend.weather.app.sda.project.WeatherForecastData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONConverter {

    public WeatherData convertToWeatherData(JSONObject jsonWeatherData) {
        String cityName = (String) jsonWeatherData.get("cityName");
        double currentTemp = (double) jsonWeatherData.get("currentTemp");
        double maxTemp = (double) jsonWeatherData.get("maxTemp");
        double minTemp = (double) jsonWeatherData.get("minTemp");
        double feelsLike = (double) jsonWeatherData.get("feelsLike");
        int humidity = (int) jsonWeatherData.get("humidity");
        String sunrise = (String) jsonWeatherData.get("sunrise");
        String sunset = (String) jsonWeatherData.get("sunset");
        
        return new WeatherData(feelsLike, currentTemp, minTemp, maxTemp, humidity, sunrise, sunset, cityName);
    }

    public AirPollutionData convertToAirPollutionData(JSONObject jsonAirPollutionData) {
        int aqi = (int) jsonAirPollutionData.get("aqi");
        String timestamp = (String) jsonAirPollutionData.get("timestamp");
        double co = (double) jsonAirPollutionData.get("co");
        double nh3 = (double) jsonAirPollutionData.get("nh3");
        double no = (double) jsonAirPollutionData.get("no");
        double no2 = (double) jsonAirPollutionData.get("no2");
        double so2 = (double) jsonAirPollutionData.get("so2");

        return new AirPollutionData(aqi, co, no, no2, so2, nh3, timestamp);
    }

    public List<WeatherForecastData> convertToForecastDataList(JSONArray jsonForecastDataList) {
        
        List<WeatherForecastData> forecastDataList = new ArrayList<>();
        for (Object obj : jsonForecastDataList) {
            JSONObject jsonForecastData = (JSONObject) obj;
            
            String date = (String) jsonForecastData.get("date");
            double minTemp = (double) jsonForecastData.get("minTemp");
            double maxTemp = (double) jsonForecastData.get("maxTemp");
            int humidity = (int) jsonForecastData.get("humidity");
            String description = (String) jsonForecastData.get("description");
            
            WeatherForecastData forecastData = new WeatherForecastData(date, minTemp, maxTemp, humidity, description);
            forecastDataList.add(forecastData);
        }
        return forecastDataList;
    }
}
