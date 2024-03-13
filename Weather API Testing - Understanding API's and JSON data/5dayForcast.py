import requests
import json

BASE_URL = 'https://api.openweathermap.org/data/2.5/forecast?'
API_KEY = '47a324990db4f14780610c8400e1696b'
LATITUDE = '31.5497'  # Latitude of Lahore
LONGITUDE = '74.3436'  # Longitude of Lahore

url = f"{BASE_URL}lat={LATITUDE}&lon={LONGITUDE}&appid={API_KEY}"

response = requests.get(url).json()

# Store the response in weather_data.json
with open('5_daysForcastSampleData.json', 'w') as f:
    json.dump(response, f, indent=4)
