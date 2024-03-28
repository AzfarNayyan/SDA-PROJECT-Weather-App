package BACKENDBussinessLogic;
import UIDataTransferInterface.UIInterface;
import TerminalUI.TerminalUI;
import org.json.JSONObject;
import org.json.JSONArray;

public class BLBackendWeatherAppSDAProject {
    
    //SIMPLY CHANGE THE NUMBER TO ACCESS DIFFERENT UI'S AND DB'S. BOTH ARE INTERCHANGABLE.
    //UI: (1 IS TERMINAL BASED) ( 2 IS GRAPHICAL BASED)
    //DB: (1 IS SQL BASED) (2 IS TXT BASED)
    
    private static int UI_NUMBER_1TERMINAL_2GRAPHICAL = 1;
    private static int DB_NUMBER_1SQL_2TXT = 2;
    
    private static UIInterface ui = null;
    private static CacheManager cacheManager = null;
    
    //=======================================================================
    //=======================================================================
    //=======================================================================
    //=======================================================================
    //RUN THIS MAIN FUNCTION TO USE THE APP/
   
   public static void main(String[] args) {
       
       //CODE TO DECIDE WHICH UI WILL RUN AND WHICH DB WILL RUN.
       
       if(UI_NUMBER_1TERMINAL_2GRAPHICAL == 1)
       {
           ui = new TerminalUI();
           handleAppUIDB();
       }
       else if (UI_NUMBER_1TERMINAL_2GRAPHICAL == 2)
       {
           ui = new GraphicalUI.GraphicalUI(); 
           ui.getInputValues();
       }
       else
       {
           System.out.println("CHOSE A VALID UI AND DB NUMBER!!!! 1 OR 2 ");
       }
       
    }
   
   //ALL PRINCIPLES ARE FOLLOWED AND THE UI AND DB ARE SOURCE INDEPENDENT.
   //=======================================================================
   //=======================================================================
   //=======================================================================
   //=======================================================================
   
   
   
   public int getDB(){
       return DB_NUMBER_1SQL_2TXT;
   }
   
   private static void handleAppUIDB()
   {
            boolean uiTer = true;
            while(uiTer)
            {
                double[] coordinates = ui.getInputValues();
                cacheManager = new CacheManager(coordinates[0], coordinates[1], DB_NUMBER_1SQL_2TXT);
        
                JSONExporter jex = new JSONExporter();
        
                JSONObject wd1 = jex.convertToJSONWeatherData(cacheManager.getCachedWeatherData());
                JSONObject apd1 = jex.convertToJSONAirPollutionData(cacheManager.getCachedAirPollutionData());
                JSONArray fd1 = jex.convertToJSONForecastData(cacheManager.getCachedForecastDataList());
                String WeatherNoti = cacheManager.getWeatherNoti();
                String AirPollution = cacheManager.getAirpollNoti();
                ui.showData(wd1,apd1,fd1,WeatherNoti,AirPollution);
            }
   }
   private static void handleAppUIDB1()
   {
       ui.getInputValues();
   }
   
}