
package bl.backend.weather.app.sda.project;
import java.text.DecimalFormat;




public class WeatherForecastData {
    private String forecastDate;
    private String forecastTime;
    private double minTemp;
    private double maxTemp;
    private int humidity;
    private String description;

    public WeatherForecastData(String forecastDate, String forecastTime, double minTemp, double maxTemp, int humidity, String description) {
        this.forecastDate = forecastDate;
        this.forecastTime = forecastTime;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.humidity = humidity;
        this.description = description;
    }

    
     public static double kelvinToCelsius(double kelvin) {
  
         double celsius = kelvin - 273.15;
         DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(celsius));
    }
     
    public String getForecastDate() {
        return forecastDate;
    }

    public String getForecastTime() {
        return forecastTime;
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

    public String getDescription() {
        return description;
    }
}
