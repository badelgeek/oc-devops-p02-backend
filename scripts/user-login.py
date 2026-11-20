import config
import requests

url = f"{config.API_URL}/login"
payload = {
    "login": config.USER,
    "password": config.PASSWORD,
}

request = requests.post(url, data=payload)
print(request)
print(request.text)
