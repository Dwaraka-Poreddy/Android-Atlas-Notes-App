"""
routes/auth.py

Minimal JWT-based auth: register, login, refresh, logout, and "who am I".

Users live in users.json. Refresh tokens are tracked in refresh_tokens.json
as an allowlist - a refresh token is only honored if it's both a validly
signed, non-expired JWT AND still present in that file. Logout removes it
from the file, which is what actually revokes it.
"""

import uuid
from datetime import datetime, timezone

from fastapi import APIRouter, Depends, HTTPException

import storage
from auth import (
    create_access_token,
    create_refresh_token,
    decode_token,
    get_current_user,
    hash_password,
    verify_password,
)
from jose import JWTError
from schemas import (
    AccessTokenResponse,
    RefreshRequest,
    TokenPair,
    UserLogin,
    UserRegister,
    UserResponse,
)

router = APIRouter()

USERS_FILE = "users.json"
REFRESH_TOKENS_FILE = "refresh_tokens.json"


def _issue_token_pair(user_id: str) -> dict:
    access_token = create_access_token(user_id)
    refresh_token = create_refresh_token(user_id)

    refresh_tokens = storage.load_json(REFRESH_TOKENS_FILE)
    refresh_tokens.append({
        "token": refresh_token,
        "userId": user_id,
        "createdAt": datetime.now(timezone.utc).isoformat(),
    })
    storage.save_json(REFRESH_TOKENS_FILE, refresh_tokens)

    return {"accessToken": access_token, "refreshToken": refresh_token}


@router.post("/auth/register", response_model=TokenPair, status_code=201)
def register(body: UserRegister):
    users = storage.load_json(USERS_FILE)

    if any(u["email"] == body.email for u in users):
        raise HTTPException(status_code=400, detail="Email already registered")

    user = {
        "id": str(uuid.uuid4()),
        "email": body.email,
        "hashedPassword": hash_password(body.password),
        "createdAt": datetime.now(timezone.utc).isoformat(),
    }
    users.append(user)
    storage.save_json(USERS_FILE, users)

    return _issue_token_pair(user["id"])


@router.post("/auth/login", response_model=TokenPair)
def login(body: UserLogin):
    users = storage.load_json(USERS_FILE)

    user = next((u for u in users if u["email"] == body.email), None)

    if not user or not verify_password(body.password, user["hashedPassword"]):
        raise HTTPException(status_code=401, detail="Invalid email or password")

    return _issue_token_pair(user["id"])


@router.post("/auth/refresh", response_model=AccessTokenResponse)
def refresh(body: RefreshRequest):
    try:
        payload = decode_token(body.refreshToken)
    except JWTError:
        raise HTTPException(status_code=401, detail="Invalid or expired refresh token")

    if payload.get("type") != "refresh":
        raise HTTPException(status_code=401, detail="Invalid or expired refresh token")

    refresh_tokens = storage.load_json(REFRESH_TOKENS_FILE)
    if not any(rt["token"] == body.refreshToken for rt in refresh_tokens):
        raise HTTPException(status_code=401, detail="Invalid or expired refresh token")

    access_token = create_access_token(payload["sub"])
    return {"accessToken": access_token}


@router.post("/auth/logout")
def logout(body: RefreshRequest):
    refresh_tokens = storage.load_json(REFRESH_TOKENS_FILE)
    remaining = [rt for rt in refresh_tokens if rt["token"] != body.refreshToken]

    if len(remaining) != len(refresh_tokens):
        storage.save_json(REFRESH_TOKENS_FILE, remaining)

    return {"message": "Logged out"}


@router.get("/auth/me", response_model=UserResponse)
def me(current_user: dict = Depends(get_current_user)):
    return current_user
