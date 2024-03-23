package bl.backend.weather.app.sda.project;

import java.util.List;

public class CacheManager {
    private WeatherAPIHandler apiStonks;
    private WeatherData cachedWeatherData;
    private AirPollutionData cachedAirPollutionData;
    private List<WeatherForecastData> cachedForecastDataList;

    public CacheManager(double latitude, double longitude) {
        this.apiStonks = new WeatherAPIHandler("70483fff196e58ca9a25fa29076f0f1e");
        this.cachedWeatherData = apiStonks.getCurrentWeather(latitude, longitude);
        this.cachedAirPollutionData = apiStonks.getAirPollutionData(latitude, longitude);
        this.cachedForecastDataList = apiStonks.getWeatherForecast(latitude, longitude);
    }

    public WeatherData getCachedWeatherData() {
        return cachedWeatherData;
    }

    public AirPollutionData getCachedAirPollutionData() {
        return cachedAirPollutionData;
    }

    public List<WeatherForecastData> getCachedForecastDataList() {
        return cachedForecastDataList;
    }
}
