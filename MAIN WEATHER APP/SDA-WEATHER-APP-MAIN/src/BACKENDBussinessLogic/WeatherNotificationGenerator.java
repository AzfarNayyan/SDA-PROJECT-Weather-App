package BACKENDBussinessLogic;

public class WeatherNotificationGenerator {

    
    private static final double POOR_TEMPERATURE_THRESHOLD = 25.0; 
    private static final int POOR_HUMIDITY_THRESHOLD = 60; 
    public static String generateNotification(WeatherData weatherData) {
        StringBuilder notification = new StringBuilder("Weather Notification:\n");

       
        if (weatherData.getTemperature() > POOR_TEMPERATURE_THRESHOLD) {
            notification.append("High temperature: ").append(weatherData.getTemperature()).append("°C\n");
        } else {
            notification.append("Temperature is acceptable.\n");
        }

       
        if (weatherData.getHumidity() > POOR_HUMIDITY_THRESHOLD) {
            notification.append("High humidity: ").append(weatherData.getHumidity()).append("%\n");
        } else {
            notification.append("Humidity is acceptable.\n");
        }
        return notification.toString();
    }
}
