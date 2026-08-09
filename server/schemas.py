"""
schemas.py

Pydantic models used to validate incoming request bodies and to describe
API responses in the auto-generated Swagger docs.

These are kept intentionally simple and separate from the "database"
dicts in database/*.json — that's why they're called schemas and not
models. A DTO-shaped schema here maps nicely to a Retrofit DTO on the
Android side.
"""

from typing import List, Optional
from pydantic import BaseModel


class Product(BaseModel):
    id: int
    title: str
    description: str
    price: float
    discountPercentage: float
    rating: float
    stock: int
    brand: str
    category: str
    thumbnail: str
    images: List[str]


class ProductListResponse(BaseModel):
    total: int
    page: int
    pageSize: int
    products: List[Product]


class WishlistResponse(BaseModel):
    productIds: List[int]


class CartItem(BaseModel):
    productId: int
    quantity: int


class CartItemCreate(BaseModel):
    productId: int
    quantity: int = 1


class CartItemUpdate(BaseModel):
    quantity: int


class CartResponse(BaseModel):
    items: List[CartItem]


class ErrorResponse(BaseModel):
    detail: str


class CategoriesResponse(BaseModel):
    categories: List[str]


class BrandsResponse(BaseModel):
    brands: List[str]


# ---------------------------------------------------------------------------
# Auth
# ---------------------------------------------------------------------------
class UserRegister(BaseModel):
    email: str
    password: str


class UserLogin(BaseModel):
    email: str
    password: str


class TokenPair(BaseModel):
    accessToken: str
    refreshToken: str


class RefreshRequest(BaseModel):
    refreshToken: str


class AccessTokenResponse(BaseModel):
    accessToken: str


class UserResponse(BaseModel):
    id: str
    email: str
    createdAt: str


# ---------------------------------------------------------------------------
# Notes
# ---------------------------------------------------------------------------
class Note(BaseModel):
    id: str
    userId: str
    title: str
    content: str
    folderOrLabel: Optional[str] = None
    updatedAt: str          # ISO 8601, server-assigned on every write
    deleted: bool = False   # soft-delete flag


class NoteListResponse(BaseModel):
    notes: List[Note]


class SyncOperation(BaseModel):
    localId: str                     # the client's local id for this note, echoed back in the response so the client can reconcile
    operationType: str               # "UPSERT" or "DELETE"
    noteId: Optional[str] = None     # server-side note id; None for a brand-new note being created for the first time
    title: Optional[str] = None
    content: Optional[str] = None
    folderOrLabel: Optional[str] = None
    clientUpdatedAt: str             # ISO 8601 — the timestamp the client had when it made this change


class SyncRequest(BaseModel):
    operations: List[SyncOperation]


class SyncOperationResult(BaseModel):
    localId: str
    status: str                    # "applied", "conflict", or "error"
    note: Optional[Note] = None    # the resulting server-side note (winning version, whether it was the client's or not)
    message: Optional[str] = None  # only set when status == "error"


class SyncResponse(BaseModel):
    results: List[SyncOperationResult]
