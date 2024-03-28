package GraphicalUI;

import UIDataTransferInterface.UIInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import BACKENDBussinessLogic.CacheManager;
import BACKENDBussinessLogic.AirPollutionData;
import BACKENDBussinessLogic.WeatherData;
import BACKENDBussinessLogic.WeatherForecastData;
import TerminalUI.LocationInput;

public class GraphicalUI implements UIInterface {
    
    BACKENDBussinessLogic.JSONConverter jc = new BACKENDBussinessLogic.JSONConverter();
    private Scanner scanner;
    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search?q=";
    

   
    public GraphicalUI() {
        
    }
    
    
    
    @Override
    public double[] getInputValues() {
        
        Main m1= new Main();
        m1.setVisible(true);
 
        return null;
    }
    
    @Override 
    public void showData(JSONObject wd, JSONObject apd, JSONArray ary , String WeatherNotification, String AirNotification)
    { 
        Main m1= new Main();
        m1.setVisible(true);
    }
    
   
}
