
package UI.DataTransferLayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface UIInterface 
{
    double[] getInputValues();
    void showData(JSONObject wd, JSONObject apd, JSONArray ary);
}