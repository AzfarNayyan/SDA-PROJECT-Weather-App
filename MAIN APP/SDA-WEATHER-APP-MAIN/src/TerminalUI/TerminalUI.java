
package TerminalUI;
import UIDataTransferInterface.UIInterface;
import BACKENDBussinessLogic.AirPollutionData;
import BACKENDBussinessLogic.WeatherData;
import BACKENDBussinessLogic.WeatherForecastData;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.Scanner;


public class TerminalUI implements UIInterface {

    
    BACKENDBussinessLogic.JSONConverter jc = new BACKENDBussinessLogic.JSONConverter();

   
    public TerminalUI() {
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
    public void showData(JSONObject wd, JSONObject apd, JSONArray ary)
    {

       Scanner scanner = new Scanner(System.in);
       boolean exit = false;

    while (!exit) {
        System.out.println("Select an option:");
        System.out.println("1. Show Weather Data");
        System.out.println("2. Show Air Pollution Data");
        System.out.println("3. Show Forecast Data");
        System.out.println("4. Return to Main Menu");
        System.out.print("Option: ");

        int option = scanner.nextInt();
        scanner.nextLine(); 

        if (option == 1) {
            showWeatherData(wd);
            clearScreen();
        } else if (option == 2) {
            showAirpollutionData(apd);
            clearScreen();
        } else if (option == 3) {
            showForcastData(ary);
            clearScreen();
        } else if (option == 4) {
            exit = true;
        } else {
            System.out.println("Invalid option. Please try again.");
        }
    
    }
    }
    
    private void showWeatherData(JSONObject jobj)
    {
       WeatherData wd = jc.convertToWeatherData(jobj);
       
        System.out.println("LATITUDE INPUT: " + wd.getCityName());
        System.out.println("LONGITUDE INPUT: " + wd.getCityName());

        System.out.println("WEATHER DATA FETCHED: ");
        System.out.println("City Name: " + wd.getCityName());
        System.out.println("Current temp: " + wd.getTemperature());
        System.out.println("Max Temperature: " + wd.getMaxTemp());
        System.out.println("Mim Temperature: " + wd.getMinTemp());
        System.out.println("Feels like: " + wd.getFeelsLike());
        System.out.println("Humidity: " + wd.getHumidity());
        System.out.println("Sunrise: " + wd.getSunrise());
        System.out.println("Sunset: " + wd.getSunset());
    }
 
    
    private void showAirpollutionData(JSONObject jobj)
    {
       AirPollutionData apd = jc.convertToAirPollutionData(jobj);
       
          
        System.out.println("AIR POLLUTION DATA FETCHED: ");
        System.out.println("Air Quality Index (aqi) : " + apd.getAqi());
        System.out.println("Timestamp " + apd.getTimestamp());
        System.out.println("CO: " + apd.getCo());
        System.out.println("NH3: " + apd.getNh3());
        System.out.println("NO: " + apd.getNo());
        System.out.println("NO2: " + apd.getNo2());
        System.out.println("SO2: " + apd.getSo2());
        
    }
    
    
    private void showForcastData(JSONArray jary)
    {
        List<WeatherForecastData> forecastDataList = jc.convertToForecastDataList(jary);
        
         System.out.println("WEATHER FORECAST DATA FETCHED: ");
        for (WeatherForecastData forecastData : forecastDataList) {
            System.out.println("Date: " + forecastData.getForecastDate());
            System.out.println("Min Temperature: " + forecastData.getMinTemp());
            System.out.println("Max Temperature: " + forecastData.getMaxTemp());
            System.out.println("Humidity: " + forecastData.getHumidity());
            System.out.println("Description: " + forecastData.getDescription());
            System.out.println();
        }
    }
}
