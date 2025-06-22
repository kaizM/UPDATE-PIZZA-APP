package com.example.pizza.repository

import com.example.pizza.data.models.*
import com.example.pizza.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PizzaRepository {
    
    private val apiService = RetrofitClient.apiService
    
    suspend fun getMenu(): Result<List<Pizza>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getMenu()
            if (response.isSuccessful) {
                val menuResponse = response.body()
                if (menuResponse?.success == true) {
                    Result.success(menuResponse.pizzas)
                } else {
                    Result.failure(Exception(menuResponse?.message ?: "Failed to load menu"))
                }
            } else {
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createOrder(order: Order): Result<Order> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.createOrder(order)
            if (response.isSuccessful) {
                val orderResponse = response.body()
                if (orderResponse?.success == true && orderResponse.order != null) {
                    Result.success(orderResponse.order)
                } else {
                    Result.failure(Exception(orderResponse?.error ?: "Failed to create order"))
                }
            } else {
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getOrder(orderId: String): Result<Order> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getOrder(orderId)
            if (response.isSuccessful) {
                val orderResponse = response.body()
                if (orderResponse?.success == true && orderResponse.order != null) {
                    Result.success(orderResponse.order)
                } else {
                    Result.failure(Exception(orderResponse?.error ?: "Order not found"))
                }
            } else {
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getCustomerOrders(phone: String): Result<List<Order>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getCustomerOrders(phone)
            if (response.isSuccessful) {
                val orders = response.body() ?: emptyList()
                Result.success(orders)
            } else {
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateOrderStatus(orderId: String, status: String): Result<Order> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.updateOrderStatus(orderId, mapOf("status" to status))
            if (response.isSuccessful) {
                val orderResponse = response.body()
                if (orderResponse?.success == true && orderResponse.order != null) {
                    Result.success(orderResponse.order)
                } else {
                    Result.failure(Exception(orderResponse?.error ?: "Failed to update order"))
                }
            } else {
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}