package BACKENDBussinessLogic;
import SQLDatabase.Implementation;
import UIDataTransferInterface.UIInterface;
import TerminalUI.TerminalUI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.json.JSONObject;
import org.json.JSONArray;

public class BLBackendWeatherAppSDAProject {
    
   
   public static void main(String[] args) {
       
       // Establish database connection
        Connection conn = null;
    try {
        // Load the MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Connect to the database
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SDAProject", "root", "12345678");
        
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
       
       
        UIInterface ui = new TerminalUI();
        
        boolean uiTer = true;
        while(uiTer)
        {
            double[] coordinates = ui.getInputValues();
            CacheManager cacheManager = new CacheManager(coordinates[0], coordinates[1],conn);
        
            JSONExporter jex = new JSONExporter();
        
            JSONObject wd1 = jex.convertToJSONWeatherData(cacheManager.getCachedWeatherData());
            JSONObject apd1 = jex.convertToJSONAirPollutionData(cacheManager.getCachedAirPollutionData());
            JSONArray fd1 = jex.convertToJSONForecastData(cacheManager.getCachedForecastDataList());
        
            ui.showData(wd1,apd1,fd1);
        }
    }
}