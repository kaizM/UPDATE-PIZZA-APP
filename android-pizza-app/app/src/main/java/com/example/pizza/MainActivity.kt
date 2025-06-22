package com.example.pizza

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebSettings
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val webView = WebView(this)
        setContentView(webView)
        
        // Configure WebView settings
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        webSettings.setSupportZoom(true)
        webSettings.defaultTextEncodingName = "utf-8"
        
        // Allow mixed content (HTTP and HTTPS)
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        
        // Set WebView client to handle navigation
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url ?: "")
                return true
            }
        }
        
        // Load the employee dashboard
        // Use the correct employee dashboard route from the web app
        // Try to get Replit environment variables, fallback to localhost for development
        val replSlug = System.getenv("REPL_SLUG")
        val replOwner = System.getenv("REPL_OWNER")
        
        val dashboardUrl = if (replSlug != null && replOwner != null) {
            "https://$replSlug-$replOwner.replit.app/employee"
        } else {
            // Fallback for development - user can replace with their deployed URL
            "http://10.0.2.2:5000/employee" // Android emulator localhost
        }
        
        webView.loadUrl(dashboardUrl)
    }
    
    override fun onBackPressed() {
        val webView = findViewById<WebView>(android.R.id.content)
        if (webView?.canGoBack() == true) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}