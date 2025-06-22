package com.example.pizza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PizzaApp()
            }
        }
    }
}

@Composable
fun PizzaApp() {
    var currentPage by remember { mutableStateOf("home") }
    var cartItems by remember { mutableStateOf(listOf<String>()) }
    
    when (currentPage) {
        "home" -> HomePage(
            onMenuClick = { currentPage = "menu" }
        )
        "menu" -> MenuPage(
            onAddToCart = { pizza ->
                cartItems = cartItems + pizza
            },
            onBackClick = { currentPage = "home" },
            onCartClick = { currentPage = "cart" },
            cartCount = cartItems.size
        )
        "cart" -> CartPage(
            items = cartItems,
            onBackClick = { currentPage = "menu" },
            onRemoveItem = { index ->
                cartItems = cartItems.toMutableList().apply { removeAt(index) }
            }
        )
    }
}

@Composable
fun HomePage(onMenuClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🍕",
            fontSize = 100.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        Text(
            text = "Pizza Paradise",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE53E3E),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = "Delicious pizzas made fresh!",
            fontSize = 18.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 40.dp)
        )
        
        Button(
            onClick = onMenuClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53E3E))
        ) {
            Text(
                text = "View Menu",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF7FAFC))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "📱 Mobile App Ready",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF38A169)
                )
                Text(
                    text = "Order fresh pizzas anytime!",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun MenuPage(
    onAddToCart: (String) -> Unit,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    cartCount: Int
) {
    val pizzas = listOf(
        "Margherita - $12.99",
        "Pepperoni - $14.99", 
        "Hawaiian - $15.99",
        "Veggie Supreme - $16.99",
        "Meat Lovers - $18.99",
        "BBQ Chicken - $17.99"
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE53E3E))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onBackClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("← Back", color = Color(0xFFE53E3E))
            }
            
            Text(
                text = "Pizza Menu",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            
            Button(
                onClick = onCartClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("Cart ($cartCount)", color = Color(0xFFE53E3E))
            }
        }
        
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(pizzas) { pizza ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "🍕 $pizza",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        
                        Button(
                            onClick = { onAddToCart(pizza) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38A169)),
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text("Add", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartPage(
    items: List<String>,
    onBackClick: () -> Unit,
    onRemoveItem: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE53E3E))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onBackClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("← Menu", color = Color(0xFFE53E3E))
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Text(
                text = "Your Cart",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            
            Spacer(modifier = Modifier.weight(1f))
        }
        
        if (items.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🛒",
                    fontSize = 80.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                Text(
                    text = "Cart is Empty",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Text(
                    text = "Add some delicious pizzas!",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items.size) { index ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🍕 ${items[index]}",
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f)
                            )
                            
                            Button(
                                onClick = { onRemoveItem(index) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53E3E)),
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                Text("Remove", color = Color.White)
                            }
                        }
                    }
                }
                
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF38A169))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Ready to Order!",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "${items.size} items in cart",
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }
            }
        }
    }
}