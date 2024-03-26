
package BACKENDBussinessLogic;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.List;

public class JSONExporter {

    public JSONObject convertToJSONWeatherData(WeatherData weatherData) {
        JSONObject jsonWeatherData = new JSONObject();
        jsonWeatherData.put("cityName", weatherData.getCityName());
        jsonWeatherData.put("currentTemp", weatherData.getTemperature());
        jsonWeatherData.put("maxTemp", weatherData.getMaxTemp());
        jsonWeatherData.put("minTemp", weatherData.getMinTemp());
        jsonWeatherData.put("feelsLike", weatherData.getFeelsLike());
        jsonWeatherData.put("humidity", weatherData.getHumidity());
        jsonWeatherData.put("sunrise", weatherData.getSunrise());
        jsonWeatherData.put("sunset", weatherData.getSunset());
        return jsonWeatherData;
    }

    public JSONObject convertToJSONAirPollutionData(AirPollutionData airPollutionData) {
        JSONObject jsonAirPollutionData = new JSONObject();
        jsonAirPollutionData.put("aqi", airPollutionData.getAqi());
        jsonAirPollutionData.put("timestamp", airPollutionData.getTimestamp());
        jsonAirPollutionData.put("co", airPollutionData.getCo());
        jsonAirPollutionData.put("nh3", airPollutionData.getNh3());
        jsonAirPollutionData.put("no", airPollutionData.getNo());
        jsonAirPollutionData.put("no2", airPollutionData.getNo2());
        jsonAirPollutionData.put("so2", airPollutionData.getSo2());
        return jsonAirPollutionData;
    }

    public JSONArray convertToJSONForecastData(List<WeatherForecastData> forecastDataList) {
        JSONArray jsonArray = new JSONArray();
        for (WeatherForecastData forecastData : forecastDataList) {
            JSONObject jsonForecastData = new JSONObject();
            jsonForecastData.put("date", forecastData.getForecastDate());
            jsonForecastData.put("minTemp", forecastData.getMinTemp());
            jsonForecastData.put("maxTemp", forecastData.getMaxTemp());
            jsonForecastData.put("humidity", forecastData.getHumidity());
            jsonForecastData.put("description", forecastData.getDescription());
            jsonArray.put(jsonForecastData);
        }
        return jsonArray;
    }
}
