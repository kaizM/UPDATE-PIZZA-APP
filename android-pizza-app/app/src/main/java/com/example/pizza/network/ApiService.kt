package com.example.pizza.network

import com.example.pizza.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    @GET("api/menu")
    suspend fun getMenu(): Response<MenuResponse>
    
    @POST("api/orders")
    suspend fun createOrder(@Body order: Order): Response<OrderResponse>
    
    @GET("api/orders/{orderId}")
    suspend fun getOrder(@Path("orderId") orderId: String): Response<OrderResponse>
    
    @PUT("api/orders/{orderId}/status")
    suspend fun updateOrderStatus(
        @Path("orderId") orderId: String,
        @Body status: Map<String, String>
    ): Response<OrderResponse>
    
    @POST("api/customers")
    suspend fun createCustomer(@Body customerInfo: CustomerInfo): Response<Map<String, Any>>
    
    @GET("api/orders/customer/{phone}")
    suspend fun getCustomerOrders(@Path("phone") phone: String): Response<List<Order>>
}