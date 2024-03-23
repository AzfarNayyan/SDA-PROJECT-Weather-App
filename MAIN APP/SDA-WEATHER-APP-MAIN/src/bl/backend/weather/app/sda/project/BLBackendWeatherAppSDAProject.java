package bl.backend.weather.app.sda.project;
import UI.DataTransferLayer.UIInterface;
import Terminal.Based.UI.TerminalUI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class BLBackendWeatherAppSDAProject {
    
   
   public static void main(String[] args) {
       
        UIInterface ui = new TerminalUI();
        
        boolean uiTer = true;
        while(uiTer)
        {
            double[] coordinates = ui.getInputValues();
            CacheManager cacheManager = new CacheManager(coordinates[0], coordinates[1]);
        
            JSONExporter jex = new JSONExporter();
        
            JSONObject wd1 = jex.convertToJSONWeatherData(cacheManager.getCachedWeatherData());
            JSONObject apd1 = jex.convertToJSONAirPollutionData(cacheManager.getCachedAirPollutionData());
            JSONArray fd1 = jex.convertToJSONForecastData(cacheManager.getCachedForecastDataList());
        
            ui.showData(wd1,apd1,fd1);
        }
    }
}

