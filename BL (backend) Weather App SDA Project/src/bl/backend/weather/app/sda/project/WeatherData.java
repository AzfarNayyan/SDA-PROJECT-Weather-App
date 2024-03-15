
package bl.backend.weather.app.sda.project;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class WeatherData {
    private double feelsLike;
    private double temperature;
    private double minTemp;
    private double maxTemp;
    private int humidity;
    private long sunrise;
    private long sunset;
    private  String cityName;
    
    public WeatherData(double feelsLike, double temperature, double minTemp, double maxTemp, int humidity, long sunrise, long sunset,String City) {
        this.feelsLike = feelsLike;
        this.temperature = temperature;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.humidity = humidity;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.cityName = City;
    }
    
     public static double kelvinToCelsius(double kelvin) {
  
         double celsius = kelvin - 273.15;
         DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(celsius));
    }
     
     public static String unixToDateTime(long utx) {
         
        Instant instant = Instant.ofEpochSecond(utx);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
       
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
     
     
    public double getFeelsLike() {
        return kelvinToCelsius(feelsLike);
    }

    public double getTemperature() {
        return kelvinToCelsius(temperature);
    }

    public double getMinTemp() {
        return kelvinToCelsius(minTemp);
    }

    public double getMaxTemp() {
        return kelvinToCelsius(maxTemp);
    }

    public int getHumidity() {
        return humidity;
    }

    public String getSunrise() {
        return unixToDateTime(sunrise);
    }

    public String getSunset() {
        return unixToDateTime(sunset);
    }
    
    public String getCityName() {
        return cityName;
    }
}

