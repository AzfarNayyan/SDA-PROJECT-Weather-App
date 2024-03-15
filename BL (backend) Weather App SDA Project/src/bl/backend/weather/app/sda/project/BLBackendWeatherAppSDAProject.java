package bl.backend.weather.app.sda.project;
import java.util.List;



public class BLBackendWeatherAppSDAProject {
    double lat = 31.5497;
    double longi = 74.3436;
    WeatherAPIHandler apiStonks;

    public static void main(String[] args) {
        BLBackendWeatherAppSDAProject app = new BLBackendWeatherAppSDAProject();
        WeatherAPIHandler apiStonks = new WeatherAPIHandler("70483fff196e58ca9a25fa29076f0f1e");
        
        WeatherData wd = apiStonks.getCurrentWeather(app.lat, app.longi);
        
        System.out.println("LATITUDE INPUT: "+ app.lat);
        System.out.println( "LONGITUDE INPUT:  " + app.longi);
            
        System.out.println("WEATHER DATA FETCHED: ");
        System.out.println("City Name: " + wd.getCityName());
        System.out.println("Current temp: " + wd.getTemperature());
        System.out.println("Max Temperature: " + wd.getMaxTemp());
        System.out.println("Mim Temperature: " + wd.getMinTemp());
        System.out.println("Feels like: " + wd.getFeelsLike());
        System.out.println("Humidity: " + wd.getHumidity());
        System.out.println("Sunrise: " + wd.getSunrise());
        System.out.println("Sunset: " + wd.getSunset());
        
        AirPollutionData apd = apiStonks.getAirPollutionData(app.lat,app.longi);
        
        System.out.println("AIR POLLITION DATA FETCHED: ");
        System.out.println("Air Quality Index (aqi) : " + apd.getAqi());
        System.out.println("Timestamp " + apd.getTimestamp());
        System.out.println("CO: " + apd.getCo());
        System.out.println("NH3: " + apd.getNh3());
        System.out.println("NO: " + apd.getNo());
        System.out.println("NO2: " + apd.getNo2());
        System.out.println("SO2: " + apd.getSo2());
        
        
        
        List<WeatherForecastData> forecastDataList = apiStonks.getWeatherForecast(app.lat, app.longi);
        
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

