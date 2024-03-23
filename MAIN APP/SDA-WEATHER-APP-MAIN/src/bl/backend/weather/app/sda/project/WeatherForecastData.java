
package bl.backend.weather.app.sda.project;


public class WeatherForecastData {
    private String forecastDate;
    private double minTemp;
    private double maxTemp;
    private int humidity;
    private String description;

    public WeatherForecastData(String forecastDate, double minTemp, double maxTemp, int humidity, String description) {
        this.forecastDate = forecastDate;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.humidity = humidity;
        this.description = description;
    }

   
     
    public String getForecastDate() {
        return forecastDate;
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

    public String getDescription() {
        return description;
    }
}
