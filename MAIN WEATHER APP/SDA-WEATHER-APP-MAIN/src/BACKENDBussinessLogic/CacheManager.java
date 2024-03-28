package BACKENDBussinessLogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import DBDataTransferInterface.DBInterface;
import SQLDatabase.ImplementationSQL;
import TXTDatabase.ImplementationTXT;
import java.sql.DriverManager;
import org.json.JSONObject;
import org.json.JSONArray;
import java.math.BigDecimal;


public class CacheManager {
    private WeatherAPIHandler apiStonks;
    private WeatherData cachedWeatherData;
    private AirPollutionData cachedAirPollutionData;
    private List<WeatherForecastData> cachedForecastDataList;
    private DBDataTransferInterface.DBInterface.DataAccessInterface dbInterface;
    private AirQualityNotificationGenerator anoti;
    private WeatherNotificationGenerator wnoti;
    Connection conn = null;
   
    
    private String AIR_QUALITY_NOTI;
    private String WEATHER_NOTIFICATION;

    public CacheManager(double latitude, double longitude, int DB_NUMBER)
    {
        latitude = roundTo9DecimalPoints(latitude);
        longitude = roundTo9DecimalPoints(longitude);
        System.out.println(latitude);
        System.out.println(longitude);
        this.apiStonks = new WeatherAPIHandler("70483fff196e58ca9a25fa29076f0f1e");
        connect();
        
        if(DB_NUMBER == 1)
        {
            this.dbInterface = new ImplementationSQL.DataAccessImpl(conn);
            System.out.println("SQL WALI DB IS CONNECTED!!!!");
        }
        else if(DB_NUMBER == 2)
        {
             this.dbInterface = new ImplementationTXT.DataAccessImpl(conn);
             System.out.println("TXT WALI DB IS CONNECTED!!!!");
        }
        else
        {
            return;
        }
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
    
    private void connect(){
        
    try {
        
        Class.forName("com.mysql.cj.jdbc.Driver");

        
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SDAProject", "root", "12345678");
        
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    
    }
    
    
    private double roundTo9DecimalPoints(double value) {
        
        BigDecimal bd = BigDecimal.valueOf(value).setScale(9, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
}