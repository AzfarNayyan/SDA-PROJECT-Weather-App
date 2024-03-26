package BACKENDBussinessLogic;

public class AirQualityNotificationGenerator {

    
    private static final int POOR_AQI_THRESHOLD = 2; // Example threshold for poor AQI

    public static String generateNotification(AirPollutionData airPollutionData) {
        StringBuilder notification = new StringBuilder("Air Quality Notification:\n");

        
        if (airPollutionData.getAqi() > POOR_AQI_THRESHOLD) {
            notification.append("Poor Air Quality Index (AQI): ").append(airPollutionData.getAqi()).append("\n");
        } else {
            notification.append("Air Quality is acceptable.\n");
        }

        return notification.toString();
    }
}
