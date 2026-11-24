import config
import requests
from user_login import login

url = f"{config.API_URL}/users"
token = login("token")

headers = {"Authorization": f"Bearer {token}"}

request = requests.get(url, headers=headers)
print(request)
print(request.text)
