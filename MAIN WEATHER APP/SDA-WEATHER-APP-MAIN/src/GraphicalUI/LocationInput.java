package GraphicalUI;
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
        
        
        
        return null;
    }
    
    
    public double[] getCoordinates(String cityName) throws IOException {
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
 
    
     private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

