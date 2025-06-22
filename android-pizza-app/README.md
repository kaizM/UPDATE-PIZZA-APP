# Pizza Ordering Android App

This Android app connects to your existing pizza ordering web API built with Express.js and running on Replit.

## Features

- **Browse Menu**: View available pizzas with sizes and pricing
- **Shopping Cart**: Add pizzas with customization options
- **Checkout Process**: Customer information and order placement
- **Order Confirmation**: Real-time order tracking integration
- **Modern UI**: Built with Jetpack Compose and Material Design 3

## Architecture

- **MVVM Pattern**: ViewModel manages UI state and business logic
- **Retrofit**: HTTP client for API communication
- **Jetpack Compose**: Modern declarative UI framework
- **Navigation Component**: Screen-to-screen navigation
- **Coroutines**: Asynchronous operations and API calls

## API Integration

The app connects to your Replit pizza ordering API at:
- **Development**: `http://10.0.2.2:5000/` (Android emulator localhost)
- **Production**: Update `BASE_URL` in `RetrofitClient.kt` with your Replit app URL

### API Endpoints Used:
- `GET /api/menu` - Fetch pizza menu
- `POST /api/orders` - Create new order
- `GET /api/orders/{id}` - Get order details
- `GET /api/orders/customer/{phone}` - Customer order history

## Setup Instructions

1. **Open in Android Studio**:
   - Import the `android-pizza-app` folder as an Android project
   - Let Gradle sync and download dependencies

2. **Configure API URL**:
   - Open `network/RetrofitClient.kt`
   - Update `BASE_URL` with your Replit app URL for production
   - For testing with emulator, use `http://10.0.2.2:5000/`

3. **Build and Run**:
   - Connect Android device or start emulator
   - Build and install the app
   - The app will connect to your Replit pizza ordering API

## Key Components

### Data Models
- `Pizza`, `CartItem`, `Order`, `CustomerInfo` - Core data structures
- JSON serialization with Gson for API communication

### Network Layer
- `ApiService` - Retrofit interface defining API endpoints
- `RetrofitClient` - HTTP client configuration
- `PizzaRepository` - Data repository pattern implementation

### UI Screens
- `MenuScreen` - Browse and select pizzas
- `CartScreen` - Review selected items
- `CheckoutScreen` - Customer info and payment
- `OrderConfirmationScreen` - Order success confirmation

### ViewModel
- `PizzaViewModel` - Manages app state and business logic
- Reactive state management with StateFlow
- Coroutines for asynchronous operations

## Dependencies

- **Jetpack Compose** - Modern UI toolkit
- **Retrofit** - HTTP client library
- **Gson** - JSON serialization
- **Navigation Compose** - Screen navigation
- **ViewModel Compose** - State management
- **Coroutines** - Asynchronous programming

## Testing

The app is configured to work with your existing Replit pizza ordering system. Make sure your web server is running on port 5000 before testing the Android app.

## Future Enhancements

- Push notifications for order status updates
- Customer account management
- Order history and favorites
- Payment integration (currently uses web API's payment handling)
- Offline mode with local caching