package bl.backend.weather.app.sda.project;


public class Location {

    private double latitude;
    private double longitude;
    private String cityName;
    private String countryName;
    
    public Location()
    {
        
    }

    
    public Location(double latitude, double longitude, String cityName, String countryName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityName = cityName;
        this.countryName = countryName;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}

