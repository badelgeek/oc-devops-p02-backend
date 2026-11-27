import sys
from datetime import datetime

import config
import requests
from user_login import login

user_id = sys.argv[1] if len(sys.argv) > 1 else None

if user_id is None:
    print("UserId is mandatory")
    sys.exit(1)

url = f"{config.API_URL}/users/{user_id}"
token = login("token")

now = datetime.now()
date = f"{now.hour:02d}{now.minute:02d}{now.second:02}"

headers = {"Authorization": f"Bearer {token}"}
payload = {
    "login": f"{config.USER}.{date}",
    "firstName": f"Test {date}",
    "lastName": f"User {date}",
}

request = requests.put(url, headers=headers, json=payload)
print(request)
print(request.text)
