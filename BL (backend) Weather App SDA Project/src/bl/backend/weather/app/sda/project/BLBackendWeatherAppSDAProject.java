package bl.backend.weather.app.sda.project;
import java.util.List;
import UI.DataTransferLayer.UIInterface;
import Terminal.Based.UI.TerminalUI;


public class BLBackendWeatherAppSDAProject {
    
   public static void main(String[] args) {
       
        UIInterface ui = new TerminalUI();
        double[] coordinates = ui.getInputValues();
        CacheManager cacheManager = new CacheManager(coordinates[0], coordinates[1]);
        ui.showData(cacheManager);

        WeatherData wd = cacheManager.getCachedWeatherData();

        System.out.println("LATITUDE INPUT: " + wd.getCityName());
        System.out.println("LONGITUDE INPUT: " + wd.getCityName());

        System.out.println("WEATHER DATA FETCHED: ");
        System.out.println("City Name: " + wd.getCityName());
        System.out.println("Current temp: " + wd.getTemperature());
        System.out.println("Max Temperature: " + wd.getMaxTemp());
        System.out.println("Mim Temperature: " + wd.getMinTemp());
        System.out.println("Feels like: " + wd.getFeelsLike());
        System.out.println("Humidity: " + wd.getHumidity());
        System.out.println("Sunrise: " + wd.getSunrise());
        System.out.println("Sunset: " + wd.getSunset());

        AirPollutionData apd = cacheManager.getCachedAirPollutionData();

        System.out.println("AIR POLLUTION DATA FETCHED: ");
        System.out.println("Air Quality Index (aqi) : " + apd.getAqi());
        System.out.println("Timestamp " + apd.getTimestamp());
        System.out.println("CO: " + apd.getCo());
        System.out.println("NH3: " + apd.getNh3());
        System.out.println("NO: " + apd.getNo());
        System.out.println("NO2: " + apd.getNo2());
        System.out.println("SO2: " + apd.getSo2());

        List<WeatherForecastData> forecastDataList = cacheManager.getCachedForecastDataList();

        System.out.println("WEATHER FORECAST DATA FETCHED: ");
        for (WeatherForecastData forecastData : forecastDataList) {
            System.out.println("Date: " + forecastData.getForecastDate());
            System.out.println("Min Temperature: " + forecastData.getMinTemp());
            System.out.println("Max Temperature: " + forecastData.getMaxTemp());
            System.out.println("Humidity: " + forecastData.getHumidity());
            System.out.println("Description: " + forecastData.getDescription());
            System.out.println();
        }
    }
}

