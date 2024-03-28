package DBDataTransferInterface;

import org.json.JSONObject;
import org.json.JSONArray;

public interface DBInterface {
    interface DataAccessInterface {
        
        void storeCurrentWeatherDataFromJson(JSONObject jsonData,String city,double longitude,double latitude);
        void storeAirPollutionDataFromJson(JSONObject jsonData,String city,double longitude,double latitude);
        void storeForecastDataFromJson(JSONArray jsonData,String city,double longitude,double latitude);
        
        JSONObject retrieveCurrentWeatherData(double latitude, double longitude);
        JSONObject retrieveAirPollutionData(double latitude, double longitude);
        JSONArray retrieveForecastData(double latitude, double longitude);

        boolean isWeatherDataExists(double longitude, double latitude);
        boolean isAirPollutionDataExists(double longitude, double latitude);
        boolean isForcastDataExists(double longitude, double latitude);
    }
}