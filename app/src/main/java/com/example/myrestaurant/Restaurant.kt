package com.example.myrestaurant

data class Restaurant(
    val id: Int,
    val name: String,
    val description: String,
    val rating: Double
)

val dummyRestaurants = listOf(
    Restaurant(1, "The Gourmet Kitchen", "Fine dining with a touch of elegance.", 4.8),
    Restaurant(2, "Burger Haven", "The best burgers in town.", 4.5),
    Restaurant(3, "Pasta Palace", "Authentic Italian pasta and pizza.", 4.2),
    Restaurant(4, "Sushi Zen", "Fresh and delicious sushi rolls.", 4.7),
    Restaurant(5, "Taco Fiesta", "Spicy and flavorful Mexican tacos.", 4.3)
)
