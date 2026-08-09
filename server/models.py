"""
models.py

This file is responsible for one thing: building the initial list of
~40 fake products. There's no database and no ORM, so a "model" here is
just a plain Python dict shaped like:

{
    "id": 1,
    "title": "...",
    "description": "...",
    "price": 19.99,
    "discountPercentage": 10.5,
    "rating": 4.2,
    "stock": 34,
    "brand": "...",
    "category": "...",
    "thumbnail": "https://...",
    "images": ["https://...", "https://..."]
}

Images use https://picsum.photos which generates real, publicly
reachable placeholder photos from a seed string, so every product gets a
different (but always working) picture.
"""


def _image(seed: str, size: int = 400) -> str:
    return f"https://picsum.photos/seed/{seed}/{size}/{size}"


def _product(pid, title, description, price, discount, rating, stock, brand, category):
    seed = f"{category}-{pid}"
    return {
        "id": pid,
        "title": title,
        "description": description,
        "price": price,
        "discountPercentage": discount,
        "rating": rating,
        "stock": stock,
        "brand": brand,
        "category": category,
        "thumbnail": _image(seed, 400),
        "images": [_image(seed, 600), _image(f"{seed}-alt", 600)],
    }


def generate_initial_products():
    """Returns a fresh list of ~40 realistic fake products."""

    raw = [
        # Electronics
        ("Aurora X12 Smartphone", "6.5-inch AMOLED display, 128GB storage, triple camera setup.", 699.00, 12.0, 4.5, 45, "Aurora", "Electronics"),
        ("Aurora Buds Pro", "Wireless earbuds with active noise cancellation and 30h battery.", 129.99, 15.0, 4.3, 120, "Aurora", "Electronics"),
        ("NovaBook 14 Laptop", "14-inch ultralight laptop, 16GB RAM, 512GB SSD.", 1099.00, 8.0, 4.6, 25, "Nova", "Electronics"),
        ("Pulse SmartWatch 2", "Fitness tracking smartwatch with heart-rate and GPS.", 199.99, 20.0, 4.1, 60, "Pulse", "Electronics"),
        ("Cinemax 55\" 4K TV", "55-inch 4K UHD Smart TV with HDR support.", 549.00, 18.0, 4.4, 15, "Cinemax", "Electronics"),
        ("Voltage Power Bank 20000mAh", "Fast-charging portable power bank, dual USB-C ports.", 39.99, 10.0, 4.2, 200, "Voltage", "Electronics"),
        ("Echo Mini Speaker", "Compact Bluetooth speaker with deep bass.", 49.99, 5.0, 4.0, 150, "Echo", "Electronics"),
        ("FrameWorks DSLR Camera", "24MP DSLR camera with 18-55mm kit lens.", 749.00, 6.0, 4.7, 12, "FrameWorks", "Electronics"),

        # Shoes
        ("Stride Runner Sneakers", "Lightweight running shoes with breathable mesh upper.", 79.99, 15.0, 4.3, 80, "Stride", "Shoes"),
        ("Summit Hiking Boots", "Waterproof hiking boots with reinforced ankle support.", 129.00, 10.0, 4.5, 40, "Summit", "Shoes"),
        ("Glide Casual Loafers", "Slip-on leather loafers for everyday wear.", 59.99, 0.0, 4.1, 55, "Glide", "Shoes"),
        ("Bounce Basketball Shoes", "High-top basketball shoes with air cushioning.", 99.99, 20.0, 4.4, 30, "Bounce", "Shoes"),
        ("Coastal Slide Sandals", "Comfortable everyday sandals with cushioned footbed.", 24.99, 0.0, 3.9, 100, "Coastal", "Shoes"),
        ("Trekline Trail Runners", "Off-road trail running shoes with grippy outsole.", 89.99, 12.0, 4.2, 35, "Trekline", "Shoes"),

        # Clothing
        ("Classic Cotton T-Shirt", "100% cotton crew-neck t-shirt, available in multiple colors.", 14.99, 0.0, 4.0, 300, "Basics Co.", "Clothing"),
        ("Denim Slim-Fit Jeans", "Stretch denim jeans with a modern slim fit.", 49.99, 10.0, 4.2, 90, "Denimly", "Clothing"),
        ("Alpine Puffer Jacket", "Insulated puffer jacket for cold weather.", 119.00, 25.0, 4.6, 20, "Alpine", "Clothing"),
        ("Everyday Hoodie", "Fleece-lined pullover hoodie with kangaroo pocket.", 39.99, 5.0, 4.3, 110, "Basics Co.", "Clothing"),
        ("Formal Oxford Shirt", "Wrinkle-resistant button-down dress shirt.", 34.99, 0.0, 4.1, 70, "Tailorline", "Clothing"),
        ("Active Training Leggings", "Squat-proof stretch leggings for workouts.", 29.99, 10.0, 4.4, 130, "FlexFit", "Clothing"),
        ("Summer Linen Shorts", "Breathable linen-blend shorts for warm days.", 27.99, 0.0, 3.8, 85, "Coastal", "Clothing"),

        # Home
        ("Lumen Table Lamp", "Modern minimalist table lamp with warm LED bulb.", 34.99, 10.0, 4.3, 65, "Lumen", "Home"),
        ("BrewMaster Coffee Maker", "12-cup programmable drip coffee maker.", 59.99, 15.0, 4.5, 40, "BrewMaster", "Home"),
        ("CloudNine Memory Foam Pillow", "Contour memory foam pillow for neck support.", 24.99, 0.0, 4.2, 150, "CloudNine", "Home"),
        ("HearthGlow Scented Candle Set", "Set of 3 soy wax candles, assorted scents.", 19.99, 5.0, 4.0, 200, "HearthGlow", "Home"),
        ("Vortex Stand Mixer", "5-quart stand mixer with 6 speed settings.", 189.00, 12.0, 4.6, 18, "Vortex", "Home"),
        ("Nimbus Cotton Bedding Set", "Queen-size 4-piece cotton bedding set.", 69.99, 8.0, 4.1, 55, "Nimbus", "Home"),
        ("GreenLeaf Indoor Planter", "Ceramic planter pot with drainage tray, medium size.", 17.99, 0.0, 4.4, 90, "GreenLeaf", "Home"),

        # Books
        ("The Silent Orchard", "A gripping mystery novel set in a quiet countryside town.", 12.99, 0.0, 4.5, 75, "Willowmere Press", "Books"),
        ("Code & Craft: Building Software Well", "A practical guide to writing maintainable software.", 24.99, 10.0, 4.7, 50, "Ledgerline Books", "Books"),
        ("Kitchen Chronicles", "A collection of 100 easy weeknight recipes.", 18.99, 5.0, 4.3, 60, "TableTop Publishing", "Books"),
        ("Wanderlust: A Travel Memoir", "A memoir of solo travel across three continents.", 15.99, 0.0, 4.2, 40, "Willowmere Press", "Books"),
        ("Mindful Mornings", "A short guide to building a calming morning routine.", 11.99, 0.0, 4.0, 100, "Ledgerline Books", "Books"),
        ("The Last Cartographer", "A fantasy adventure about a mapmaker who charts impossible lands.", 16.99, 8.0, 4.6, 45, "Willowmere Press", "Books"),

        # Accessories
        ("Horizon Leather Wallet", "Slim bifold wallet made from genuine leather.", 29.99, 0.0, 4.3, 120, "Horizon", "Accessories"),
        ("Traveler Canvas Backpack", "20L water-resistant canvas backpack with laptop sleeve.", 54.99, 10.0, 4.5, 70, "Traveler", "Accessories"),
        ("Orbit Sunglasses", "Polarized UV400 sunglasses with lightweight frame.", 22.99, 5.0, 4.1, 95, "Orbit", "Accessories"),
        ("Timekeeper Analog Watch", "Stainless steel analog watch with leather strap.", 79.99, 15.0, 4.4, 35, "Timekeeper", "Accessories"),
        ("Woven Wool Scarf", "Soft woven scarf, one size fits most.", 19.99, 0.0, 4.0, 150, "Woolcraft", "Accessories"),
        ("Everyday Baseball Cap", "Adjustable cotton baseball cap.", 14.99, 0.0, 3.9, 200, "Basics Co.", "Accessories"),
    ]

    products = []
    for i, item in enumerate(raw, start=1):
        title, description, price, discount, rating, stock, brand, category = item
        products.append(_product(i, title, description, price, discount, rating, stock, brand, category))

    return products
