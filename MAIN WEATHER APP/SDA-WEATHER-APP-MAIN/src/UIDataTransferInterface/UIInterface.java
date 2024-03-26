
package UIDataTransferInterface;
import org.json.JSONObject;
import org.json.JSONArray;

public interface UIInterface 
{
    double[] getInputValues();
    void showData(JSONObject wd, JSONObject apd, JSONArray ary, String WeatherNotification, String AirNotification);
}