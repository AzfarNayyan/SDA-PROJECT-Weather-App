package BACKENDBussinessLogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import DBDataTransferInterface.DBInterface;
import SQLDatabase.Implementation;
import org.json.JSONObject;
import org.json.JSONArray;


public class CacheManager {
    private WeatherAPIHandler apiStonks;
    private WeatherData cachedWeatherData;
    private AirPollutionData cachedAirPollutionData;
    private List<WeatherForecastData> cachedForecastDataList;
    private DBDataTransferInterface.DBInterface.DataAccessInterface dbInterface;
    private AirQualityNotificationGenerator anoti;
    private WeatherNotificationGenerator wnoti;
    
    private String AIR_QUALITY_NOTI;
    private String WEATHER_NOTIFICATION;

    public CacheManager(double latitude, double longitude, Connection conn) 
    {
        this.apiStonks = new WeatherAPIHandler("70483fff196e58ca9a25fa29076f0f1e");
        this.dbInterface = new Implementation.DataAccessImpl(conn);
        String cityName = null;
        anoti = new AirQualityNotificationGenerator();
        wnoti = new WeatherNotificationGenerator();
        
        if (dbInterface.isWeatherDataExists(longitude, latitude)) 
        {
            JSONObject jsonWeatherData = dbInterface.retrieveCurrentWeatherData(latitude, longitude);
            cachedWeatherData = new JSONConverter().convertToWeatherData(jsonWeatherData);
            cityName= cachedWeatherData.getCityName();
            System.out.println("HEYY I CALLED DB!! WEATHER DATA!!!");
        } 
        else 
        {
           cachedWeatherData = apiStonks.getCurrentWeather(latitude, longitude);
           cityName= cachedWeatherData.getCityName();
           dbInterface.storeCurrentWeatherDataFromJson(new JSONExporter().convertToJSONWeatherData(cachedWeatherData), cityName,longitude,latitude);
            System.out.println("HEYY I CALLED API!! WEATHER DATA!!!");
        }
        
        if (dbInterface.isAirPollutionDataExists(longitude, latitude)) 
        {
            JSONObject jsonAirPollutionData = dbInterface.retrieveAirPollutionData(latitude, longitude);
            cachedAirPollutionData = new JSONConverter().convertToAirPollutionData(jsonAirPollutionData);
             System.out.println("HEYY I CALLED DB!! APD!!!");
        } 
        else 
        {
            cachedAirPollutionData = apiStonks.getAirPollutionData(latitude, longitude);
            dbInterface.storeAirPollutionDataFromJson(new JSONExporter().convertToJSONAirPollutionData(cachedAirPollutionData), cityName, longitude, latitude);
             System.out.println("HEYY I CALLED API!! APD!!!");
        }
        
        if (dbInterface.isForcastDataExists(longitude, latitude)) 
        {
            JSONArray jsonForecastDataList = dbInterface.retrieveForecastData(latitude, longitude);
            cachedForecastDataList = new JSONConverter().convertToForecastDataList(jsonForecastDataList);
             System.out.println("HEYY I CALLED DB!! FORCAST DATA!!!");
        } 
        else 
        {
            cachedForecastDataList = apiStonks.getWeatherForecast(latitude, longitude);
            dbInterface.storeForecastDataFromJson(new JSONExporter().convertToJSONForecastData(cachedForecastDataList), cityName, longitude, latitude);
             System.out.println("HEYY I CALLED API!! FORCAST DATA!!!");
        }
        
        AIR_QUALITY_NOTI = anoti.generateNotification(cachedAirPollutionData);
        WEATHER_NOTIFICATION = wnoti.generateNotification(cachedWeatherData);

    }

    public WeatherData getCachedWeatherData() {
        return cachedWeatherData;
    }

    public AirPollutionData getCachedAirPollutionData() {
        return cachedAirPollutionData;
    }

    public List<WeatherForecastData> getCachedForecastDataList() {
        return cachedForecastDataList;
    }
    
    public String getWeatherNoti()
    {
        return WEATHER_NOTIFICATION;
    }
    
    public String getAirpollNoti()
    {
        return AIR_QUALITY_NOTI;
    }
}