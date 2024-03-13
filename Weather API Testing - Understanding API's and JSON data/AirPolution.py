import requests
import json

API_KEY = '47a324990db4f14780610c8400e1696b'
LATITUDE = '31.5497'  # Latitude of Lahore
LONGITUDE = '74.3436'  # Longitude of Lahore

BASE_URL = f'https://api.openweathermap.org/data/2.5/air_pollution?lat={LATITUDE}&lon={LONGITUDE}&appid={API_KEY}'

response = requests.get(BASE_URL).json()

# Store the response in air_pollution_data.json
with open('air_pollution_data.json', 'w') as f:
    json.dump(response, f, indent=4)
