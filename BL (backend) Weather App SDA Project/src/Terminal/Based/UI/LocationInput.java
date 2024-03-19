package Terminal.Based.UI;
import java.util.Scanner;

public class LocationInput {
    
    private Scanner scanner;
    
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
    // Here you need to implement logic to convert the city name to coordinates
    // For demonstration purposes, I'm assuming fixed coordinates
    double latitude = 31.5497;
    double longitude = 74.3436;
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

