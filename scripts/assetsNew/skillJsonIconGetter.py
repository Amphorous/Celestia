# very scuffed way to get all hsr icons (not sure if we get all)
# after the first attempt, it seems all but SPRatio are downloaded this way

import json
import os
from time import sleep

import requests

# config
BASE_URL = "https://enka.network"
JSON_FILE = "skills.json"
SAVE_DIR = "downloaded_icons"

# create folder if not exists
os.makedirs(SAVE_DIR, exist_ok=True)

# load json
with open(JSON_FILE, "r", encoding="utf-8") as f:
    data = json.load(f)

for key, value in data.items():
    icon_path = value.get("IconPath", "")

    # filter condition
    if "Icon/Icon" in icon_path:
        url = BASE_URL + icon_path

        # filename from path
        filename = os.path.basename(icon_path)
        save_path = os.path.join(SAVE_DIR, filename)

        if not os.path.exists(save_path):
            try:
                response = requests.get(url, timeout=10)
                response.raise_for_status()

                with open(save_path, "wb") as img_file:
                    img_file.write(response.content)

                print(f"Downloaded: {filename}")
                sleep(1)

            except Exception as e:
                print(f"Failed: {url} -> {e}")
        else:
            print(f"Skipping {filename}")