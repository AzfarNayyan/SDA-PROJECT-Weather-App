package GraphicalUI;

import BACKENDBussinessLogic.AirPollutionData;
import BACKENDBussinessLogic.WeatherData;
import BACKENDBussinessLogic.WeatherForecastData;
import TerminalUI.LocationInput;
import UIDataTransferInterface.UIInterface;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class GraphicalUI implements UIInterface {
    
    BACKENDBussinessLogic.JSONConverter jc = new BACKENDBussinessLogic.JSONConverter();

   
    public GraphicalUI() {
    }

    @Override
    public double[] getInputValues() {
        LocationInput li = new LocationInput();
        double[] coor = li.getCoors();
        return coor;
    }
    
    public static void clearScreen() {
        
         try {
            Thread.sleep(5000); // 5000 milliseconds = 5 seconds
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        // ANSI escape code to clear the screen
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    @Override 
    public void showData(JSONObject wd, JSONObject apd, JSONArray ary , String WeatherNotification, String AirNotification)
    { 
        WeatherData wdata = jc.convertToWeatherData(wd);
        AirPollutionData apdata = jc.convertToAirPollutionData(apd);
        List<WeatherForecastData> forecastDataList = jc.convertToForecastDataList(ary);
    }
    
}
