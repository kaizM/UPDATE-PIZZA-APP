package com.example.pizza.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza.data.models.*
import com.example.pizza.repository.PizzaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PizzaViewModel : ViewModel() {
    
    private val repository = PizzaRepository()
    
    // UI States
    private val _menuState = MutableStateFlow<MenuState>(MenuState.Loading)
    val menuState: StateFlow<MenuState> = _menuState
    
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems
    
    private val _orderState = MutableStateFlow<OrderState>(OrderState.Idle)
    val orderState: StateFlow<OrderState> = _orderState
    
    private val _customerInfo = MutableStateFlow<CustomerInfo?>(null)
    val customerInfo: StateFlow<CustomerInfo?> = _customerInfo
    
    init {
        loadMenu()
    }
    
    fun loadMenu() {
        viewModelScope.launch {
            _menuState.value = MenuState.Loading
            repository.getMenu()
                .onSuccess { pizzas ->
                    _menuState.value = MenuState.Success(pizzas)
                }
                .onFailure { error ->
                    _menuState.value = MenuState.Error(error.message ?: "Unknown error")
                }
        }
    }
    
    fun addToCart(pizza: Pizza, size: PizzaSize, quantity: Int = 1, toppings: List<String> = emptyList()) {
        val cartItem = CartItem(
            pizzaId = pizza.id,
            pizzaName = pizza.name,
            size = size.name,
            price = size.price,
            quantity = quantity,
            toppings = toppings
        )
        
        val currentItems = _cartItems.value.toMutableList()
        val existingItemIndex = currentItems.indexOfFirst { 
            it.pizzaId == cartItem.pizzaId && 
            it.size == cartItem.size && 
            it.toppings == cartItem.toppings 
        }
        
        if (existingItemIndex >= 0) {
            currentItems[existingItemIndex] = currentItems[existingItemIndex].copy(
                quantity = currentItems[existingItemIndex].quantity + quantity
            )
        } else {
            currentItems.add(cartItem)
        }
        
        _cartItems.value = currentItems
    }
    
    fun removeFromCart(cartItem: CartItem) {
        _cartItems.value = _cartItems.value.filter { it != cartItem }
    }
    
    fun updateCartItemQuantity(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(cartItem)
            return
        }
        
        _cartItems.value = _cartItems.value.map { item ->
            if (item == cartItem) item.copy(quantity = newQuantity) else item
        }
    }
    
    fun clearCart() {
        _cartItems.value = emptyList()
    }
    
    fun setCustomerInfo(customerInfo: CustomerInfo) {
        _customerInfo.value = customerInfo
    }
    
    fun calculateSubtotal(): Double {
        return _cartItems.value.sumOf { it.price * it.quantity }
    }
    
    fun calculateTax(subtotal: Double): Double {
        return subtotal * 0.08 // 8% tax rate
    }
    
    fun calculateTotal(subtotal: Double, tax: Double, tip: Double = 0.0): Double {
        return subtotal + tax + tip
    }
    
    fun placeOrder(tip: Double = 0.0, specialInstructions: String? = null) {
        val customer = _customerInfo.value
        if (customer == null) {
            _orderState.value = OrderState.Error("Customer information is required")
            return
        }
        
        if (_cartItems.value.isEmpty()) {
            _orderState.value = OrderState.Error("Cart is empty")
            return
        }
        
        viewModelScope.launch {
            _orderState.value = OrderState.Loading
            
            val subtotal = calculateSubtotal()
            val tax = calculateTax(subtotal)
            val total = calculateTotal(subtotal, tax, tip)
            
            val orderItems = _cartItems.value.map { cartItem ->
                OrderItem(
                    pizzaId = cartItem.pizzaId,
                    pizzaName = cartItem.pizzaName,
                    size = cartItem.size,
                    price = cartItem.price,
                    quantity = cartItem.quantity,
                    toppings = cartItem.toppings,
                    specialInstructions = cartItem.specialInstructions
                )
            }
            
            val order = Order(
                items = orderItems,
                customerInfo = customer,
                subtotal = subtotal,
                tax = tax,
                tip = tip,
                total = total,
                specialInstructions = specialInstructions
            )
            
            repository.createOrder(order)
                .onSuccess { createdOrder ->
                    _orderState.value = OrderState.Success(createdOrder)
                    clearCart()
                }
                .onFailure { error ->
                    _orderState.value = OrderState.Error(error.message ?: "Failed to place order")
                }
        }
    }
    
    fun resetOrderState() {
        _orderState.value = OrderState.Idle
    }
}

sealed class MenuState {
    object Loading : MenuState()
    data class Success(val pizzas: List<Pizza>) : MenuState()
    data class Error(val message: String) : MenuState()
}

sealed class OrderState {
    object Idle : OrderState()
    object Loading : OrderState()
    data class Success(val order: Order) : OrderState()
    data class Error(val message: String) : OrderState()
}