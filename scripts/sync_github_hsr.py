import json
import os
import requests
from pathlib import Path
import shutil

# --- CONFIG ---
REPO = "EnkaNetwork/API-docs"
BRANCH = "master"  
FILES = {
    "affixes": "store/hsr/affixes.json",
    "avatars": "store/hsr/avatars.json",
    "hsr": "store/hsr/hsr.json",
    "pfps": "store/hsr/pfps.json",
    "ranks": "store/hsr/ranks.json",
    "relics": "store/hsr/relics.json",
    "skills": "store/hsr/skills.json",
    "tree": "store/hsr/tree.json",
    "weapons": "store/hsr/weapons.json"
}
BASE_DIR = Path(__file__).parent / "assetsNew"
FILES_DIR = BASE_DIR
ARCHIVE_DIR = BASE_DIR / "archives"
META_PATH = BASE_DIR / "metadata.json"

# --- HELPERS ---
def github_file_sha(repo_path):
    """Fetch file info and return its SHA and download URL."""
    api_url = f"https://api.github.com/repos/{REPO}/contents/{repo_path}?ref={BRANCH}"
    resp = requests.get(api_url)
    resp.raise_for_status()
    data = resp.json()
    return data["sha"], data["download_url"]

def load_metadata():
    if META_PATH.exists():
        with open(META_PATH) as f:
            return json.load(f)
    else:
        # initial structure
        return {"files": [{}], "version": "v1"}

def next_version(current_version):
    prefix = "v"
    num = int(current_version.lstrip(prefix))
    return f"{prefix}{num+1}"

def save_metadata(meta):
    with open(META_PATH, "w") as f:
        json.dump(meta, f, indent=2)

def download_file(url, local_path):
    resp = requests.get(url)
    resp.raise_for_status()
    local_path.parent.mkdir(parents=True, exist_ok=True)
    with open(local_path, "wb") as f:
        f.write(resp.content)

# --- MAIN SYNC ---
def sync():
    meta = load_metadata()
    current_version = meta.get("version", "v1")
    file_meta = meta["files"][0]
    changed_files = []

    print(f"Checking for updates (current version {current_version})...")

    for name, path in FILES.items():
        remote_sha, download_url = github_file_sha(path)
        local_sha = file_meta.get(name, {}).get("sha")

        if not local_sha or remote_sha != local_sha:
            changed_files.append((name, path, remote_sha, download_url))
            print(f"Change detected in {name}: {local_sha} → {remote_sha}")
        else:
            print(f"No change in {name}")

    if not changed_files:
        print("No updates detected.")
        return

    # --- Archive old version ---
    archive_path = ARCHIVE_DIR / current_version
    archive_path.mkdir(parents=True, exist_ok=True)
    print(f"Archiving old files to {archive_path}")

    # Move metadata
    if META_PATH.exists():
        shutil.move(str(META_PATH), archive_path / "metadata.json")
    # Move changed files
    for name, _, _, _ in changed_files:
        fpath = FILES_DIR / f"{name}.json"
        if fpath.exists():
            shutil.move(str(fpath), archive_path / f"{name}.json")

    # --- Download new files ---
    new_version = next_version(current_version)
    for name, path, remote_sha, download_url in changed_files:
        local_path = FILES_DIR / f"{name}.json"
        print(f"Downloading new {name}.json ...")
        download_file(download_url, local_path)
        file_meta[name] = {"sha": remote_sha}

    meta["files"][0] = file_meta
    meta["version"] = new_version
    save_metadata(meta)

    print(f"Updated to {new_version}")

if __name__ == "__main__":
    sync()
