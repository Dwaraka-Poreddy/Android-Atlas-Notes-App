"""
auth.py

Tiny helper module for password hashing and JWT access/refresh tokens.

This backs the /auth/* endpoints in routes/auth.py and gives every other
resource a way to require a logged-in user via the get_current_user
dependency below.
"""

from datetime import datetime, timedelta, timezone

from fastapi import Header, HTTPException
from jose import JWTError, jwt
from passlib.context import CryptContext

import storage

# Local-only learning server — hardcoding this is fine here, never do this in production.
SECRET_KEY = "a1b2c3d4-not-a-real-secret-just-for-local-learning-9f8e7d6c"
ALGORITHM = "HS256"
ACCESS_TOKEN_EXPIRE_MINUTES = 15
REFRESH_TOKEN_EXPIRE_DAYS = 30

USERS_FILE = "users.json"

_pwd_context = CryptContext(schemes=["bcrypt"], deprecated="auto")


def hash_password(password: str) -> str:
    return _pwd_context.hash(password)


def verify_password(plain: str, hashed: str) -> bool:
    return _pwd_context.verify(plain, hashed)


def _create_token(user_id: str, token_type: str, expires_delta: timedelta) -> str:
    expire = datetime.now(timezone.utc) + expires_delta
    payload = {"sub": user_id, "type": token_type, "exp": expire}
    return jwt.encode(payload, SECRET_KEY, algorithm=ALGORITHM)


def create_access_token(user_id: str) -> str:
    return _create_token(user_id, "access", timedelta(minutes=ACCESS_TOKEN_EXPIRE_MINUTES))


def create_refresh_token(user_id: str) -> str:
    return _create_token(user_id, "refresh", timedelta(days=REFRESH_TOKEN_EXPIRE_DAYS))


def decode_token(token: str) -> dict:
    """Decodes and verifies a JWT. Raises jose.JWTError on invalid/expired tokens."""
    return jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])


def get_current_user(authorization: str = Header(None)) -> dict:
    """
    FastAPI dependency that validates the Authorization: Bearer <token>
    header and returns the current user dict (minus hashedPassword).
    """
    if not authorization:
        raise HTTPException(status_code=401, detail="Missing authorization header")

    parts = authorization.split()
    if len(parts) != 2 or parts[0].lower() != "bearer":
        raise HTTPException(status_code=401, detail="Invalid or expired token")

    token = parts[1]

    try:
        payload = decode_token(token)
    except JWTError:
        raise HTTPException(status_code=401, detail="Invalid or expired token")

    if payload.get("type") != "access":
        raise HTTPException(status_code=401, detail="Invalid or expired token")

    user_id = payload.get("sub")

    users = storage.load_json(USERS_FILE)
    for user in users:
        if user["id"] == user_id:
            return {k: v for k, v in user.items() if k != "hashedPassword"}

    raise HTTPException(status_code=401, detail="User not found")
