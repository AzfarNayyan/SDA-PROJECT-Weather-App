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


CREATE TABLE DeleteRecordsEvent (
    id INT AUTO_INCREMENT PRIMARY KEY,
    table_name VARCHAR(50) NOT NULL,
    record_id INT NOT NULL,
    delete_time DATETIME NOT NULL
);



/*
DELIMITER //
CREATE TRIGGER DeleteAfter24Hours
AFTER INSERT ON CurrentWeatherData
FOR EACH ROW
BEGIN
    DECLARE delete_time DATETIME;
    SET delete_time = DATE_ADD(NOW(), INTERVAL 1 DAY);
    INSERT INTO DeleteRecordsEvent (table_name, record_id, delete_time)
    VALUES ('CurrentWeatherData', NEW.id, delete_time);
END;
//

DELIMITER //

CREATE TRIGGER DeleteAfter24Hours_AirPollution
AFTER INSERT ON AirPollutionData
FOR EACH ROW
BEGIN
    DECLARE delete_time DATETIME;
    SET delete_time = DATE_ADD(NOW(), INTERVAL 1 DAY);
    INSERT INTO DeleteRecordsEvent (table_name, record_id, delete_time)
    VALUES ('AirPollutionData', NEW.id, delete_time);
END;
//

DELIMITER //


CREATE TRIGGER DeleteAfter24Hours_Forecast
AFTER INSERT ON ForecastData
FOR EACH ROW
BEGIN
    DECLARE delete_time DATETIME;
    SET delete_time = DATE_ADD(NOW(), INTERVAL 1 DAY);
    INSERT INTO DeleteRecordsEvent (table_name, record_id, delete_time)
    VALUES ('ForecastData', NEW.id, delete_time);
END;
//

DELIMITER ;

DELIMITER //

CREATE EVENT DeleteExpiredRecords
ON SCHEDULE EVERY 1 HOUR
DO
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE record_id INT;
    DECLARE table_name VARCHAR(50);
    DECLARE cur CURSOR FOR SELECT table_name, record_id FROM DeleteRecordsEvent WHERE delete_time <= NOW();
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO table_name, record_id;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        SET @sql = CONCAT('DELETE FROM ', table_name, ' WHERE id = ', record_id);
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;
    CLOSE cur;
    DELETE FROM DeleteRecordsEvent WHERE delete_time <= NOW();
END;
//

DELIMITER ;
*/
SET GLOBAL event_scheduler = ON;
CREATE EVENT e_clear_sessions
ON SCHEDULE EVERY 120 SECOND
STARTS CURRENT_TIMESTAMP
ENDS CURRENT_TIMESTAMP + INTERVAL 24 HOUR
COMMENT 'Clears out outdated locations every 30 seconds for 24 hours.'
DO
DELETE FROM Locations
WHERE id IN (
    SELECT DISTINCT location_id
    FROM AirPollutionData
    WHERE TIMESTAMPDIFF(SECOND, timestamp, NOW()) > 300
);
