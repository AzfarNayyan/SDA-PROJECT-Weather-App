create database SDAProject
use SDAProject

-- Drop the existing tables if they exist

-- Create the Locations table with an auto-increment primary key
CREATE TABLE Locations (
  id INT AUTO_INCREMENT PRIMARY KEY,
  city VARCHAR(255) DEFAULT NULL,
  latitude DECIMAL(10,6) NOT NULL,
  longitude DECIMAL(10,6) NOT NULL
);

-- Create the CurrentWeatherData table with a foreign key reference to Locations
CREATE TABLE CurrentWeatherData (
  id INT AUTO_INCREMENT PRIMARY KEY,
  location_id INT NOT NULL,
  feels_like DECIMAL(5,2) NOT NULL,
  temperature DECIMAL(5,2) NOT NULL,
  min_temp DECIMAL(5,2) NOT NULL,
  max_temp DECIMAL(5,2) NOT NULL,
  humidity INT NOT NULL,
  sunrise TIME NOT NULL,
  sunset TIME NOT NULL,
  city VARCHAR(30) NOT NULL,
  FOREIGN KEY (location_id) REFERENCES Locations(id) ON DELETE CASCADE
);

-- Create the AirPollutionData table with a foreign key reference to Locations
CREATE TABLE AirPollutionData (
  id INT AUTO_INCREMENT PRIMARY KEY,
  location_id INT NOT NULL,
  timestamp DATETIME NOT NULL,
  aqi INT NOT NULL,
  co DECIMAL(5,2) DEFAULT NULL,
  no DECIMAL(5,2) DEFAULT NULL,
  no2 DECIMAL(5,2) DEFAULT NULL,
  so2 DECIMAL(5,2) DEFAULT NULL,
  nh3 DECIMAL(5,2) DEFAULT NULL,
  city VARCHAR(20) DEFAULT NULL,
  FOREIGN KEY (location_id) REFERENCES Locations(id) ON DELETE CASCADE
);

-- Create the ForecastData table with a foreign key reference to Locations
CREATE TABLE ForecastData (
  id INT AUTO_INCREMENT PRIMARY KEY,
  location_id INT NOT NULL,
  forecast_date DATE NOT NULL, 
  min_temp DECIMAL(5,2) NOT NULL,
  max_temp DECIMAL(5,2) NOT NULL,
  humidity INT NOT NULL,
  description VARCHAR(50) NOT NULL,
  FOREIGN KEY (location_id) REFERENCES Locations(id) ON DELETE CASCADE
);
ALTER TABLE AirPollutionData MODIFY COLUMN co DECIMAL(10,2);

ALTER TABLE Locations
MODIFY latitude DECIMAL(11, 9),
MODIFY longitude DECIMAL(12, 9);


select * from Locations;
select * from CurrentWeatherData;
select * from AirPollutionData;
select * from ForecastData


SET sql_safe_updates = 0;




SET GLOBAL event_scheduler = ON;
CREATE EVENT e_clear_sessions
ON SCHEDULE EVERY 86400 SECOND
COMMENT 'Clears out outdated locations every 30 seconds for 24 hours.'
DO
DELETE FROM Locations
WHERE id IN (
    SELECT DISTINCT location_id
    FROM AirPollutionData
    WHERE TIMESTAMPDIFF(SECOND, timestamp, NOW()) > 300
);
