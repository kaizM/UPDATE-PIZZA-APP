package com.example.pizza.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    
    // Your Replit server URL - update this with your actual Replit app URL
    // For development, you can use: "http://10.0.2.2:5000/" (Android emulator localhost)
    // For production, use your Replit app URL: "https://your-replit-app-name.replit.app/"
    private const val BASE_URL = "http://10.0.2.2:5000/"
    
    private val gson = GsonBuilder()
        .setLenient()
        .create()
        
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    
    val apiService: ApiService = retrofit.create(ApiService::class.java)
    
    // Method to update base URL dynamically (for testing with different servers)
    fun updateBaseUrl(newBaseUrl: String): ApiService {
        val newRetrofit = Retrofit.Builder()
            .baseUrl(if (newBaseUrl.endsWith("/")) newBaseUrl else "$newBaseUrl/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        
        return newRetrofit.create(ApiService::class.java)
    }
}