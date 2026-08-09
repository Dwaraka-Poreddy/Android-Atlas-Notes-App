# Fake Shopping API

A tiny, local-only backend built to support Android architecture learning
(Retrofit, Room, Repository pattern, offline-first, pagination, error
handling, WorkManager, etc). No database — just JSON files on disk and a
FastAPI server, with a minimal JWT auth layer in front of the notes
resource.

## Folder structure

```
server/
    app.py              # FastAPI app, CORS, logging, debug endpoints
    auth.py              # Password hashing + JWT create/decode, get_current_user dependency
    models.py           # Generates the initial 40 fake products
    schemas.py           # Pydantic request/response schemas
    storage.py          # Tiny JSON read/write helpers
    routes/
        products.py      # GET /products, GET /products/{id}
        wishlist.py      # GET/POST/DELETE /wishlist
        cart.py          # GET/POST/PUT/DELETE /cart
        auth.py           # POST /auth/register, /login, /refresh, /logout, GET /auth/me
        notes.py          # GET/POST /notes, POST /notes/sync
    database/
        products.json    # Created automatically on first run
        wishlist.json    # Created automatically on first run
        cart.json        # Created automatically on first run
        users.json        # Created automatically on first run
        refresh_tokens.json  # Created automatically on first run
        notes.json         # Created automatically on first run
    requirements.txt
    README.md
```

## Requirements

- Python 3.12+ (3.10+ will also work fine)

## Installation

```bash
cd server

# create a virtual environment
python3 -m venv .venv

# activate it
source .venv/bin/activate        # macOS / Linux
.venv\Scripts\activate           # Windows

# install dependencies
pip install -r requirements.txt
```

## Running the server

```bash
uvicorn app:app --host 0.0.0.0 --port 8000 --reload
```

The first time it runs, it will automatically create `database/products.json`
(pre-filled with 40 fake products), `database/wishlist.json`,
`database/cart.json`, `database/users.json`, `database/refresh_tokens.json`,
and `database/notes.json`.

Find your laptop's LAN IP (so your Android emulator/phone can reach it):

- macOS/Linux: `ifconfig` or `ip addr`
- Windows: `ipconfig`

Then point your Android app at something like:

```
http://192.168.1.23:8000
```

> Note: the Android emulator can't reach `localhost` on your machine —
> use `http://10.0.2.2:8000` for the default Android Studio emulator, or
> your real LAN IP for a physical device.

Swagger UI (interactive API docs) is available automatically at:

```
http://192.168.1.23:8000/docs
```

## API Reference

### Products

| Method | Endpoint | Description |
|---|---|---|
| GET | `/products` | List products. Supports `search`, `category`, `sort`, `page`, `pageSize` |
| GET | `/products/{id}` | Get a single product |

Examples:

```
GET /products
GET /products?page=1&pageSize=20
GET /products?search=smartphone
GET /products?category=Electronics
GET /products?sort=price       # ascending
GET /products?sort=-price      # descending (prefix with -)
```

Example response for `GET /products?page=1&pageSize=1`:

```json
{
  "total": 40,
  "page": 1,
  "pageSize": 1,
  "products": [
    {
      "id": 1,
      "title": "Aurora X12 Smartphone",
      "description": "6.5-inch AMOLED display, 128GB storage, triple camera setup.",
      "price": 699.0,
      "discountPercentage": 12.0,
      "rating": 4.5,
      "stock": 45,
      "brand": "Aurora",
      "category": "Electronics",
      "thumbnail": "https://picsum.photos/seed/Electronics-1/400/400",
      "images": [
        "https://picsum.photos/seed/Electronics-1/600/600",
        "https://picsum.photos/seed/Electronics-1-alt/600/600"
      ]
    }
  ]
}
```

### Wishlist

| Method | Endpoint | Description |
|---|---|---|
| GET | `/wishlist` | Get wishlist product ids |
| POST | `/wishlist/{productId}` | Add a product to the wishlist |
| DELETE | `/wishlist/{productId}` | Remove a product from the wishlist |

Response shape:

```json
{ "productIds": [1, 7, 12] }
```

### Cart

| Method | Endpoint | Body | Description |
|---|---|---|---|
| GET | `/cart` | - | Get the cart |
| POST | `/cart` | `{ "productId": 1, "quantity": 2 }` | Add item (increases quantity if it already exists) |
| PUT | `/cart/{productId}` | `{ "quantity": 5 }` | Set the quantity for an item |
| DELETE | `/cart/{productId}` | - | Remove one item |
| DELETE | `/cart` | - | Clear the whole cart |

Response shape:

```json
{ "items": [ { "productId": 1, "quantity": 2 } ] }
```

### Auth

| Method | Endpoint | Body | Description |
|---|---|---|---|
| POST | `/auth/register` | `{ "email": "...", "password": "..." }` | Create a user, returns an access + refresh token pair |
| POST | `/auth/login` | `{ "email": "...", "password": "..." }` | Returns an access + refresh token pair |
| POST | `/auth/refresh` | `{ "refreshToken": "..." }` | Returns a new access token (refresh token is not rotated) |
| POST | `/auth/logout` | `{ "refreshToken": "..." }` | Revokes the given refresh token |
| GET | `/auth/me` | - | Returns the current user (requires `Authorization` header) |

Access tokens are short-lived (15 minutes) JWTs. Send them on every
authenticated request as:

```
Authorization: Bearer <accessToken>
```

Example `POST /auth/register` request:

```json
{ "email": "ada@example.com", "password": "hunter2" }
```

Response (`201`):

```json
{
  "accessToken": "eyJhbGciOi...",
  "refreshToken": "eyJhbGciOi..."
}
```

Example `GET /auth/me` response:

```json
{
  "id": "b6f1a2e0-9c3d-4b7a-8e2f-1a2b3c4d5e6f",
  "email": "ada@example.com",
  "createdAt": "2026-08-08T09:15:00.000000+00:00"
}
```

### Notes

| Method | Endpoint | Description |
|---|---|---|
| GET | `/notes` | List the current user's notes. Supports `search`, `page`, `pageSize` |
| GET | `/notes/{id}` | Get a single note owned by the current user |
| POST | `/notes/sync` | Batch upsert/delete with last-write-wins conflict resolution |

All `/notes*` endpoints require the `Authorization: Bearer <accessToken>`
header. `GET /notes` returns a flat `{ "notes": [...] }` (no
`total`/`page` wrapper, unlike `/products`).

`POST /notes/sync` is the endpoint an offline-first client's sync queue
calls after coming back online. It takes a list of local operations and
applies each one independently — one bad operation in the batch doesn't
fail the rest. For an `UPSERT` on an existing note, the server compares
the operation's `clientUpdatedAt` against the note's current
`updatedAt`: if the client was current or newer, the change is applied;
if the note has moved forward since the client last saw it (e.g. another
of the user's devices wrote to it first), the client's change is
rejected as a `"conflict"` and the server's current version is returned
so the client can adopt it as the winner.

Example request with three operations — creating a note, editing an
existing one, and deleting another:

```json
{
  "operations": [
    {
      "localId": "local-1",
      "operationType": "UPSERT",
      "noteId": null,
      "title": "Grocery list",
      "content": "Eggs, milk, bread",
      "folderOrLabel": "Personal",
      "clientUpdatedAt": "2026-08-08T09:00:00+00:00"
    },
    {
      "localId": "local-2",
      "operationType": "UPSERT",
      "noteId": "3f9c1e2a-...",
      "title": "Meeting notes",
      "content": "Updated agenda for Thursday",
      "folderOrLabel": "Work",
      "clientUpdatedAt": "2026-08-08T08:55:00+00:00"
    },
    {
      "localId": "local-3",
      "operationType": "DELETE",
      "noteId": "7bd0a44f-...",
      "clientUpdatedAt": "2026-08-08T09:00:00+00:00"
    }
  ]
}
```

Corresponding response — `local-2` lost the race because the note had
already been updated on the server after the client's `clientUpdatedAt`:

```json
{
  "results": [
    {
      "localId": "local-1",
      "status": "applied",
      "note": {
        "id": "9a1b2c3d-...",
        "userId": "b6f1a2e0-...",
        "title": "Grocery list",
        "content": "Eggs, milk, bread",
        "folderOrLabel": "Personal",
        "updatedAt": "2026-08-08T09:00:01.123456+00:00",
        "deleted": false
      }
    },
    {
      "localId": "local-2",
      "status": "conflict",
      "note": {
        "id": "3f9c1e2a-...",
        "userId": "b6f1a2e0-...",
        "title": "Meeting notes (edited on laptop)",
        "content": "Final agenda",
        "folderOrLabel": "Work",
        "updatedAt": "2026-08-08T08:58:00.000000+00:00",
        "deleted": false
      }
    },
    {
      "localId": "local-3",
      "status": "applied",
      "note": {
        "id": "7bd0a44f-...",
        "userId": "b6f1a2e0-...",
        "title": "Old note",
        "content": "...",
        "folderOrLabel": null,
        "updatedAt": "2026-08-08T09:00:01.456789+00:00",
        "deleted": true
      }
    }
  ]
}
```

### Meta

| Method | Endpoint | Description |
|---|---|---|
| GET | `/categories` | List of distinct categories |
| GET | `/brands` | List of distinct brands |

### Debug endpoints (for Android testing only)

| Method | Endpoint | Behavior |
|---|---|---|
| GET | `/debug/slow` | Waits 5 seconds before responding |
| GET | `/debug/error` | Always returns HTTP 500 |
| GET | `/debug/offline` | Always returns HTTP 503 |
| GET | `/debug/randomFailure` | 30% chance of HTTP 500, otherwise 200 |
| GET | `/debug/reset` | Resets products/wishlist/cart/notes/users/refresh tokens to their initial state (also logs everyone out) |
| POST | `/debug/simulateConflict/notes/{id}` | Bumps a note's `updatedAt` to "now", simulating another device having just written to it — use this to deterministically trigger the `"conflict"` path in `POST /notes/sync` |

These are meant to help you test things like Retrofit retry logic,
WorkManager retry policies, loading states, and error handling UI — they
are not part of the "real" shop API.

### Error responses

All errors return a plain JSON body:

```json
{ "detail": "Product 999 not found" }
```

- `404` — resource not found (unknown product id, item not in cart/wishlist)
- `400` — bad input (e.g. `quantity <= 0`)
- `500` — only from the debug endpoints (or truly unexpected server errors)

## Notes on design choices

- **No database, no ORM.** Everything lives in `database/*.json` and is
  read/written on every request through `storage.py`. This is
  deliberately simple, not scalable — perfect for local Android
  development, not for production.
- **JWT auth (access + refresh).** Passwords are hashed with `passlib`
  (bcrypt). `/auth/login` and `/auth/register` issue a short-lived access
  token (15 min) plus a longer-lived refresh token (30 days). Refresh
  tokens are tracked in `database/refresh_tokens.json`, which acts as an
  allowlist — a refresh token only works if it's both a validly-signed,
  unexpired JWT *and* still present in that file, which is what lets
  `/auth/logout` actually revoke it rather than just relying on
  expiry. `products`, `wishlist`, and `cart` are intentionally left
  unauthenticated for now — user-scoping those is a separate future
  task; only `notes` currently requires a logged-in user.
- **Stable response shapes.** `ProductListResponse`, `CartResponse`, and
  `WishlistResponse` wrap their lists in a named object (`products`,
  `items`, `productIds`) rather than returning a bare JSON array, so new
  fields (like `total` or future metadata) can be added without breaking
  existing Android DTOs.
