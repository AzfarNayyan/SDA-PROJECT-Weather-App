package dbconnection;

import org.json.JSONObject;
import org.json.JSONArray;

public interface DBInterface {
    interface DataAccessInterface {
        void storeCurrentWeatherDataFromJson(JSONObject jsonData,String city,double longitude,double latitude);

        JSONObject retrieveCurrentWeatherData(double latitude, double longitude);

        void storeAirPollutionDataFromJson(JSONObject jsonData,String city,double longitude,double latitude);

        JSONObject retrieveAirPollutionData(double latitude, double longitude);

        void storeForecastDataFromJson(JSONArray jsonData,String city,double longitude,double latitude);

        JSONArray retrieveForecastData(double latitude, double longitude);

        boolean isWeatherDataExists(double longitude, double latitude);

        boolean isAirPollutionDataExists(double longitude, double latitude);

        boolean isForcastDataExists(double longitude, double latitude);
    }
}
