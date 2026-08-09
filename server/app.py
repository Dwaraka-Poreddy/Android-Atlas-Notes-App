"""
app.py

Entry point for the fake shopping backend.

Run it with:
    uvicorn app:app --host 0.0.0.0 --port 8000 --reload

Then open http://<your-lan-ip>:8000/docs for Swagger UI.
"""

import random
import time
from datetime import datetime, timezone

from fastapi import FastAPI, Request
from fastapi.middleware.cors import CORSMiddleware
from starlette.responses import JSONResponse

import storage
from models import generate_initial_products
from schemas import CategoriesResponse, BrandsResponse
from routes import products, wishlist, cart, auth, notes

app = FastAPI(
    title="Fake Shopping API",
    description="A tiny local JSON-file backend for Android architecture learning.",
    version="1.0.0",
)

# ---------------------------------------------------------------------------
# CORS - wide open since this only ever runs on your own laptop for learning.
# ---------------------------------------------------------------------------
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


# ---------------------------------------------------------------------------
# Logging middleware - prints method, URL and response time for every request.
# ---------------------------------------------------------------------------
@app.middleware("http")
async def log_requests(request: Request, call_next):
    start_time = time.time()
    response = await call_next(request)
    duration_ms = (time.time() - start_time) * 1000

    print(f"{request.method} {request.url.path} -> {response.status_code} ({duration_ms:.1f} ms)")

    return response


# ---------------------------------------------------------------------------
# Make sure the JSON "database" files exist on first run.
# ---------------------------------------------------------------------------
def _seed_if_missing():
    if not storage.load_json("products.json"):
        storage.save_json("products.json", generate_initial_products())

    # wishlist.json / cart.json default to [] via storage.load_json,
    # but writing them out explicitly makes the files show up on disk
    # right away instead of only after the first write.
    if not storage.file_exists("wishlist.json"):
        storage.save_json("wishlist.json", [])

    if not storage.file_exists("cart.json"):
        storage.save_json("cart.json", [])

    if not storage.file_exists("users.json"):
        storage.save_json("users.json", [])

    if not storage.file_exists("refresh_tokens.json"):
        storage.save_json("refresh_tokens.json", [])

    if not storage.file_exists("notes.json"):
        storage.save_json("notes.json", [])


_seed_if_missing()


# ---------------------------------------------------------------------------
# Feature routers
# ---------------------------------------------------------------------------
app.include_router(products.router, tags=["Products"])
app.include_router(wishlist.router, tags=["Wishlist"])
app.include_router(cart.router, tags=["Cart"])
app.include_router(auth.router, tags=["Auth"])
app.include_router(notes.router, tags=["Notes"])


# ---------------------------------------------------------------------------
# Bonus endpoints
# ---------------------------------------------------------------------------
@app.get("/categories", response_model=CategoriesResponse, tags=["Meta"])
def get_categories():
    all_products = storage.load_json("products.json")
    categories = sorted({p["category"] for p in all_products})
    return {"categories": categories}


@app.get("/brands", response_model=BrandsResponse, tags=["Meta"])
def get_brands():
    all_products = storage.load_json("products.json")
    brands = sorted({p["brand"] for p in all_products})
    return {"brands": brands}


# ---------------------------------------------------------------------------
# Debug endpoints - used only to test Android error handling / retries /
# loading states. Not "real" API endpoints.
# ---------------------------------------------------------------------------
@app.get("/debug/slow", tags=["Debug"])
async def debug_slow():
    import asyncio
    await asyncio.sleep(5)
    return {"message": "This response was delayed by 5 seconds"}


@app.get("/debug/error", tags=["Debug"])
def debug_error():
    return JSONResponse(status_code=500, content={"detail": "Simulated server error"})


@app.get("/debug/offline", tags=["Debug"])
def debug_offline():
    return JSONResponse(status_code=503, content={"detail": "Simulated service unavailable"})


@app.get("/debug/randomFailure", tags=["Debug"])
def debug_random_failure():
    if random.random() < 0.3:
        return JSONResponse(status_code=500, content={"detail": "Random simulated failure"})
    return {"message": "Success this time"}


@app.get("/debug/reset", tags=["Debug"])
def debug_reset():
    """Resets products, wishlist, cart, notes, users, and refresh tokens
    back to their initial (empty, for everything but products) state."""
    storage.save_json("products.json", generate_initial_products())
    storage.save_json("wishlist.json", [])
    storage.save_json("cart.json", [])
    storage.save_json("notes.json", [])
    storage.save_json("users.json", [])
    storage.save_json("refresh_tokens.json", [])
    return {"message": "All data has been reset to its initial state"}


@app.post("/debug/simulateConflict/notes/{note_id}", tags=["Debug"])
def debug_simulate_conflict_notes(note_id: str):
    """
    Bumps a note's updatedAt to "now", simulating another device having
    just written to it. Lets you deterministically trigger the conflict
    path in POST /notes/sync without needing two real devices racing.
    """
    notes = storage.load_json("notes.json")

    for note in notes:
        if note["id"] == note_id:
            note["updatedAt"] = datetime.now(timezone.utc).isoformat()
            storage.save_json("notes.json", notes)
            return note

    return JSONResponse(status_code=404, content={"detail": f"Note {note_id} not found"})


@app.get("/", tags=["Meta"])
def root():
    return {
        "message": "Fake Shopping API is running",
        "docs": "/docs",
    }
