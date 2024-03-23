
package bl.backend.weather.app.sda.project;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class WeatherData  {
    private double feelsLike;
    private double temperature;
    private double minTemp;
    private double maxTemp;
    private int humidity;
    private String sunrise;
    private String sunset;
    private  String cityName;
    
    public WeatherData(double feelsLike, double temperature, double minTemp, double maxTemp, int humidity, String sunrise, String sunset,String City) {
        this.feelsLike = feelsLike;
        this.temperature = temperature;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.humidity = humidity;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.cityName = City;
    }
     
    
    public double getFeelsLike() {
        return feelsLike;
    }


    public double getTemperature() {
        return temperature;
    }

   
    public double getMinTemp() {
        return minTemp;
    }

   
    public double getMaxTemp() {
        return maxTemp;
    }

 
    public int getHumidity() {
        return humidity;
    }

 
    public String getSunrise() {
        return sunrise;
    }


    public String getSunset() {
        return sunset;
    }
    
    public String getCityName() {
        return cityName;
    }
}

