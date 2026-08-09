"""
routes/cart.py

Simple cart: a persisted list of {productId, quantity} objects in
cart.json.
"""

from fastapi import APIRouter, HTTPException

import storage
from schemas import CartItemCreate, CartItemUpdate, CartResponse

router = APIRouter()

CART_FILE = "cart.json"
PRODUCTS_FILE = "products.json"


def _product_exists(product_id: int) -> bool:
    products = storage.load_json(PRODUCTS_FILE)
    return any(p["id"] == product_id for p in products)


@router.get("/cart", response_model=CartResponse)
def get_cart():
    items = storage.load_json(CART_FILE)
    return {"items": items}


@router.post("/cart", response_model=CartResponse)
def add_to_cart(item: CartItemCreate):
    if not _product_exists(item.productId):
        raise HTTPException(status_code=404, detail=f"Product {item.productId} not found")

    if item.quantity <= 0:
        raise HTTPException(status_code=400, detail="quantity must be greater than 0")

    items = storage.load_json(CART_FILE)

    for existing in items:
        if existing["productId"] == item.productId:
            existing["quantity"] += item.quantity
            storage.save_json(CART_FILE, items)
            return {"items": items}

    items.append({"productId": item.productId, "quantity": item.quantity})
    storage.save_json(CART_FILE, items)

    return {"items": items}


@router.put("/cart/{product_id}", response_model=CartResponse)
def update_cart_item(product_id: int, update: CartItemUpdate):
    if update.quantity <= 0:
        raise HTTPException(status_code=400, detail="quantity must be greater than 0")

    items = storage.load_json(CART_FILE)

    for existing in items:
        if existing["productId"] == product_id:
            existing["quantity"] = update.quantity
            storage.save_json(CART_FILE, items)
            return {"items": items}

    raise HTTPException(status_code=404, detail=f"Product {product_id} is not in the cart")


@router.delete("/cart/{product_id}", response_model=CartResponse)
def remove_cart_item(product_id: int):
    items = storage.load_json(CART_FILE)

    if not any(existing["productId"] == product_id for existing in items):
        raise HTTPException(status_code=404, detail=f"Product {product_id} is not in the cart")

    items = [existing for existing in items if existing["productId"] != product_id]
    storage.save_json(CART_FILE, items)

    return {"items": items}


@router.delete("/cart", response_model=CartResponse)
def clear_cart():
    storage.save_json(CART_FILE, [])
    return {"items": []}
