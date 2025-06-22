package com.example.pizza.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.pizza.data.models.Pizza
import com.example.pizza.data.models.PizzaSize
import com.example.pizza.viewmodel.MenuState
import com.example.pizza.viewmodel.PizzaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    navController: NavController,
    viewModel: PizzaViewModel
) {
    val menuState by viewModel.menuState.collectAsStateWithLifecycle()
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pizza Menu") },
                actions = {
                    BadgedBox(
                        badge = {
                            if (cartItems.isNotEmpty()) {
                                Badge {
                                    Text("${cartItems.sumOf { it.quantity }}")
                                }
                            }
                        }
                    ) {
                        IconButton(onClick = { navController.navigate("cart") }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        when (menuState) {
            is MenuState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is MenuState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Error loading menu",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = menuState.message,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Button(
                        onClick = { viewModel.loadMenu() },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Retry")
                    }
                }
            }
            is MenuState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(menuState.pizzas) { pizza ->
                        PizzaCard(
                            pizza = pizza,
                            onAddToCart = { selectedSize, quantity ->
                                viewModel.addToCart(pizza, selectedSize, quantity)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PizzaCard(
    pizza: Pizza,
    onAddToCart: (PizzaSize, Int) -> Unit
) {
    var selectedSize by remember { mutableStateOf(pizza.sizes.firstOrNull()) }
    var quantity by remember { mutableIntStateOf(1) }
    var showAddDialog by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Pizza image placeholder
                Card(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🍕",
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = pizza.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = pizza.description,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = "Category: ${pizza.category}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Size selection
            if (pizza.sizes.isNotEmpty()) {
                Text(
                    text = "Sizes:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    pizza.sizes.forEach { size ->
                        FilterChip(
                            onClick = { selectedSize = size },
                            label = { 
                                Text("${size.name} - $${String.format("%.2f", size.price)}")
                            },
                            selected = selectedSize == size
                        )
                    }
                }
            }
            
            // Add to cart button
            Button(
                onClick = { showAddDialog = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = selectedSize != null && pizza.isAvailable
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add to Cart")
            }
        }
    }
    
    // Add to cart dialog
    if (showAddDialog && selectedSize != null) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add ${pizza.name} to Cart") },
            text = {
                Column {
                    Text("Size: ${selectedSize!!.name}")
                    Text("Price: $${String.format("%.2f", selectedSize!!.price)}")
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Quantity:")
                        
                        OutlinedButton(
                            onClick = { if (quantity > 1) quantity-- },
                            enabled = quantity > 1
                        ) {
                            Text("-")
                        }
                        
                        Text(
                            text = quantity.toString(),
                            style = MaterialTheme.typography.titleMedium
                        )
                        
                        OutlinedButton(
                            onClick = { quantity++ }
                        ) {
                            Text("+")
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onAddToCart(selectedSize!!, quantity)
                        showAddDialog = false
                        quantity = 1
                    }
                ) {
                    Text("Add to Cart")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}