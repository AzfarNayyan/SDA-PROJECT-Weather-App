package Terminal.Based.UI;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LocationInput {
    
    private Scanner scanner;
    
    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search?q=";
    
    public LocationInput() {
        this.scanner = new Scanner(System.in);
    }

    public double[] getCoors() {
        return getInputValues();
    }
    
    
    //----------------------------------------------------------------------
    private double[] getInputValues() {
        
        clearScreen();
        System.out.println("Choose an option:");
        System.out.println("1. Enter coordinates");
        System.out.println("2. Enter city name");
        System.out.println("3. Exit");
        System.out.print("Option: ");
        int option = scanner.nextInt();
        scanner.nextLine(); 

        if (option == 1) {
             return getInputByCoordinates();
         } else if (option == 2) {
             
             return getInputByCityName();
         } else if (option == 3) {
            System.exit(0);
         } else {
            System.out.println("Invalid option. Please try again.");
            return getInputValues();
         }
        
        return null;
    }
    
    
    private double[] getInputByCityName() {
       
        clearScreen();
        System.out.print("Enter city name: ");
        String cityName = scanner.nextLine();
        try {
            double[] coordinates = getCoordinates(cityName);
            return coordinates;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private double[] getCoordinates(String cityName) throws IOException {
        String url = NOMINATIM_URL + URLEncoder.encode(cityName, "UTF-8") + "&format=json";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        String jsonResponse = response.toString();
        double latitude = Double.parseDouble(jsonResponse.split("\"lat\":\"")[1].split("\",")[0]);
        double longitude = Double.parseDouble(jsonResponse.split("\"lon\":\"")[1].split("\",")[0]);

        return new double[]{latitude, longitude};
    }

private double[] getInputByCoordinates() {
    
    clearScreen();
    
    System.out.print("Enter latitude: ");
    double latitude = scanner.nextDouble();
    System.out.print("Enter longitude: ");
    double longitude = scanner.nextDouble();
    scanner.nextLine(); 
    return new double[]{latitude, longitude};
    
    
}

    
     private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

