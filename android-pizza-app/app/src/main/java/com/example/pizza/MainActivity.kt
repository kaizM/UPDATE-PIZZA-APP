package com.example.pizza

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.pizza.ui.theme.PizzaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            PizzaTheme {
                WebDashboard()
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebDashboard() {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                // Configure WebView settings for optimal performance
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.builtInZoomControls = true
                settings.displayZoomControls = false
                settings.setSupportZoom(true)
                settings.defaultTextEncodingName = "utf-8"
                settings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
                
                webViewClient = WebViewClient()
                
                // Smart URL detection for your Firebase deployment
                val replSlug = System.getenv("REPL_SLUG")
                val replOwner = System.getenv("REPL_OWNER")
                
                val dashboardUrl = if (replSlug != null && replOwner != null) {
                    "https://$replSlug-$replOwner.replit.app/employee"
                } else {
                    // Fallback for development testing
                    "http://10.0.2.2:5000/employee"
                }
                
                loadUrl(dashboardUrl)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}