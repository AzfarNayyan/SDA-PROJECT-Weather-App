create database SDAProject
use SDAProject
CREATE TABLE Locations (
  id INT PRIMARY KEY Not null,
  city VARCHAR(255) DEFAULT NULL,
  latitude DECIMAL(10,6) NOT NULL,
  longitude DECIMAL(10,6) NOT NULL
)
CREATE TABLE CurrentWeatherData (
  id INT PRIMARY KEY Not null,
  location_id INT NOT NULL,
  timestamp DATETIME NOT NULL,
  feels_like DECIMAL(5,2) NOT NULL,
  temperature DECIMAL(5,2) NOT NULL,
  min_temp DECIMAL(5,2) NOT NULL,
  max_temp DECIMAL(5,2) NOT NULL,
  humidity INT NOT NULL,
  sunrise TIME NOT NULL,
  sunset TIME NOT NULL,
  FOREIGN KEY (location_id) REFERENCES Locations(id)
);

CREATE TABLE AirPollutionData (
  id INT PRIMARY KEY not null,
  location_id INT NOT NULL,
  timestamp DATETIME NOT NULL,
  aqi INT NOT NULL,
  co DECIMAL(5,2) DEFAULT NULL,
  no DECIMAL(5,2) DEFAULT NULL,
  no2 DECIMAL(5,2) DEFAULT NULL,
  so2 DECIMAL(5,2) DEFAULT NULL,
  nh3 DECIMAL(5,2) DEFAULT NULL,
  FOREIGN KEY (location_id) REFERENCES Locations(id)
);
CREATE TABLE ForecastData (
  id INT PRIMARY KEY not null,
  location_id INT NOT NULL,
  forecast_date DATE NOT NULL, 
  forcast_time TIME not null,
  min_temp DECIMAL(5,2) NOT NULL,
  max_temp DECIMAL(5,2) NOT NULL,
  FOREIGN KEY (location_id) REFERENCES Locations(id)
);