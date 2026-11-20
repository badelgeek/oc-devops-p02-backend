import config
import requests

url = f"{config.API_URL}/register"
headers = {"Content-Type": "application/json"}
payload = {
    "firstName": "Test",
    "lastName": "User",
    "login": config.USER,
    "password": config.PASSWORD,
}

request = requests.post(url, json=payload, headers=headers)
print(request)
print(request.text)
