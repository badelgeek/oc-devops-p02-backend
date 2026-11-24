import sys

import config
import requests
from user_login import login

user_id = sys.argv[1] if len(sys.argv) > 1 else None

if user_id is None:
    print("UserId is mandatory")
    sys.exit(1)

url = f"{config.API_URL}/users/{user_id}"
token = login("token")

headers = {"Authorization": f"Bearer {token}"}

request = requests.delete(url, headers=headers)
print(request)
print(request.text)
