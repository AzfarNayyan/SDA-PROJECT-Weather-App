import org.json.JSONObject;
import java.util.List;

public class WeatherDataDBManager implements DBInterface.DataAccessInterface {

    @Override
    public void storeCurrentWeatherDataFromJson(JSONObject jsonData) {
        WeatherDataFileManager.storeCurrentWeatherDataToFile(jsonData, "CurrentWeatherForcast.txt");
    }

    @Override
    public List<JSONObject> retrieveCurrentWeatherData(double latitude, double longitude) {
        return WeatherDataFileManager.retrieveCurrentWeatherFromFile(latitude, longitude, "CurrentWeatherForcast.txt");
    }

    @Override
    public void storeAirPollutionDataFromJson(JSONObject jsonData) {
        WeatherDataFileManager.storeDataToAP(jsonData, "air_pollution_data.txt");
    }

    @Override
    public List<JSONObject> retrieveAirPollutionData(double latitude, double longitude) {
        return WeatherDataFileManager.retrieveDataFromFileAP(latitude, longitude, "air_pollution_data.txt");
    }

    @Override
    public void storeForecastDataFromJson(JSONObject jsonData) {
        WeatherDataFileManager.storeForecastWeatherDataToFile(jsonData, "ForecastWeatherData.txt");
    }

    @Override
    public List<JSONObject> retrieveForecastData(double latitude, double longitude) {
        return WeatherDataFileManager.retrieveForecastWeatherFromFile(latitude, longitude, "ForecastWeatherData.txt");
    }
}
