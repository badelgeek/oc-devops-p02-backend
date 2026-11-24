import sys

import config
import requests

mode = sys.argv[1] if len(sys.argv) > 1 else None


def login(mode):
    url = f"{config.API_URL}/login"
    headers = {"Content-Type": "application/json"}
    payload = {
        "login": config.USER,
        "password": config.PASSWORD,
    }

    request = requests.post(url, json=payload, headers=headers)

    if mode == "token":
        return request.json()["token"]

    print(request)
    print(request.text)


if __name__ == "__main__":
    login(mode)
