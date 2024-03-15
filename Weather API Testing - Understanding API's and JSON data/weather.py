#python API code
#For each API we have 2 ways (by city name),(By long and lat)
#for example :
#1) Current Weather Data
#--------By City --------
import datetime as dt
import requests
import json

BASE_URL = 'https://api.openweathermap.org/data/2.5/weather?'
API_KEY = '70483fff196e58ca9a25fa29076f0f1e'
CITY = 'Lahore'


url = f"{BASE_URL}q={CITY}&appid={API_KEY}"  # Modified URL construction for better readability*/


response = requests.get(url).json()  # Call .json() method to get JSON response content
with open('weather_data.json', 'w') as f:
    json.dump(response, f)


def kelvin_to_celsius(temp_kelvin):
    temp_celsius = round(temp_kelvin - 273.15, 1)
    return temp_celsius

degree =kelvin_to_celsius(response['main']['temp'])
fl=response['main']['feels_like']
fl =kelvin_to_celsius(response['main']['feels_like'])
print(f"temperatue in lahore:{degree} °C  and feels like {fl}°C")


##----By Long & Lat----
import datetime as dt
import requests
import json

BASE_URL = 'https://api.openweathermap.org/data/2.5/weather?'
API_KEY = '70483fff196e58ca9a25fa29076f0f1e'
CITY = 'Lahore'
LATITUDE = '31.5497'  # Latitude of Lahore
LONGITUDE = '74.3436'  # Longitude of Lahore


url = f"{BASE_URL}lat={LATITUDE}&lon={LONGITUDE}&appid={API_KEY}"

response = requests.get(url).json()  # Call .json() method to get JSON response content
with open('weather_data.json', 'w') as f:
    json.dump(response, f)


def kelvin_to_celsius(temp_kelvin):
    temp_celsius = round(temp_kelvin - 273.15, 1)
    return temp_celsius

degree =kelvin_to_celsius(response['main']['temp'])
fl=response['main']['feels_like']
fl =kelvin_to_celsius(response['main']['feels_like'])
print(f"temperatue in lahore:{degree} °C  and feels like {fl}°C")
