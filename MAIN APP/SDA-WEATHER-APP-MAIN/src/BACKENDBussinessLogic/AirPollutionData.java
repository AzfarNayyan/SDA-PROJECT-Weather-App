
package BACKENDBussinessLogic;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class AirPollutionData {
    private int aqi;
    private double co;
    private double no;
    private double no2;
    private double so2;
    private double nh3;
    private String timestamp;
    

    public AirPollutionData(int aqi, double co, double no, double no2, double so2, double nh3, String timestamp) {
        this.aqi = aqi;
        this.co = co;
        this.no = no;
        this.no2 = no2;
        this.so2 = so2;
        this.nh3 = nh3;
        this.timestamp = timestamp;
    }

    public int getAqi() {
        return aqi;
    }

    public double getCo() {
        return co;
    }

    public double getNo() {
        return no;
    }

    public double getNo2() {
        return no2;
    }

    public double getSo2() {
        return so2;
    }

    public double getNh3() {
        return nh3;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
