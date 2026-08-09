"""
routes/wishlist.py

Simple wishlist: just a persisted list of product ids in wishlist.json.
"""

from fastapi import APIRouter, HTTPException

import storage
from schemas import WishlistResponse

router = APIRouter()

WISHLIST_FILE = "wishlist.json"
PRODUCTS_FILE = "products.json"


@router.get("/wishlist", response_model=WishlistResponse)
def get_wishlist():
    product_ids = storage.load_json(WISHLIST_FILE)
    return {"productIds": product_ids}


@router.post("/wishlist/{product_id}", response_model=WishlistResponse)
def add_to_wishlist(product_id: int):
    products = storage.load_json(PRODUCTS_FILE)
    if not any(p["id"] == product_id for p in products):
        raise HTTPException(status_code=404, detail=f"Product {product_id} not found")

    product_ids = storage.load_json(WISHLIST_FILE)

    if product_id not in product_ids:
        product_ids.append(product_id)
        storage.save_json(WISHLIST_FILE, product_ids)

    return {"productIds": product_ids}


@router.delete("/wishlist/{product_id}", response_model=WishlistResponse)
def remove_from_wishlist(product_id: int):
    product_ids = storage.load_json(WISHLIST_FILE)

    if product_id not in product_ids:
        raise HTTPException(status_code=404, detail=f"Product {product_id} is not in the wishlist")

    product_ids = [pid for pid in product_ids if pid != product_id]
    storage.save_json(WISHLIST_FILE, product_ids)

    return {"productIds": product_ids}
