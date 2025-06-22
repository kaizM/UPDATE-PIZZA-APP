package com.example.pizza.data.models

import com.google.gson.annotations.SerializedName

data class Pizza(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val category: String,
    val sizes: List<PizzaSize>,
    val toppings: List<String> = emptyList(),
    @SerializedName("is_available") val isAvailable: Boolean = true
)

data class PizzaSize(
    val name: String,
    val price: Double,
    val diameter: String
)

data class CartItem(
    val pizzaId: String,
    val pizzaName: String,
    val size: String,
    val price: Double,
    val quantity: Int = 1,
    val toppings: List<String> = emptyList(),
    val specialInstructions: String? = null
)

data class CustomerInfo(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String? = null
)

data class Order(
    val id: String? = null,
    val uniqueOrderId: String? = null,
    val items: List<OrderItem>,
    val customerInfo: CustomerInfo,
    val subtotal: Double,
    val tax: Double,
    val tip: Double = 0.0,
    val total: Double,
    val orderType: String = "pickup",
    val status: String = "confirmed",
    val specialInstructions: String? = null,
    val estimatedTime: Int? = null,
    val paymentStatus: String = "pending",
    val createdAt: String? = null
)

data class OrderItem(
    val pizzaId: String,
    val pizzaName: String,
    val size: String,
    val price: Double,
    val quantity: Int,
    val toppings: List<String> = emptyList(),
    val specialInstructions: String? = null
)

data class OrderResponse(
    val success: Boolean,
    val order: Order? = null,
    val message: String? = null,
    val error: String? = null
)

data class MenuResponse(
    val success: Boolean,
    val pizzas: List<Pizza> = emptyList(),
    val message: String? = null
)