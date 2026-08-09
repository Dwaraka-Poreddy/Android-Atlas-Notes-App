"""
routes/products.py

Everything related to browsing products: list (with search / category /
sort / pagination) and fetching a single product by id.
"""

from fastapi import APIRouter, HTTPException, Query
from typing import Optional

import storage
from schemas import Product, ProductListResponse

router = APIRouter()

PRODUCTS_FILE = "products.json"


@router.get("/products", response_model=ProductListResponse)
def get_products(
    search: Optional[str] = Query(None, description="Search in product title"),
    category: Optional[str] = Query(None, description="Filter by category"),
    sort: Optional[str] = Query(None, description="Sort by: price, -price, rating, -rating, title"),
    page: int = Query(1, ge=1),
    pageSize: int = Query(20, ge=1, le=100),
):
    products = storage.load_json(PRODUCTS_FILE)

    # --- filtering ---
    if search:
        search_lower = search.lower()
        products = [p for p in products if search_lower in p["title"].lower()]

    if category:
        category_lower = category.lower()
        products = [p for p in products if p["category"].lower() == category_lower]

    # --- sorting ---
    if sort:
        reverse = sort.startswith("-")
        key = sort.lstrip("-")

        if key in ("price", "rating", "stock", "discountPercentage"):
            products = sorted(products, key=lambda p: p[key], reverse=reverse)
        elif key == "title":
            products = sorted(products, key=lambda p: p["title"].lower(), reverse=reverse)
        # any unknown sort value is silently ignored, keeping things simple

    total = len(products)

    # --- pagination ---
    start = (page - 1) * pageSize
    end = start + pageSize
    page_items = products[start:end]

    return {
        "total": total,
        "page": page,
        "pageSize": pageSize,
        "products": page_items,
    }


@router.get("/products/{product_id}", response_model=Product)
def get_product(product_id: int):
    products = storage.load_json(PRODUCTS_FILE)

    for product in products:
        if product["id"] == product_id:
            return product

    raise HTTPException(status_code=404, detail=f"Product {product_id} not found")
