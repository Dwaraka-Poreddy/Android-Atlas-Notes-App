"""
storage.py

Tiny helper module for reading and writing JSON files.

Nothing fancy here on purpose: every "table" in this fake backend is just
a JSON file living inside the database/ folder. This file just knows how
to load and save those files as plain Python lists/dicts.
"""

import json
import os

# database/ lives next to this file, e.g. server/database/
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
DB_DIR = os.path.join(BASE_DIR, "database")


def _full_path(filename: str) -> str:
    return os.path.join(DB_DIR, filename)


def load_json(filename: str):
    """
    Loads a JSON file from the database/ folder.
    Returns [] if the file doesn't exist yet.
    """
    path = _full_path(filename)

    if not os.path.exists(path):
        return []

    with open(path, "r", encoding="utf-8") as f:
        return json.load(f)


def file_exists(filename: str) -> bool:
    """Checks whether a given database file already exists on disk."""
    return os.path.exists(_full_path(filename))


def save_json(filename: str, data) -> None:
    """
    Saves data (list or dict) as JSON into the database/ folder.
    Overwrites whatever was there before.
    """
    path = _full_path(filename)

    # Make sure the database folder exists (it should, but just in case).
    os.makedirs(DB_DIR, exist_ok=True)

    with open(path, "w", encoding="utf-8") as f:
        json.dump(data, f, indent=2, ensure_ascii=False)
