package DBDataTransferInterface;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface DBInterface 
{
    void storeCurrentWeatherDataFromJson(JSONObject jsonObjectWeatherData, String cityName);
    void storeAirPollutionDataFromJson(JSONObject jsonObjectAirpollData, String cityName);
    void storeForecastDataFromJson(JSONArray jsonArrayForcastData, String cityName);
    
    JSONObject retrieveCurrentWeatherData(double latitude, double longitude);
    JSONObject retrieveAirPollutionData(double latitude, double longitude);
    JSONArray retrieveForecastData(double latitude, double longitude);
}
