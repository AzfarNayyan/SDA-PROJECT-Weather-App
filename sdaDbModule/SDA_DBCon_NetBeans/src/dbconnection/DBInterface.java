
package dbconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;


public class DBInterface
{
     interface DataAccessInterface
    {
    void storeCurrentWeatherDataFromJson(JSONObject jsonData,String city);
    
    List<JSONObject> retrieveCurrentWeatherData(double latitude, double longitude);

    void storeAirPollutionDataFromJson(JSONObject jsonData,String city);
    
    List<JSONObject> retrieveAirPollutionData(double latitude, double longitude);

    void storeForecastDataFromJson(JSONObject jsonData,String city);
    List<JSONObject> retrieveForecastData(double latitude, double longitude);
    
  }
   
}
